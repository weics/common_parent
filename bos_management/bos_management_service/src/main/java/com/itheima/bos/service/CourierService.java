package com.itheima.bos.service;

import com.itheima.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourierService {
    void save(Courier model);

    Page<Courier> pageQuery(Pageable pageable);

    void deleteBatch(String ids);
}
