package com.investing.tradeexecutor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
	
	@RequestMapping("/buy")
	public void buy(@RequestParam(value="underlying", defaultValue="OMXS30") String underlying) {
//		get pending orders of short instruments for underlying 
//		cancel pending orders of short instruments for underlying
//		get holdings for underlying
//		sell short holdings for underlying
//		get value of long instruments for underlying
//		get remaining finance ceiling for underlying
//		calculate available amount, if less than ceiling then update ceiling
//		calculate amount of instruments to buy
//		buy long instruments
	}
	
	@RequestMapping("/sell")
	public void sell(@RequestParam(value="underlying", defaultValue="OMXS30") String underlying) {
//		get pending orders of long instruments for underlying
//		cancel pending orders of long instruments for underlying
//		get long holdings for underlying
//		sell holdings for underlying
//		get value of short instruments for underlying
//		get remaining finance ceiling for underlying
//		calculate available amount, if less than ceiling then update ceiling
//		calculate amount of instruments to buy
//		buy short instruments
	}

}
