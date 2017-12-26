package com.itheima.crm.service.impl;

import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class FindAllTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void test1() {
        List<Customer> list = customerService.findAll();
        System.out.println(list);
    }
}
