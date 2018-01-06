package com.itheima.bos.service.take_delivery.impl;

import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.take_delivery.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    //自动分单
    public void autoOrder(Order order) {
        System.out.println("后台系统自动分单逻辑...");
    }
}
