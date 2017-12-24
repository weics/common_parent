package com.itheima.crm.service;

import java.util.List;

import javax.jws.WebService;

import com.itheima.crm.domain.Customer;

@WebService
public interface CustomerService {
	public List<Customer> findAll();
	
	//查询未关联到定区的客户
	public List<Customer> findCustomersNotAssociation();
	
	//查询已经关联到[指定定区]的客户
	public List<Customer> findCustomersHasAssociation(String fixedAreaId);
	
	//将客户关联到定区
	public void assignCustomers2FixedArea(String fixedAreaId, Integer[] customerIds);
}
