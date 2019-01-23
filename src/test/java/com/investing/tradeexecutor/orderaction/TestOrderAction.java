package com.investing.tradeexecutor.orderaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.investing.tradeexecutor.fetcher.ActionType;
import com.investing.tradeexecutor.ordering.OrderAction;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class TestOrderAction {
	
	@Autowired
	OrderAction orderAction;

	@Test
	public void testBuy() throws InterruptedException {
		orderAction.order("OMX", ActionType.BUY);
		orderAction.order("OMX", ActionType.BUY);
	}
	
	@Test
	public void testSell() throws InterruptedException {
		orderAction.order("OMX", ActionType.SELL);
		orderAction.order("OMX", ActionType.SELL);
	}
}
