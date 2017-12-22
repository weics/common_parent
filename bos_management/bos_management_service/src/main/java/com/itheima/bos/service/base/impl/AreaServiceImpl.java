package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.AreaDao;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    //区域数据批量导入
    @Override
    public void save(ArrayList<Area> list) {
        areaDao.save(list);
    }
}
