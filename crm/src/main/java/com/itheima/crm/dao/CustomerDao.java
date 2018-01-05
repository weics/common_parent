package com.itheima.crm.dao;

import com.itheima.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
    //查询未关联到定区的客户  SQL--->>select * from t_customer where c_fixed_area_id is null
    public List<Customer> findByFixedAreaIdIsNull();

    //查询已经关联到[指定定区]的客户 SQL--->>select * from t_customer where c_fixed_area_id = ?
    public List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    public void clearCustomer(String fixedAreaId);

    @Query("update Customer set fixedAreaId = ? where id = ?")
    @Modifying
    public void assignCustomers2FixedArea(String fixedAreaId, Integer customerId);

    Customer findByTelephone(String telephone);

    @Query("update Customer set type=1 where telephone=?")
    @Modifying
    void activeMail(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);
}
