package com.shyam.addressservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.addressservice.model.Address;
import com.shyam.addressservice.service.AddressService;

@RestController
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/address/{employeeId}")
	public ResponseEntity<Address> getAddressByEmployeeId(@PathVariable("employeeId") int employeeId) {
		
		Address address = addressService.findEmployeeByEmployeeId(employeeId);
		return ResponseEntity.status(HttpStatus.OK).body(address);
		
	}
}
