package com.shyam.employeeservice.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shyam.employeeservice.Response.AddressResponse;
import com.shyam.employeeservice.Response.EmployeeResponse;
import com.shyam.employeeservice.model.Employee;
import com.shyam.employeeservice.proxy.AddressServiceProxy;
import com.shyam.employeeservice.repo.EmployeeRepo;


@Service
public class EmployeeService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private EmployeeRepo employeeRepo;

//	@Autowired
//	private DiscoveryClient discoveryClient;
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	@Autowired
	private AddressServiceProxy addressServiceProxy;

	public EmployeeResponse getEmployeeById(int id) {

		Employee employee = employeeRepo.findById(id).orElseThrow();
		EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);

		
		AddressResponse addressResponse =getAddressResponseByRESTTemplate(id);
		employeeResponse.setAddressResponse(addressResponse);

		return employeeResponse;
	}

	
	
	public AddressResponse getAddressResponseByRESTTemplate(int id) {
//		List<ServiceInstance> instances = discoveryClient.getInstances("address-service");
//		ServiceInstance serviceInstance = instances.get(0);
//		String uri = serviceInstance.getUri().toString()+"/address-app/api/address/{id}";
		
		ServiceInstance serviceInstance = loadBalancerClient.choose("address-service");
		String uri = serviceInstance.getUri().toString();
//		String contextRoot= serviceInstance.getMetadata().get("configPath");
		
		System.out.println("uri---->"+uri);
//		System.out.println(contextRoot);
		return restTemplate.getForObject(uri+"/address-service/api/address/{id}", AddressResponse.class,id);
	}
	
	public AddressResponse getAddressResponseByFeignClient(int id) {
		return  addressServiceProxy.getAddressByEmployeeId(id);
	}

}
