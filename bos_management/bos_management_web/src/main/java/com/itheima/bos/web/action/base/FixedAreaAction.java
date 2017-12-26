package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.base.FixedAreaService;
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
public class FixedAreaAction extends CommonAction<FixedArea> {
    @Autowired
    private FixedAreaService fixedAreaService;

    //保存定区
    @Action(value = "fixedAreaAction_save", results = {@Result(name = "success", type = "redirect", location = "/pages/base/fixed_area.jsp")})
    public String save() throws Exception {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
}
