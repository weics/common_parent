package com.itheima.bos.service.take_delivery;

import com.itheima.bos.domain.take_delivery.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface OrderService {
    //自动分单
    public void autoOrder(Order order);

    //查询人工调度订单
    //Page<Order> pageQuery(Specification<Order> spe, Pageable pageable);
}
