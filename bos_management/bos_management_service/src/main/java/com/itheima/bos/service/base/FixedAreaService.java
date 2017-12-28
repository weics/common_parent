package com.itheima.bos.service.base;

import com.itheima.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FixedAreaService {
    void save(FixedArea model);

    Page<FixedArea> pageQuery(Pageable pageable);

    void associationCourierToFixedArea(String fixedAreaId, Integer courierId, Integer takeTimeId);
}
