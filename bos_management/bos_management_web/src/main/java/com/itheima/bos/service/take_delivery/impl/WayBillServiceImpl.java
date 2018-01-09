package com.itheima.bos.service.take_delivery.impl;

import com.itheima.bos.dao.take_delivery.WayBillDao;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
    @Autowired
    private WayBillDao wayBillDao;

    //保存运单
    public void save(WayBill model) {
        wayBillDao.save(model);
    }
}
