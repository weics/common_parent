package com.itheima.bos.service.base;

import com.itheima.bos.domain.take_delivery.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface OrderServiceBase {

    //查询人工调度订单
    Page<Order> pageQuery(Specification<Order> spe, Pageable pageable);

    Order findByOrderNum(String orderNum);

    void handlerOrder(String orderNums, String courierId);
}
