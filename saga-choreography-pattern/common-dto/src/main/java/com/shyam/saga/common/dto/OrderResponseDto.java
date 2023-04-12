package com.shyam.saga.common.dto;

import com.shyam.saga.common.event.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

	private Integer userId;
	private Integer productId;
	private Integer ammount;
	private Integer orderId;
	private OrderStatus orderStatus;
}
