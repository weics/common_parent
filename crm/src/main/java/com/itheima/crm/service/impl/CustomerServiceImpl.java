package com.itheima.crm.service.impl;

import com.itheima.crm.dao.CustomerDao;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao dao;

    public List<Customer> findAll() {
        return dao.findAll();
    }

    //查询未关联到定区的客户
    public List<Customer> findCustomersNotAssociation() {
        return dao.findByFixedAreaIdIsNull();
    }

    //查询已经关联到[指定定区]的客户
    public List<Customer> findCustomersHasAssociation(String fixedAreaId) {
        return dao.findByFixedAreaId(fixedAreaId);
    }

    //将客户关联到定区
    public void assignCustomers2FixedArea(String fixedAreaId, Integer[] customerIds) {
        //1、清理数据，让当前定区不再关联任何客户
        dao.clearCustomer(fixedAreaId);

        if (customerIds != null && customerIds.length > 0) {
            for (Integer customerId : customerIds) {
                //2、重新提交的客户关联到定区
                dao.assignCustomers2FixedArea(fixedAreaId, customerId);
            }
        }
    }

    //根据手机号查询客户
    public Customer findByTelephone(String telephone) {
        return dao.findByTelephone(telephone);
    }

    //保存客户
    public void regist(Customer customer) {
        dao.save(customer);
    }

    //客户邮件激活
    public void activeMail(String telephone) {
        dao.activeMail(telephone);
    }

    //登录
    public Customer login(String telephone, String password) {
        return dao.findByTelephoneAndPassword(telephone, password);
    }

    //根据客户地址查询定区
    public String findFixedAreaIdByAddress(String address) {
        return dao.findFixedAreaIdByAddress(address);
    }
}
