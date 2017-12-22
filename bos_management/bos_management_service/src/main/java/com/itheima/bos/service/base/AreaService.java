package com.itheima.bos.service.base;

import com.itheima.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface AreaService {
    void save(ArrayList<Area> list);

    Page<Area> pageQuery(Pageable pageable);
}
