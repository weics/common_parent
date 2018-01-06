package com.itheima.crm.service;

import com.itheima.crm.domain.Customer;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface CustomerService {
    public List<Customer> findAll();

    //查询未关联到定区的客户
    public List<Customer> findCustomersNotAssociation();

    //查询已经关联到[指定定区]的客户
    public List<Customer> findCustomersHasAssociation(String fixedAreaId);

    //将客户关联到定区
    public void assignCustomers2FixedArea(String fixedAreaId, Integer[] customerIds);

    //根据手机号查询客户
    Customer findByTelephone(String telephone);

    //保存客户
    void regist(Customer customer);

    //客户激活邮件
    void activeMail(String telephone);

    //登录
    public Customer login(String telephone, String password);

    //根据客户地址查询定区
    public String findFixedAreaIdByAddress(String address);
}
