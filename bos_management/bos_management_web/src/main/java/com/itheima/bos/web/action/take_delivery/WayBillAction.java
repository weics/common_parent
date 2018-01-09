package com.itheima.bos.web.action.take_delivery;

import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;
import com.itheima.bos.web.action.common.CommonAction;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Namespace(value = "/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class WayBillAction extends CommonAction<WayBill> {
    @Autowired
    private WayBillService wayBillService;

    //保存运单
    @Action(value = "wayBillAction_save")
    public String wayBillAction_save() throws Exception {
        String saveFlag = "0";
        try {
            wayBillService.save(getModel());
        } catch (Exception e) {
            saveFlag = "1";
            e.printStackTrace();
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(saveFlag);
        return NONE;
    }
}
