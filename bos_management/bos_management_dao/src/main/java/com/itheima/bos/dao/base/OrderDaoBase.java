package com.itheima.bos.dao.base;

import com.itheima.bos.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDaoBase extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    Order findByOrderNum(String orderNum);
}
