package com.investing.tradeexecutor.buyrunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.investing.tradeexecutor.fetcher.ActionType;
import com.investing.tradeexecutor.fetcher.FetchBalance;
import com.investing.tradeexecutor.fetcher.FetchHoldings;
import com.investing.tradeexecutor.fetcher.FetchMonitoredList;
import com.investing.tradeexecutor.fetcher.FetchPendingOrders;
import com.investing.tradeexecutor.ordering.OrderHandler;
import com.investing.tradeexecutor.startup.StartUp;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class BuySteps {
	
	@Autowired
	StartUp startUp;
	
	ArrayList<HashMap<String, WebElement>> pendingOrdersTable;
	HashMap<WebElement, Integer> shortHoldings;
	HashMap<WebElement, Double> shortHoldingsValue;
	HashMap<WebElement, Double> instrumetCeilingValue = new HashMap<>();
	
	@Given("^Open the Firefox and launch the application$")			
	public void open_the_Firefox_and_launch_the_application() throws Throwable							
	{				
				
	}
	
//	get pending orders of short instruments for underlying 
	@When("^Get pending orders$")
	public void getPendingOrders() {
		pendingOrdersTable = FetchPendingOrders.fetchPendingOrders(startUp.getDriver(), "OMX", ActionType.SELL);
	}

	//	cancel listed pending orders
	@When("^Cancel listed pending orders$")
	public void cancelPendingOrders() {
		OrderHandler.cancelPendingOrders(startUp.getDriver(), pendingOrdersTable);
	}
	
//	get short holdings for underlying
	@When("^Get short holdings for underlying$")
	public void getShortHoldings() {
		shortHoldings = FetchHoldings.fetchHoldings(startUp.getDriver(), "OMX", ActionType.SELL);
	}
	
//	sell short holdings for underlying	
	@When("^Sell short holdings for underlying$")
	public void sellShortHoldings() throws InterruptedException {
		OrderHandler.sell(startUp.getDriver(), shortHoldings);
	}
	
//	get value of long instruments for underlying
	@When("^Get value of long instruments for underlying$")
	public void getValueOfLongInstruments() {
		shortHoldingsValue = FetchHoldings.fetchHoldingsValue(startUp.getDriver(), "OMX", ActionType.BUY);
		
	} 
	
//	get remaining finance ceiling for underlying
	@When("^Get remaining finance ceiling for underlying$")
	public void getRemainingFinanceCeilingForUnderlying() {
		for (Entry<WebElement, Double> entry : shortHoldingsValue.entrySet()) {
			WebElement key = entry.getKey();
			Double value = entry.getValue();
			instrumetCeilingValue.put(key, 3000 - value);
		}
	}
	
//	calculate available amount, if less than ceiling then update ceiling
	@When("^calculate available amount per instrument$")
	public void availableAmountPerInstrument() {
		// not sure why I need this
		// we will have equal amount allocated per instrument
		// if we fail to buy it means that we failed to sell
		// and create available amount
	}
	
//	calculate amount of instruments to buy
	
	
//	buy long instruments
	@Then("^Buy Instruments$")
	public void buyInstruments() throws InterruptedException {
		double totalAvailableAmount = 0;
		for (Entry<WebElement, Double> entry : instrumetCeilingValue.entrySet()) {
			WebElement key = entry.getKey();
			Double value = entry.getValue();
			totalAvailableAmount += value;
		}

		int balance = FetchBalance.fetchAccountBalance(startUp.getDriver());
		if (totalAvailableAmount > balance) {
			totalAvailableAmount = balance;
		}

		List<WebElement> instrumetsLink = FetchMonitoredList.fetch(ActionType.BUY, "OMX", startUp.getDriver());

		double amountPerInstrument = totalAvailableAmount/instrumetsLink.size(); 

		for (WebElement element : instrumetsLink) 
			OrderHandler.buy(startUp.getDriver(), element, amountPerInstrument);
	}
}
