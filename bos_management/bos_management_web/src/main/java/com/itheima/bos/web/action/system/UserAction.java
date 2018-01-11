package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.User;
import com.itheima.bos.web.action.common.CommonAction;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class UserAction extends CommonAction<User> {

    //属性驱动,获得验证码
    private String validateCode;

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    //用户登录
    @Action(
            value = "userAction_login",
            results = {
                    @Result(name = "index", type = "redirect", location = "/index.jsp"),
                    @Result(name = "login", type = "redirect", location = "/login.jsp")
            })
    public String login() {
        String result = null;
        //从session中获取生成的验证码
        String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        //判断验证码是否正确
        if (StringUtils.isNotBlank(validateCode) && StringUtils.isNotBlank(key) && validateCode.equals(key)) {
            //验证码输入正确
            //基于shiro框架实现认证
            Subject subject = SecurityUtils.getSubject();//获得当前用户对象
            //用户密码令牌
            UsernamePasswordToken token = new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());
            try {
                subject.login(token);
                //如果框架没有抛异常,说明认证通过
                User user = (User) subject.getPrincipal();
                ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
                //跳转到系统首页
                result = "index";
            } catch (AuthenticationException e) {
                e.printStackTrace();
                //认证失败,跳转登录页
                result = LOGIN;
            }
        } else {
            //验证码输入错误,跳转会登录页面
            result = LOGIN;
        }
        return result;
    }

    //用户注销
    @Action(value = "userAction_logout", results = {@Result(name = "login", type = "redirect", location = "/login.jsp")})
    public String logout() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return LOGIN;
    }
}
