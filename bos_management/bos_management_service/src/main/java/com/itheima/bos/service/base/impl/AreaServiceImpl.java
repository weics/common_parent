package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.AreaDao;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    //区域分页查询
    @Override
    public Page<Area> pageQuery(Pageable pageable) {
        return areaDao.findAll(pageable);
    }

    //查询所有数据
    public List<Area> findAll() {
        return areaDao.findAll();
    }
}
