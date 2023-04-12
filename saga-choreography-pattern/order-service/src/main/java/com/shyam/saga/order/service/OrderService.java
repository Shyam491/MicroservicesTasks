package com.shyam.saga.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyam.saga.common.dto.OrderRequestDto;
import com.shyam.saga.common.event.OrderStatus;
import com.shyam.saga.order.entity.PurchaseOrder;
import com.shyam.saga.order.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderStatusPublisher orderStatusPublisher;
	
	@Transactional
	public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
		PurchaseOrder order = orderRepository.save(convertDtoToEntity(orderRequestDto));
		orderRequestDto.setOrderId(order.getId());
		//produce kafka event with status ORDER_CREATED
		orderStatusPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
		return order;
	}
	
	public void deleteOrderById(Integer id) {
        PurchaseOrder purchaseOrder = orderRepository.findById(id).get();


        orderRepository.deleteById(id);

        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CANCELLED);
        orderRepository.save(purchaseOrder);

        OrderRequestDto orderRequestDTO = new OrderRequestDto(purchaseOrder.getUserId(), purchaseOrder.getProductId(),purchaseOrder.getPrice(),purchaseOrder.getId());

        orderStatusPublisher.publishOrderEvent(orderRequestDTO,OrderStatus.ORDER_CANCELLED);


    }
	public List<PurchaseOrder> getAllOrders(){
		return orderRepository.findAll();
	}
	
	private PurchaseOrder convertDtoToEntity(OrderRequestDto dto) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setProductId(dto.getProductId());
		purchaseOrder.setUserId(dto.getUserId());
		purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
		purchaseOrder.setPrice(dto.getAmmount());
		
		return purchaseOrder;
	}

}
