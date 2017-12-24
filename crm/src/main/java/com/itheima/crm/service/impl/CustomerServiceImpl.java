package com.itheima.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerDao;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao dao;
	
	public List<Customer> findAll() {
		return dao.findAll();
	}

	//查询未关联到定区的客户
	public List<Customer> findCustomersNotAssociation() {
		return dao.findByFixedAreaIdIsNull();
	}

	//查询已经关联到[指定定区]的客户
	public List<Customer> findCustomersHasAssociation(String fixedAreaId) {
		return dao.findByFixedAreaId(fixedAreaId);
	}

	//将客户关联到定区
	public void assignCustomers2FixedArea(String fixedAreaId, Integer[] customerIds) {
		//1、清理数据，让当前定区不再关联任何客户
		dao.clearCustomer(fixedAreaId);
		
		if(customerIds != null && customerIds.length > 0){
			for (Integer customerId : customerIds) {
				//2、重新提交的客户关联到定区
				dao.assignCustomers2FixedArea(fixedAreaId,customerId);
			}
		}
	}
}
