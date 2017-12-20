package com.itheima.bos.dao;

import com.itheima.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourierDao extends JpaRepository<Courier, Integer> {
    @Query(value = "update Courier set deltag ='1' where id=?")
    @Modifying
    void deleteById(int id);
}
