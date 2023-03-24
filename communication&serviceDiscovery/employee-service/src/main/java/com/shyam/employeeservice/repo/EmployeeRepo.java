package com.shyam.employeeservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.employeeservice.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

}
