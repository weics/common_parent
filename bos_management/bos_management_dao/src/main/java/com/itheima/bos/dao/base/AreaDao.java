package com.itheima.bos.dao.base;

import com.itheima.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AreaDao extends JpaRepository<Area,String>{

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
