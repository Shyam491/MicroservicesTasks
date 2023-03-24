package com.shyam.employeeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.employeeservice.Response.EmployeeResponse;
import com.shyam.employeeservice.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeesById(@PathVariable("id") int id) {
		EmployeeResponse employeeResponse =employeeService.getEmployeeById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
	}

}
