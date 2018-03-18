package com.tesla.springcloud.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tesla.springcloud.model.Employee;

@FeignClient(name="employee-producer")
public interface RemoteCallService {
	@RequestMapping(method=RequestMethod.GET, value="api/v1/employee")
	public Employee getData();
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/employee/{empId}")
    public Employee getEmployee(@PathVariable("empId") Integer empId);
 

}

