package com.itheima.bos.dao.base;

import com.itheima.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourierDao extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {
    @Query(value = "update Courier set deltag ='1' where id=?")
    @Modifying
    void deleteById(int id);

    /**
     * 根据deltag进行查询,如果参数为0表示查询未删除的快递员,如果为1表示查询已删除的快递员
     * 注意:要将实体类Courier中deltag属性指定一个默认值'0'!!!
     *
     * @param deltag
     * @return
     */
    List<Courier> findByDeltag(Character deltag);

    @Query(value = "update Courier set deltag='0' where id=?")
    @Modifying
    void recoverById(int cId);
}
