package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.CourierDao;
import com.itheima.bos.dao.base.FixedAreaDao;
import com.itheima.bos.dao.base.TakeTimeDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaDao fixedAreaDao;

    @Autowired
    private CourierDao courierDao;

    @Autowired
    private TakeTimeDao takeTimeDao;

    //保存定区
    public void save(FixedArea model) {
        fixedAreaDao.save(model);
    }

    //定区分页查询
    public Page<FixedArea> pageQuery(Pageable pageable) {
        return fixedAreaDao.findAll(pageable);
    }

    //定区关联快递员多对多,快递员关联收派时间多对一
    public void associationCourierToFixedArea(String fixedAreaId, Integer courierId, Integer takeTimeId) {
        FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
        Courier courier = courierDao.findOne(courierId);
        TakeTime takeTime = takeTimeDao.findOne(takeTimeId);

        //定区关联快递员多对多
        fixedArea.getCouriers().add(courier);

        //快递员关联收派时间多对一
        courier.setTakeTime(takeTime);
    }
}
