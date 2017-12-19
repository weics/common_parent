package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

    //持有JavaBean对象
    private Standard model = new Standard();

    //通过属性驱动获取分页提交的page rows
    private int page;//页号

    private int rows;//查询多少条,页大小

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

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

    //收派标准分页查询
    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws Exception {
        //构造一个Pageable对象作为分页的参数,提供给Spring data jpa实现分页
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Standard> page = standardService.pageQuery(pageable);

        //获取总数据量
        long total = page.getTotalElements();
        //当前页要展示的数据集合
        List<Standard> rows = page.getContent();

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("rows", rows);


        //如何将page对象转为页面datagrid可以解析的数据???
        String json = JSONObject.fromObject(result).toString();

        //通过输出流将json数据响应到页面
        ServletActionContext.getResponse().getWriter().write(json);

        return NONE;
    }
}
