package com.investing.tradeexecutor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.investing.tradeexecutor.fetcher.ActionType;
import com.investing.tradeexecutor.ordering.OrderAction;

@RestController
public class OrderController {
	
	@Autowired
	OrderAction orderAction;
	
	@RequestMapping("/buy")
	public void buy(@RequestParam(value="underlying", defaultValue="OMX") String underlying) throws InterruptedException {
		orderAction.order(underlying, ActionType.BUY);
	}
	
	@RequestMapping("/sell")
	public void sell(@RequestParam(value="underlying", defaultValue="OMX") String underlying) throws InterruptedException {		
//		get pending orders of long instruments for underlying
//		cancel listed pending orders 
//		get long holdings for underlying
//		sell holdings for underlying
//		get value of short instruments for underlying
//		get remaining finance ceiling for underlying
//		calculate available amount, if less than ceiling then update ceiling
//		calculate amount of instruments to buy
//		buy short instruments
		orderAction.order(underlying, ActionType.SELL);
	}
}
