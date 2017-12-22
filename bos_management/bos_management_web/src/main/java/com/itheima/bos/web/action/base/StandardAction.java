package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.web.action.common.CommonAction;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class StandardAction extends CommonAction<Standard> {

    //注入Service
    @Autowired
    private StandardService standardService;

    //添加收派标准
    @Action(value = "standardAction_save", results = {@Result(name = "success", type = "redirect", location = "/pages/base/standard.jsp")})
    public String save() throws Exception {
        standardService.save(getModel());
        return SUCCESS;
    }

    //收派标准分页查询
    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws Exception {
        //构造一个Pageable对象作为分页的参数,提供给Spring data jpa实现分页
        Pageable pageable = new PageRequest(getPage() - 1, getRows());
        Page<Standard> page = standardService.pageQuery(pageable);

        page2json(page, null);

        return NONE;
    }

    //查询所有收派标准
    @Action(value = "standardAction_findAll")
    public String findAll() throws Exception {

        List<Standard> list = standardService.findAll();

        String json = JSONArray.fromObject(list).toString();

        //通过输出流将json数据响应到页面
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().write(json);
        return NONE;
    }

}
