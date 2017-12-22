package com.itheima.bos.service.base.impl;

import com.itheima.bos.dao.base.CourierDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourierServiceImpl implements CourierService{
    @Autowired
    private CourierDao courierDao;

    //保存
    @Override
    public void save(Courier model) {
        courierDao.save(model);
    }

    //快递员分页查询
    @Override
    public Page<Courier> pageQuery(Pageable pageable) {
        return courierDao.findAll(pageable);
    }

    //批量删除
    @Override
    public void deleteBatch(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] cIds = ids.split(",");
            for (String cId : cIds) {
                courierDao.deleteById(Integer.parseInt(cId));
            }
        }
    }

    //快递员分页查询,有条件
    @Override
    public Page<Courier> pageQuery(Specification<Courier> spe, Pageable pageable) {
        return courierDao.findAll(spe,pageable);
    }
}