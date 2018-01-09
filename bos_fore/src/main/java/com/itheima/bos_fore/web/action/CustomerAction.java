package com.itheima.bos_fore.web.action;

import com.itheima.bos_fore.utils.MD5Utils;
import com.itheima.bos_fore.utils.MailUtils;
import com.itheima.crm.cxf.Customer;
import com.itheima.crm.cxf.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
    private Customer model = new Customer();

    public Customer getModel() {
        return model;
    }

    @Autowired
    @Qualifier(value = "jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    //属性驱动
    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    //注入crm客户端代理对象
    @Autowired
    private CustomerService crmClientProxy;

    //为注册用户发送短信验证码
    @Action(value = "customerAction_sendValidateCode")
    public String sendValidateCode() throws Exception {
        final String telephone = model.getTelephone();
        Customer customer = crmClientProxy.findByTelephone(telephone);

        if (customer != null) {
            //当前手机号已经注册过了,不能重复注册,返回结果,提示用户
            ServletActionContext.getResponse().setContentType("text/html;chaset=utf-8");
            ServletActionContext.getResponse().getWriter().write("1");
        } else {
            //说明手机号没有注册过,可以发送验证码
            final String validateCode = RandomStringUtils.randomNumeric(6);//随机生成6位数验证码
            //将生成的验证码放入Session
            ServletActionContext.getRequest().getSession().setAttribute(telephone, validateCode);

            //通过云通信短信服务,发送验证码,通过下面的消息队列发送验证码
            /*try {
                SMSUtils.sendValidateCode(telephone, validateCode);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            //向指定的队列中发送消息
            jmsTemplate.send("sms_queue", new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("telephone", telephone);
                    mapMessage.setString("validateCode", validateCode);
                    return mapMessage;
                }
            });
        }
        return NONE;
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //用户注册
    @Action(
            value = "customerAction_regist",
            results = {
                    @Result(name = "success", type = "redirect", location = "/login.html"),
                    @Result(name = "fail", type = "redirect", location = "/signup-fail.html")
            }
    )
    public String regist() throws Exception {
        String result = SUCCESS;

        //判断用户输入的验证码是否正确
        String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
        if (checkcode != null && validateCode != null && checkcode.equals(validateCode)) {
            //验证码输入正确,调用crm服务注册用户
            model.setPassword(MD5Utils.md5(model.getPassword()));
            try {
                //调用CRM服务保存用户信息
                crmClientProxy.regist(model);
                //生成激活码
                String activeCode = UUID.randomUUID().toString();
                //将激活码保存的redis,保存24消失
                redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24, TimeUnit.HOURS);
                String subject = "速运快递激活邮件";
                String content = "尊敬的用户你好，欢迎注册成为速运快递会员，请于24小时内点击下面链接完成邮件激活。"
                        + "<br><a href='" + MailUtils.activeUrl + "?activeCode=" + activeCode + "&telephone=" + model.getTelephone() + "'>点此激活</a>";
                String to = model.getEmail();
                MailUtils.sendMail(subject, content, to);
            } catch (Exception e) {
                //验证码输入错误,跳转到注册失败页面
                result = "fail";
                e.printStackTrace();
            }
        }
        return result;
    }

    //激活邮件
    @Action(
            value = "customerAction_activeMail",
            results = {
                    @Result(name = "success", type = "redirect", location = "/activeMail-success.html"),
                    @Result(name = "fail", type = "redirect", location = "/activeMail-fail.html")
            }
    )
    public String activeMail() throws Exception {
        String result = null;
        String telephone = model.getTelephone();
        //校验用户提交的激活码是否正确
        //从redis中获取提前保存的激活码
        String redisCode = (String) redisTemplate.opsForValue().get(telephone);
        //判断用户是否存在重复激活
        Customer customer = crmClientProxy.findByTelephone(telephone);
        if (redisCode == null) {
            //有可能是超时,导致激活码清除掉
            //有可能是用户伪造的请求
            //跳转到激活失败提示页面
            result = "fail";
        } else if (customer.getType() == 1) {
            //已经激活了
            result = "fail";
        } else {
            //调用CRM服务完成激活
            crmClientProxy.activeMail(telephone);
            result = SUCCESS;
        }
        return result;
    }

    //登录
    @Action(
            value = "customerAction_login",
            results = {
                    @Result(name = "login", type = "redirect", location = "/login.html"),
                    @Result(name = "index", type = "redirect", location = "/index.html")
            }
    )
    public String login() throws Exception {
        String result = null;
        //获取Session中保存的验证码
        String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        //判断用户提交的验证码是否正确
        if (StringUtils.isNotBlank(checkcode) && StringUtils.isNotBlank(validateCode) && checkcode.equals(validateCode)) {
            //验证码输入正确
            //调用CRM服务实现登录
            Customer customer = crmClientProxy.login(model.getTelephone(), MD5Utils.md5(model.getPassword()));
            if (customer != null) {
                //登录成功
                //将customer放入session
                ServletActionContext.getRequest().getSession().setAttribute("currentUser", customer);
                result = "index";
            } else {
                //登录失败
                result = LOGIN;
            }
        } else {
            //验证码错误
            result = LOGIN;
        }
        return result;
    }
}
