package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {
    //持有courier对象
    private Courier model = new Courier();

    //通过属性驱动获取分页提交的参数page rows
    private int page;//页号
    private int rows;//页大小

    //批量删除时接收页面传入的id
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public Courier getModel() {
        return model;
    }


    @Autowired
    private CourierService courierService;

    //保存
    @Action(value = "courierAction_save", results = {@Result(name = "success", type = "redirect", location = "/pages/base/courier.jsp")})
    public String execute() throws Exception {
        courierService.save(model);
        return SUCCESS;
    }

    //快递员分页查询
    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws Exception {
        //构造一个Pageable对象作为分页的参数,提供给Spring data jpa实现分页
        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Courier> page = courierService.pageQuery(pageable);

        //获取总数据量
        long total = page.getTotalElements();
        //获取当前页要展示的数据集合
        List<Courier> rows = page.getContent();

        //创建Map用于封装总量和页数据,转成json响应给页面
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", rows);

        //如何将page对象转为页面datagrid可以解析的json数据???
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas"});
        String json = JSONObject.fromObject(map, jsonConfig).toString();

        //通过输出流将json数据响应到页面
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(json);

        return NONE;
    }

    //批量删除
    @Action(value = "courierAction_deleteBatch", results = {@Result(name = "success", type = "redirect", location = "/pages/base/courier.jsp")})
    public String deleteBatch() throws Exception {
        courierService.deleteBatch(ids);
        return SUCCESS;
    }
}
