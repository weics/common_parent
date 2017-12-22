package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.SubAreaDao;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
    @Autowired
    private SubAreaDao dao;

    //保存一个区域
    public void save(SubArea model) {
        model.setId(UUID.randomUUID().toString());
        dao.save(model);
    }
}
