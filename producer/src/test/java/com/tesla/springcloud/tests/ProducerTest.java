package com.tesla.springcloud.tests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesla.springcloud.controller.TestController;
import com.tesla.springcloud.entity.EmployeeEntity;
import com.tesla.springcloud.model.Employee;
import com.tesla.springcloud.repository.ProducerRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
public class ProducerTest {
	
	final static Logger logger = Logger.getLogger(ProducerTest.class);

	@Autowired
	private MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	ProducerRepository producerRepo;
 
	@InjectMocks
	TestController testController;
	
	Employee emp = null;
	
	@Before
	public void setUp() {
		emp = new Employee();
		emp.setDesignation("designation");
		emp.setName("name");
		emp.setSalary(5000);
	}
	
	@Test
	public void testAddEmployee() throws Exception {
		EmployeeEntity entity = new EmployeeEntity();
		entity.setDesignation("designation");
		entity.setName("name");
		entity.setSalary(5000.0);
		entity.setId(1);
		when(producerRepo.save(any(EmployeeEntity.class))).thenReturn(entity);
		
		MvcResult mvcResult = mvc
				.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(emp)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();

		Employee response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				Employee.class);
		if (response != null) {
			logger.info(response.getEmpId());
			Assert.assertEquals(1, response.getEmpId());
		}
	}
	
	@Test
	public void testGetEmployee() throws Exception {
		
		EmployeeEntity entity = new EmployeeEntity();
		entity.setDesignation("designation");
		entity.setName("name");
		entity.setSalary(5000.0);
		entity.setId(1);
		when(producerRepo.findById(1)).thenReturn(entity);
		
		MvcResult mvcResult = mvc
				.perform(get("/employee/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();
		
		Employee response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				Employee.class);
		if (response != null) {
			logger.info(response.getEmpId());
			Assert.assertEquals(1, response.getEmpId());
		}

	}
}
