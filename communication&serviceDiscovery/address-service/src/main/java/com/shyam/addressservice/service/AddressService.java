package com.shyam.addressservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyam.addressservice.model.Address;
import com.shyam.addressservice.repo.AddressRepo;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepo addressRepo;
	
	public Address findEmployeeByEmployeeId(int employeeId) {
		
		System.out.println("finding address for employee "+employeeId);
		Address address = addressRepo.findAddressByEmployeeId(employeeId);
		
		return address;
	}
	
	
}
