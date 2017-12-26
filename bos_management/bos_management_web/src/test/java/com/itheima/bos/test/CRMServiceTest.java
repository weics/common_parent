package com.itheima.bos.test;

import com.itheima.crm.cxf.Customer;
import com.itheima.crm.cxf.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CRMServiceTest {
    @Autowired
    private CustomerService crmClientProxy;

    @Test
    public void test1() {
        List<Customer> list = crmClientProxy.findAll();
        System.out.println(list);
    }
}
