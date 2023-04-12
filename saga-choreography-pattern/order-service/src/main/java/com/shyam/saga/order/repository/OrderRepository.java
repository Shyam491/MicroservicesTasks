package com.shyam.saga.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.saga.order.entity.PurchaseOrder;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {

}
