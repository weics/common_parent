package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.StandardDao;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "standardService")
@Transactional
public class StandardServiceImpl implements StandardService {
    //注入Dao
    @Autowired
    private StandardDao standardDao;

    //添加收派标准
    @Override
    public void save(Standard model) {
        standardDao.save(model);
    }
}
