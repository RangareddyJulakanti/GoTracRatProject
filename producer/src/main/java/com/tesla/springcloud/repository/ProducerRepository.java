package com.tesla.springcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tesla.springcloud.entity.EmployeeEntity;


public interface ProducerRepository extends JpaRepository<EmployeeEntity, Integer> {
	
	EmployeeEntity findById(Integer id);

}