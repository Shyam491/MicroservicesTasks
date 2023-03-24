package com.shyam.employeeservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shyam.employeeservice.Response.AddressResponse;

@FeignClient(name="address-service",url="http://localhost:8083/address-app/api")
public interface AddressServiceProxy {
	
	@GetMapping("/address/{id}")
	AddressResponse getAddressByEmployeeId(@PathVariable("id") int id); 

}
