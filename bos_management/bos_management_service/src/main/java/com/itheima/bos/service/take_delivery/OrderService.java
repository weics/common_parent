package com.itheima.bos.service.take_delivery;

import com.itheima.bos.domain.take_delivery.Order;

import javax.jws.WebService;

@WebService
public interface OrderService {
    //自动分单
    public void autoOrder(Order order);
}
