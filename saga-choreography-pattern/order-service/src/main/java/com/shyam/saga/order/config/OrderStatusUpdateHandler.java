package com.shyam.saga.order.config;

import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.shyam.saga.common.dto.OrderRequestDto;
import com.shyam.saga.common.event.OrderStatus;
import com.shyam.saga.common.event.PaymentStatus;
import com.shyam.saga.order.entity.PurchaseOrder;
import com.shyam.saga.order.repository.OrderRepository;
import com.shyam.saga.order.service.OrderStatusPublisher;

@Configuration
public class OrderStatusUpdateHandler {

	@Autowired
	private OrderRepository repository;
	@Autowired
	private OrderStatusPublisher publisher;
	
	@Transactional
	public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
		repository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
	}
	
	private void updateOrder(PurchaseOrder purchaseOrder) {
		boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
		OrderStatus orderStatus =isPaymentComplete? OrderStatus.ORDER_COMPLETED:OrderStatus.ORDER_CANCELLED;
		purchaseOrder.setOrderStatus(orderStatus);
		if(!isPaymentComplete) {
			publisher.publishOrderEvent(convertEntityToDto(purchaseOrder),orderStatus);
		}
	}
	
	public OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderRequestDto.setOrderId(purchaseOrder.getId());
		orderRequestDto.setUserId(purchaseOrder.getUserId());
		orderRequestDto.setAmmount(purchaseOrder.getPrice());
		orderRequestDto.setProductId(purchaseOrder.getProductId());
		return orderRequestDto;
	}
	
	
}
