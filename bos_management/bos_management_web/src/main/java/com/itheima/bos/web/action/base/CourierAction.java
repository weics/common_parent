package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.action.common.CommonAction;
import org.apache.commons.lang.StringUtils;
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
import java.util.List;

@Namespace(value = "/")
@ParentPackage(value = "struts-default")
@Controller
@Scope(value = "prototype")
public class CourierAction extends CommonAction<Courier> {

    //批量删除时接收页面传入的id
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }


    @Autowired
    private CourierService courierService;

    //保存
    @Action(value = "courierAction_save", results = {@Result(name = "success", type = "redirect", location = "/pages/base/courier.jsp")})
    public String execute() throws Exception {
        courierService.save(getModel());
        return SUCCESS;
    }

    //快递员分页查询
    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws Exception {

        final String courierNum = getModel().getCourierNum();
        final String company = getModel().getCompany();
        final String type = getModel().getType();

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

                if (getModel().getStandard() != null) {
                    String name = getModel().getStandard().getName();
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
        Pageable pageable = new PageRequest(getPage() - 1, getRows());

        Page<Courier> page = courierService.pageQuery(spe, pageable);

        page2json(page, new String[]{"fixedAreas"});

        return NONE;
    }

    //批量删除
    @Action(value = "courierAction_deleteBatch", results = {@Result(name = "success", type = "redirect", location = "/pages/base/courier.jsp")})
    public String deleteBatch() throws Exception {
        courierService.deleteBatch(ids);
        return SUCCESS;
    }

    //查询所有未删除的快递员
    @Action(value = "courierAction_listajax")
    public String listajax() throws Exception {
        List<Courier> list = courierService.listajax();
        list2json(list, new String[]{"fixedAreas"});
        return NONE;
    }
}
