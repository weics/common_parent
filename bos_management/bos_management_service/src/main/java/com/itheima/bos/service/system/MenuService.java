package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findTopMenu();

    void save(Menu model);
}
