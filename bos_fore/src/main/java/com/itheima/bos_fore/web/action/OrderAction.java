package com.itheima.bos_fore.web.action;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.take_delivery.OrderService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Namespace(value = "/")
@Controller
@ParentPackage(value = "struts-default")
@Scope(value = "prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
    private Order model = new Order();

    public Order getModel() {
        return model;
    }

    //属性驱动
    private String sendAreaInfo;
    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    //注入订单服务的客户端代理对象
    @Autowired
    private OrderService orderClientProxy;

    //提交订单
    @Action(value = "orderAction_add", results = {@Result(name = "success", type = "redirect", location = "/order-success.html")})
    public String add() throws Exception {
        if (StringUtils.isNotBlank(sendAreaInfo)) {
            String[] info1 = sendAreaInfo.split("/");
            String province = info1[0];
            String city = info1[1];
            String district = info1[2];
            Area sendArea = new Area(province, city, district);
            model.setSendArea(sendArea);
        }

        if (StringUtils.isNotBlank(recAreaInfo)) {
            String[] info2 = recAreaInfo.split("/");
            String province = info2[0];
            String city = info2[1];
            String district = info2[2];
            Area recArea = new Area(province, city, district);
            model.setRecArea(recArea);
        }

        //通过WebService调用后台订单服务
        orderClientProxy.autoOrder(model);

        return SUCCESS;
    }
}
