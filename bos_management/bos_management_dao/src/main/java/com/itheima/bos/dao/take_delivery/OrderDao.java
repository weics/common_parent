package com.itheima.bos.dao.take_delivery;

import com.itheima.bos.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {
}
