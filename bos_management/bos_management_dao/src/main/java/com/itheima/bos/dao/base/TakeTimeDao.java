package com.itheima.bos.dao.base;

import com.itheima.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TakeTimeDao extends JpaRepository<TakeTime, Integer> {
    List<TakeTime> findAll();
}
