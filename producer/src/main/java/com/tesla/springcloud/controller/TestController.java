package com.tesla.springcloud.controller;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tesla.springcloud.entity.EmployeeEntity;
import com.tesla.springcloud.model.Employee;
import com.tesla.springcloud.repository.ProducerRepository;

@RestController
@RequestMapping("/api/v1/")
public class TestController {
	
	final static Logger logger = Logger.getLogger(TestController.class);
	
	@Autowired
	ProducerRepository producerRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	//@PostMapping(value = "/employee")
	public Employee firstPost(@RequestBody Employee emp) {
		if(emp != null) {
			EmployeeEntity entityObj = modelMapper.map(emp, EmployeeEntity.class);
			EmployeeEntity entity = producerRepo.save(entityObj);
			Employee empObj = modelMapper.map(entity, Employee.class);
			empObj.setEmpId(entity.getId());
			return empObj;
		}else {
			throw new RuntimeException();
		}
		
		//return null;
	}
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	//@GetMapping(value = "/employee/{id}")
	@HystrixCommand(fallbackMethod = "getDataFallBack")
	public Employee firstPage(@PathVariable("id") String id) {
		Employee emp = new Employee();
		EmployeeEntity entity = producerRepo.findById(Integer.valueOf(id));
		
		if(entity != null) {
			emp = modelMapper.map(entity, Employee.class);
			emp.setEmpId(entity.getId());
//			if(emp.getEmpId().equalsIgnoreCase("1"))
//				throw new RuntimeException();
		}else {
			throw new RuntimeException();
		}
		return emp;
	}

	public Employee getDataFallBack(String id) {
		
		Employee emp = new Employee();
		emp.setName("fallback-emp1");
		emp.setDesignation("fallback-manager");
		emp.setEmpId(Integer.parseInt(id));
		emp.setSalary(3000);

		return emp;
		
	}
	
	/*@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	//@HystrixCommand(fallbackMethod = "getDataFallBack")
	public Employee secondPage(@PathVariable("id") Integer id) {

		Employee emp = new Employee();
		emp.setName("emp1");
		emp.setDesignation("manager");
		emp.setEmpId(id.toString());
		emp.setSalary(3000);
		
		if(emp.getName().equalsIgnoreCase("emp1"))
			throw new RuntimeException();

		return emp;
	}*/

	
}