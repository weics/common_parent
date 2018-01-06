package com.itheima.bos.service.take_delivery.impl;

import com.itheima.bos.dao.base.AreaDao;
import com.itheima.bos.dao.base.FixedAreaDao;
import com.itheima.bos.dao.take_delivery.OrderDao;
import com.itheima.bos.dao.take_delivery.WorkBillDao;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.take_delivery.OrderService;
import com.itheima.crm.cxf.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    //注入CRM客户端代理对象
    @Autowired
    private CustomerService crmClientProxy;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private FixedAreaDao fixedAreaDao;

    @Autowired
    private WorkBillDao workBillDao;

    //自动分单
    public void autoOrder(Order order) {
        System.out.println("后台系统自动分单逻辑...");
        order.setOrderTime(new Date());
        order.setOrderNum(UUID.randomUUID().toString());

        //将订单对象关联的区域对象改为持久状态
        Area sendArea = order.getSendArea();
        Area recArea = order.getRecArea();
        sendArea = areaDao.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
        recArea = areaDao.findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(), recArea.getDistrict());
        order.setSendArea(sendArea);
        order.setRecArea(recArea);

        //保存订单
        orderDao.save(order);//持久状态
        order.setOrderType("2");//人工分单

        //调用crm服务获取定区id
        String fixedAreaId = crmClientProxy.findFixedAreaIdByAddress(order.getSendAddress());
        if (StringUtils.isNotBlank(fixedAreaId)) {
            //查询到了定区id
            FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
            Set<Courier> couriers = fixedArea.getCouriers();
            //从集合中取出一个快递员负责区间,有一个算法,判断哪个快递员在上班,通过定位计算位置近的优先
            Iterator<Courier> iterator = couriers.iterator();
            Courier courier = iterator.next();
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

            workBillDao.save(workBill);

            //修改分单类型为:自动分单
            order.setOrderType("1");//自动分单

            //建立订单和快递员的关系
            order.setCourier(courier);//订单关联快递员
            return;
        }
        //第一种分单方法失败,没有完成自动分单,尝试第二种分单

        //获得当前区域中所有分区
        Set<SubArea> subareas = sendArea.getSubareas();
        for (SubArea subarea : subareas) {
            //分区关键字
            String keyWords = subarea.getKeyWords();
            //辅助关键字
            String assistKeyWords = subarea.getAssistKeyWords();

            String sendAddress = order.getSendAddress();
            if (sendAddress.contains(keyWords) || sendAddress.contains(assistKeyWords)) {
                FixedArea fixedArea = subarea.getFixedArea();
                Set<Courier> couriers = fixedArea.getCouriers();
                //从集合中取出一个快递员负责取件，有一个算法，判断哪个快递员在上班，通过定位计算位置近的优先
                Iterator<Courier> iterator = couriers.iterator();
                Courier courier = iterator.next();
                //为快递员发送短信
                System.out.println("工单信息：请到" + order.getSendAddress() + "取件，客户电话：" + order.getSendMobile());
                //为快递员产生工单
                WorkBill workBill = new WorkBill();
                workBill.setCourier(courier);//工单关联快递员
                workBill.setAttachbilltimes(0);
                workBill.setBuildtime(new Date());
                workBill.setOrder(order);//工单关联订单
                workBill.setPickstate("新单");//取件状态
                workBill.setRemark(order.getRemark());
                workBill.setSmsNumber(UUID.randomUUID().toString());
                workBill.setType("新");

                workBillDao.save(workBill);

                //修改分单类型为：自动分单
                order.setOrderType("1");//自动分单

                //建立订单和快递员的关系
                order.setCourier(courier);//订单关联快递员
                break;
            }
        }

        //两种方式都没有完成自动分单,需要设置分单类型为:人工分单
    }
}
