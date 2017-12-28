package com.itheima.bos.web.action.base;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.web.action.common.CommonAction;
import com.itheima.crm.cxf.Customer;
import com.itheima.crm.cxf.CustomerService;
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
public class FixedAreaAction extends CommonAction<FixedArea> {
    @Autowired
    private FixedAreaService fixedAreaService;

    //保存定区
    @Action(value = "fixedAreaAction_save", results = {@Result(name = "success", type = "redirect", location = "/pages/base/fixed_area.jsp")})
    public String save() throws Exception {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }

    //定区分页查询
    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws Exception {
        //构造一个Pageable对象作为分页的参数,提供给Spring data jpa 实现分页
        Pageable pageable = new PageRequest(getPage() - 1, getRows());

        Page<FixedArea> page = fixedAreaService.pageQuery(pageable);

        page2json(page, new String[]{"subareas", "couriers"});

        return NONE;
    }

    //注入CRM客户端代理对象,实现Webservice远程调用
    @Autowired
    private CustomerService crmClientProxy;

    //获取未关联到定区的客户
    @Action(value = "fixedAreaAction_findCustomersNotAssociation")
    public String findCustomersNotAssociation() throws Exception {
        List<Customer> list = crmClientProxy.findCustomersNotAssociation();
        list2json(list, null);
        return NONE;
    }

    //获取已关联到定区的客户
    @Action(value = "fixedAreaAction_findCustomersHasAssociation")
    public String findCustomersHasAssociation() throws Exception {
        List<Customer> list = crmClientProxy.findCustomersHasAssociation(getModel().getId());
        list2json(list, null);
        return NONE;
    }

    //属性驱动获得要绑定的客户id
    private List<Integer> customerIds;

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    //获取已关联到定区的客户,远程调用crm实现
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {@Result(name = "success", type = "redirect", location = "/pages/base/fixed_area.jsp")})
    public String assignCustomers2FixedArea() throws Exception {
        String fixedAreaId = getModel().getId();
        crmClientProxy.assignCustomers2FixedArea(fixedAreaId, customerIds);
        return SUCCESS;
    }

    /**
     * id	001
     * courierId	41
     * takeTimeId	2
     *
     * @return
     * @throws Exception
     */

    private Integer courierId;

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    private Integer takeTimeId;

    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    //定区关联快递员
    @Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {@Result(name = "success", type = "redirect", location = "/pages/base/fixed_area.jsp")})
    public String associationCourierToFixedArea() throws Exception {
        String fixedAreaId = getModel().getId();
        fixedAreaService.associationCourierToFixedArea(fixedAreaId, courierId,takeTimeId);
        return SUCCESS;
    }


}
