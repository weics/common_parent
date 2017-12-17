package com.itheima.bos.dao.base;

import com.itheima.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StandardDao extends JpaRepository<Standard, Integer> {
    //命名规范都是针对查询方法的
    public Standard save(Standard standard);

    public List<Standard> findByName(String name);

    public List<Standard> findByNameLike(String name);

    public List<Standard> findByIdAndName(Integer id, String name);

    //使用注解:Query指定SQL语句,默认是JPQL(HQL)
    @Query(value = "from Standard")
    public List<Standard> findByXxx();

    //nativeQuery = true,表示使用普通SQL语句
    @Query(value = "select * from t_standard where c_name=?", nativeQuery = true)
    public List<Standard> findByYyy(String name);

    //自己扩展非查询方法
    @Query(value = "update Standard set name = ? where id = ?")
    @Modifying
    public void updateNameById(String name, Integer id);
}
