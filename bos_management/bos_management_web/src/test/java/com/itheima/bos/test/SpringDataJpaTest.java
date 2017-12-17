package com.itheima.bos.test;

import com.itheima.bos.dao.base.StandardDao;
import com.itheima.bos.domain.base.Standard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringDataJpaTest {
    //注入Dao
    @Autowired
    private StandardDao standardDao;

    @Test
    public void test1() {
        Standard entity = new Standard();
        entity.setName("jack");
        standardDao.findByName(entity.getName());
    }

    @Test
    public void test2() {
        Standard entity = new Standard();
        entity.setName("jack");
        standardDao.findByNameLike(entity.getName());
    }

    @Test
    public void test3() {
        Standard entity = new Standard();
        entity.setName("jack");
        standardDao.findByIdAndName(1, "jack");
    }

    @Test
    public void test4() {
        Standard entity = new Standard();
        entity.setName("jack");
        standardDao.findByXxx();
    }

    @Test
    public void test5() {
        Standard entity = new Standard();
        entity.setName("jack");
        standardDao.findByYyy("jack");
    }

    @Test
    @Transactional
    public void test6() {
        Standard entity = new Standard();
        entity.setName("jack");
        standardDao.updateNameById("rose", 1);
    }

    @Test
    public void test7() {
        Standard entity = new Standard();
        entity.setName("jack");
        standardDao.save(entity);
    }

}
