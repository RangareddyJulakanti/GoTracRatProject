package com.tesla.springcloud;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tesla.springcloud.model.Employee;
import com.tesla.springcloud.services.RemoteCallService;

@RestController
public class ConsumerControllerClient {

	@Autowired
	private LoadBalancerClient loadBalancer;
	
	@Autowired
	RemoteCallService service;

	@RequestMapping(value = "/api/consumer/{id}", method = RequestMethod.GET)
	//@GetMapping(value = "/consumer/{id}")
	@HystrixCommand(fallbackMethod = "getDataFallBack")
	public Employee getEmployee(@PathVariable("id") Integer id) throws RestClientException, IOException {

		return service.getEmployee(id);
		/*ServiceInstance serviceInstance = loadBalancer.choose("employee-producer");

		System.out.println(serviceInstance.getUri());

		String baseUrl = serviceInstance.getUri().toString();

		baseUrl = baseUrl + "/employee/"+id;*/

		/*RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Employee> response = null;
		try {
			response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), Employee.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return response.getBody();*/
	}

	public Employee getDataFallBack(Integer id) {
		Employee emp = new Employee();
		emp.setName("fallback-emp1");
		return emp;

	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
}
