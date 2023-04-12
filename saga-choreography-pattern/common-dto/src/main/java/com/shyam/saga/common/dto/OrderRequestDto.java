package com.shyam.saga.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

	private Integer userId;
	
	private Integer productId;
	
	private Integer ammount;
	
	private Integer orderId;
}
