package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.util.ArrayList;
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

        final String courierNum = model.getCourierNum();
        final String company = model.getCompany();
        final String type = model.getType();

        //封装一个对象(查询条件)
        Specification<Courier> spe = new Specification<Courier>() {
            List<Predicate> list = new ArrayList<>();

            //封装查询雕件
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate predicate = null;

                if (StringUtils.isNotBlank(courierNum)) {
                    //创建一个查询条件,根据快递员工号等值查询
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }

                if (StringUtils.isNotBlank(company)) {
                    //创建一个查询条件,根据快递员公司等值查询
                    Predicate p2 = cb.equal(root.get("company").as(String.class), company);
                    list.add(p2);
                }

                if (StringUtils.isNotBlank(type)) {
                    //创建一个查询条件,根据快递员类型模糊查询
                    Predicate p3 = cb.like(root.get("type").as(String.class), "%" + type + "%");
                    list.add(p3);
                }

                if (model.getStandard() != null) {
                    String name = model.getStandard().getName();
                    if (StringUtils.isNotBlank(name)) {
                        //创建一个查询条件,根据收派标准的名称进行模糊查询
                        Join<Object, Object> join = root.join("standard");
                        Predicate p4 = cb.like(join.get("name").as(String.class), "%" + name + "%");
                        list.add(p4);
                    }

                }
                if (list.size() != 0) {
                    Predicate[] ps = new Predicate[list.size()];
                    //封装条件,框架会根据我们封装的条件进行查询
                    predicate = cb.and(list.toArray(ps));
                }
                return predicate;
            }
        };


        //构造一个Pageable对象作为分页的参数,提供给Spring data jpa实现分页
        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Courier> page = courierService.pageQuery(spe,pageable);

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
