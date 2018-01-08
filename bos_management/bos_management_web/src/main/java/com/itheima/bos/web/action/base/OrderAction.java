package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.service.base.OrderServiceBase;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Namespace(value = "/")
@Controller
@ParentPackage(value = "struts-default")
@Scope(value = "prototype")
public class OrderAction extends CommonAction<Order> {
    @Autowired
    private OrderServiceBase orderService;

    //属性驱动
    private String orderNums;
    private String courierId;
    @Autowired
    private CourierService courierService;

    public void setOrderNums(String orderNums) {
        this.orderNums = orderNums;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    //查询人工调度订单
    @Action(value = "orderAction_pageQuery")
    public String pageQuery() throws Exception {

        final String orderType = getModel().getOrderType();
        final Courier courier = getModel().getCourier();

        //封装一个对象(查询条件)
        Specification<Order> spe = new Specification<Order>() {
            List<Predicate> list = new ArrayList<>();

            //封装查询雕件
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate predicate = null;

                if (StringUtils.isNotBlank(orderType)) {
                    //创建一个查询条件,根据订单类型查询
                    Predicate p3 = cb.equal(root.get("orderType").as(String.class), orderType);
                    list.add(p3);
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

        Page<Order> page = orderService.pageQuery(spe, pageable);

        page2json(page, new String[]{"workBills", "subareas", "fixedAreas"});

        return NONE;
    }

    //人工分单
    @Action(value = "orderAction_handlerOrder", results = {@Result(name = "success", type = "redirect", location = "/pages/take_delivery/dispatcher.html")})
    public String handlerOrder() throws Exception {
        orderService.handlerOrder(orderNums, courierId);
        return SUCCESS;
    }
}
