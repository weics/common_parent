package com.itheima.bos_fore.web.action;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos_fore.utils.MD5Utils;
import com.itheima.bos_fore.utils.SMSUtils;
import com.itheima.crm.cxf.Customer;
import com.itheima.crm.cxf.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 客户信息管理
 * @author zhaoqx
 *
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	private Customer model = new Customer();
	
	public Customer getModel() {
		return model;
	}
	
	//注入CRM客户端代理对象
	@Autowired
	private CustomerService crmClientProxy;
	
	//为注册用户发送短信验证码
	@Action(value="customerAction_sendValidateCode")
	public String sendValidateCode() throws Exception{
		String telephone = model.getTelephone();
		Customer customer = crmClientProxy.findByTelephone(telephone);
		if(customer != null){
			//当前手机号已经注册过了，不能重复注册，返回结果，提示用户
			ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print("1");
			return NONE;
		}
		//说明手机号没有注册过，可以发送验证码
		String validateCode = RandomStringUtils.randomNumeric(6);//生成随机6位验证码
		//将生成的验证码放入session
		ServletActionContext.getRequest().getSession().setAttribute(telephone, validateCode);
		
		//调用云通信短信服务，发送验证码
		try{
			SMSUtils.sendValidateCode(telephone, validateCode);
		}catch(Exception e){
			e.printStackTrace();
		}
		return NONE;
	}
	
	//通过属性驱动获取客户输入的验证码
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	//用户注册
	@Action(value="customerAction_regist",results={
			@Result(name="fail",type="redirect",location="/signup-fail.html"),
			@Result(name="success",type="redirect",location="/login.html")
	})
	public String regist() throws Exception{
		//判断用户输入的验证码是否正确
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
		if(checkcode != null && validateCode != null && checkcode.equals(validateCode)){
			//验证码输入正确，调用CRM服务注册用户
			model.setPassword(MD5Utils.md5(model.getPassword()));
			try{
				crmClientProxy.regist(model);
				return SUCCESS;
			}catch(Exception e){
				e.printStackTrace();
				return "fail";
			}
		}else{
			//验证码输入错误,跳转到注册失败页面
			return "fail";
		}
	}
}
