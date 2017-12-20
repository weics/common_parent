package com.itheima.bos.service;

import com.itheima.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CourierService {
    void save(Courier model);

    Page<Courier> pageQuery(Pageable pageable);

    void deleteBatch(String ids);

    Page<Courier> pageQuery(Specification<Courier> spe, Pageable pageable);
}
