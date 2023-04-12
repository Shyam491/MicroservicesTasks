package com.shyam.saga.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.saga.payment.entity.UserBalance;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer>{

}
