package com.shyam.saga.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.saga.common.dto.OrderRequestDto;
import com.shyam.saga.order.entity.PurchaseOrder;
import com.shyam.saga.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/create")
	public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto) {
		return orderService.createOrder(orderRequestDto);
	}
	
	@GetMapping
	public List<PurchaseOrder> getOrders(){
		return orderService.getAllOrders();
	}
	

    @DeleteMapping("/delete/{id}")
    public String deleteOrders(@PathVariable Integer id){
        orderService.deleteOrderById(id);
        return "Order Cancelled";
    }
}
