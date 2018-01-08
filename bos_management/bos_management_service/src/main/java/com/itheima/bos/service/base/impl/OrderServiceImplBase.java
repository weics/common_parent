package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.OrderDaoBase;
import com.itheima.bos.dao.take_delivery.WorkBillDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.service.base.OrderServiceBase;
import com.itheima.bos.utils.SMSUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImplBase implements OrderServiceBase {

    @Autowired
    private OrderDaoBase orderDao;

    @Autowired
    private CourierService courierService;

    @Autowired
    private WorkBillDao workBillDao;

    //查询人工调度订单
    public Page<Order> pageQuery(Specification<Order> spe, Pageable pageable) {
        return orderDao.findAll(spe, pageable);
    }

    //根据id查询订单
    public Order findByOrderNum(String orderNum) {
        return orderDao.findByOrderNum(orderNum);
    }

    //人工分单
    public void handlerOrder(String orderNums, String courierId) {
        //查询所有订单
        if (StringUtils.isNotBlank(orderNums)) {
            String[] orderNumArray = orderNums.split(",");
            for (String orderNum : orderNumArray) {
                Order order = findByOrderNum(orderNum);


                //查询快递员
                Courier courier = courierService.findById(courierId);
                //为快递员发送短信

                System.out.println("工单信息：请到" + order.getSendAddress() + "取件，客户电话：" + order.getSendMobile());

                //为快递员产生工单
                WorkBill workBill = new WorkBill();
                workBill.setCourier(courier);//工单关联快递员
                workBill.setAttachbilltimes(0);
                workBill.setBuildtime(new Date());
                workBill.setOrder(order);//关联订单
                workBill.setPickstate("新单");//取件状态
                workBill.setRemark(order.getRemark());
                workBill.setSmsNumber(UUID.randomUUID().toString());
                workBill.setType("新");

                //保存工单
                workBillDao.save(workBill);

                //建立订单和快递员的关系
                order.setCourier(courier);//订单关联快递员

            }
        }
    }

}
