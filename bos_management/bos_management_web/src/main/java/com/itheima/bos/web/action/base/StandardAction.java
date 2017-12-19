package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

    //持有JavaBean对象
    private Standard model = new Standard();

    @Override
    public Standard getModel() {
        return model;
    }

    //注入Service
    @Autowired
    private StandardService standardService;

    //添加收派标准
    @Action(value = "standardAction_save", results = {@Result(name = "success", type = "redirect", location = "/pages/base/standard.jsp")})
    public String save() throws Exception {
        standardService.save(model);
        return SUCCESS;
    }
}
