package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;
import com.itheima.bos.web.action.common.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {
    @Autowired
    private TakeTimeService takeTimeService;

    //获取所有收派时间
    @Action(value = "takeTimeAction_listajax")
    public String listajax() throws Exception {
        List<TakeTime> list = takeTimeService.findAll();
        list2json(list, null);
        return NONE;
    }
}
