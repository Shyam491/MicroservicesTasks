package com.shyam.saga.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shyam.saga.common.dto.OrderRequestDto;
import com.shyam.saga.common.event.OrderEvent;
import com.shyam.saga.common.event.OrderStatus;

import reactor.core.publisher.Sinks;

@Service
@Component
public class OrderStatusPublisher {

	@Autowired
	private Sinks.Many<OrderEvent> orderSinks;
	
	public void publishOrderEvent(OrderRequestDto orderRequestDto,OrderStatus orderStatus) {
		OrderEvent orderEvent = new OrderEvent(orderRequestDto,orderStatus);
		orderSinks.tryEmitNext(orderEvent);
	}
}
