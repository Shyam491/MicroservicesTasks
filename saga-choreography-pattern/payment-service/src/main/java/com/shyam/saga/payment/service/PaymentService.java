package com.shyam.saga.payment.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyam.saga.common.dto.OrderRequestDto;
import com.shyam.saga.common.dto.PaymentRequestDto;
import com.shyam.saga.common.event.OrderEvent;
import com.shyam.saga.common.event.PaymentEvent;
import com.shyam.saga.common.event.PaymentStatus;
import com.shyam.saga.payment.entity.UserBalance;
import com.shyam.saga.payment.entity.UserTransaction;
import com.shyam.saga.payment.repository.UserBalanceRepository;
import com.shyam.saga.payment.repository.UserTransactionRepository;

@Service
public class PaymentService {

	@Autowired
	private UserBalanceRepository userBalanceRepository;
	@Autowired
	private UserTransactionRepository userTransactionRepository;
	
	@PostConstruct
	public void initUserBalanceInDB() {
		userBalanceRepository.saveAll(Stream.of(new UserBalance(101,5000),
				new UserBalance(102,3000),
				new UserBalance(103,4200),
				new UserBalance(104,20000),
				new UserBalance(105,999)).collect(Collectors.toList()));
	}
	
	//get the user id
	//check the balance availability
	//if balance sufficient -> Payment completed and deduct amount from DB
	//if payment not sufficient -> cancel order event and update the amount
	
	@Transactional
	public  PaymentEvent newOrderEvent(OrderEvent orderEvent) {
		OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
		PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),orderRequestDto.getUserId(),orderRequestDto.getAmmount());
		
		return userBalanceRepository.findById(orderRequestDto.getUserId())
		              .filter(ub->ub.getPrice()>orderRequestDto.getAmmount())
		              .map(ub->{
		            	  ub.setPrice(ub.getPrice()-orderRequestDto.getAmmount());
		            	  userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),orderRequestDto.getUserId(),orderRequestDto.getAmmount()));
		            	  return new PaymentEvent(paymentRequestDto,PaymentStatus.PAYMENT_COMPLETED);
		              }).orElse(new PaymentEvent(paymentRequestDto,PaymentStatus.PAYMENT_FAILED));
	}
	
	@Transactional
	public void cancelOrderEvent(OrderEvent orderEvent) {
//		System.out.println("2nd step checking...............");

        UserTransaction ut =  userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId()).get();

//        System.out.println("........................");
//        System.out.println(orderEvent.getOrderRequestDto().getUserId());
//        System.out.println("........................");
        UserBalance ub = userBalanceRepository.findById(orderEvent.getOrderRequestDto().getUserId()).get();

        ub.setPrice(ub.getPrice() + ut.getAmount());

        userTransactionRepository.deleteById(ut.getOrderId());
	}
	
}
