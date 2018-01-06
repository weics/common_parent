package com.itheima.bos.service.base;

import com.itheima.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CourierService {
    void save(Courier model);

    Page<Courier> pageQuery(Pageable pageable);

    void deleteBatch(String ids);

    Page<Courier> pageQuery(Specification<Courier> spe, Pageable pageable);

    List<Courier> listajax();

    void recoverBatch(String ids);
}
