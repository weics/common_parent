package com.itheima.bos.service.system.impl;

import com.itheima.bos.dao.system.MenuDao;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    //查询所有菜单
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    //查询顶级菜单
    public List<Menu> findTopMenu() {
        return menuDao.findByParentMenuIsNull();
    }

    //添加菜单数据
    public void save(Menu model) {
        //如果当前添加的是顶级菜单,需要进行特殊处理
        if (model.getParentMenu() != null && model.getParentMenu().getId() == null) {
            model.setParentMenu(null);
        }
        menuDao.save(model);
    }
}
