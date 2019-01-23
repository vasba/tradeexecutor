package com.investing.tradeexecutor.ordering;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.investing.tradeexecutor.fetcher.ActionType;
import com.investing.tradeexecutor.fetcher.FetchBalance;
import com.investing.tradeexecutor.fetcher.FetchHoldings;
import com.investing.tradeexecutor.fetcher.FetchMonitoredList;
import com.investing.tradeexecutor.fetcher.FetchPendingOrders;
import com.investing.tradeexecutor.startup.StartUp;

@Component
public class OrderAction {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(OrderAction.class);
	
	@Autowired
	StartUp startUp;
	
	public void order(String underlying, ActionType type) throws InterruptedException {
		startUp.orderPending = true;
		WebDriver driver = startUp.getDriver();
				
		if (isExchangeClosed()) {
			HashMap<WebElement, Integer> holdings = FetchHoldings.fetchHoldings(driver, underlying, ActionType.SELL);
			OrderHandler.sell(startUp.getDriver(), holdings);
			holdings = FetchHoldings.fetchHoldings(driver, underlying, ActionType.BUY);
			OrderHandler.sell(startUp.getDriver(), holdings);
			return;
		}
		
		ActionType otherActionType = getOtherActionType(type);
//		get pending orders of short instruments for underlying 
		ArrayList<HashMap<String, WebElement>>  pendingOrdersTable = FetchPendingOrders.fetchPendingOrders(driver, underlying, otherActionType);
		
		while (pendingOrdersTable.size() > 0) {
//		cancel listed pending orders
			LOGGER.info("Looping order canceling");
			OrderHandler.cancelPendingOrders(driver, pendingOrdersTable);
			pendingOrdersTable = FetchPendingOrders.fetchPendingOrders(driver, underlying, otherActionType);
		}
		
//		get short holdings for underlying		
		HashMap<WebElement, Integer> shortHoldings = FetchHoldings.fetchHoldings(driver, underlying, otherActionType);
		while (shortHoldings.size() > 0) {
			LOGGER.info("Looping holdings selling");
//		sell short holdings for underlying
			OrderHandler.sell(startUp.getDriver(), shortHoldings);
			shortHoldings = FetchHoldings.fetchHoldings(driver, underlying, otherActionType);
		}
//		get value of long instruments for underlying
		HashMap<WebElement, Double> holdingsValue = FetchHoldings.fetchHoldingsValue(driver, underlying, type);
//		get remaining finance ceiling for underlying
		double totalAvailableAmount = 1500;
//		HashMap<WebElement, Double> instrumetCeilingValue = new HashMap<>();
		for (Entry<WebElement, Double> entry : holdingsValue.entrySet()) {
//			WebElement key = entry.getKey();	
			Double value = entry.getValue();
			totalAvailableAmount -= value;
//			instrumetCeilingValue.put(key, 1500 - value);
		}
		// not sure why I need this
		// we will have equal amount allocated per instrument
		// if we fail to buy it means that we failed to sell
		// and create available amount
		//		calculate available amount, if less than ceiling then update ceiling
//		calculate amount of instruments to buy
		
		
//		buy long instruments
//		double totalAvailableAmount = 1500;
//		for (Entry<WebElement, Double> entry : instrumetCeilingValue.entrySet()) {
//			WebElement key = entry.getKey();
//			Double value = entry.getValue();
//			totalAvailableAmount += value;
//		}

		int balance = FetchBalance.fetchAccountBalance(startUp.getDriver());
		if (totalAvailableAmount > balance) {
			totalAvailableAmount = balance;
		}

//		List<WebElement> instrumetsLink = FetchMonitoredList.fetch(type, underlying, driver);		
		LOGGER.info("fetched instruments to buy for type: " + type);
//		LOGGER.info("Instruments links count: " + instrumetsLink.size());
//		double amountPerInstrument = totalAvailableAmount/instrumetsLink.size(); 
//
//		for (WebElement element : instrumetsLink) 
//			OrderHandler.buy(driver, element, amountPerInstrument);
		
		String link = FetchMonitoredList.fetchAsLink(type, underlying, driver);
		OrderHandler.buy(driver, link, totalAvailableAmount);
		startUp.orderPending = false;
	}
	
	public ActionType getOtherActionType(ActionType type) {
		if (type == ActionType.BUY)
			return ActionType.SELL;
		
		return ActionType.BUY;
	}
	
	public boolean isExchangeClosed() {
		LocalDateTime nowTime = LocalDateTime.now();		
		LocalDate dateToday = LocalDate.now();
		LocalDateTime dateHour = dateToday.atTime(17, 15);
		if (nowTime.compareTo(dateHour) > 0) {
			return true;
		}
		
		return false;
	}
}
