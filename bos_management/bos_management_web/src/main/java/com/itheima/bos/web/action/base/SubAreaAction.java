package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;
import com.itheima.bos.web.action.common.CommonAction;
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
public class SubAreaAction extends CommonAction<SubArea> {
    @Autowired
    private SubAreaService service;

    //保存一个分区
    @Action(value = "subareaAction_save", results = {@Result(name = "success", type = "redirect", location = "/pages/base/sub_area.jsp")})
    public String save() throws Exception {
        service.save(getModel());
        return SUCCESS;
    }
}
