package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.FixedAreaDao;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaDao fixedAreaDao;

    //保存定区
    public void save(FixedArea model) {
        fixedAreaDao.save(model);
    }
}
