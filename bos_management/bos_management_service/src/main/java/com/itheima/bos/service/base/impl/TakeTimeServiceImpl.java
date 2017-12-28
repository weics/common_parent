package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.TakeTimeDao;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeDao takeTimeDao;

    //获取所有收派时间
    public List<TakeTime> findAll() {
        return takeTimeDao.findAll();
    }
}
