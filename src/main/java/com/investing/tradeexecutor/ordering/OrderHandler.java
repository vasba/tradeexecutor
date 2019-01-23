package com.investing.tradeexecutor.ordering;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderHandler {
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(OrderHandler.class);

	public static void buy(WebDriver driver, WebElement instrument, double limitAmount) throws InterruptedException {
		instrument.click();
		computeAmountAndOrder(driver, limitAmount);		
	}
	
	public static void buy(WebDriver driver, String instrument, double limitAmount) throws InterruptedException {
		driver.get(instrument);
		computeAmountAndOrder(driver, limitAmount);		
	}
	
	public static void computeAmountAndOrder(WebDriver driver, double limitAmount) throws InterruptedException {
		WebElement text = driver.findElement(By.className("sellPrice"));
		String sellPriceStr = text.getText();
		
		if (sellPriceStr.equals("-")) {
			return;
//			text = driver.findElement(By.className("lastPrice"));
//			sellPriceStr = text.getText();
		}
		
		sellPriceStr = sellPriceStr.replaceAll(",", ".");
		double sellPrice = Double.parseDouble(sellPriceStr) * 1.005;
		DecimalFormat df = new DecimalFormat("#.##");  
		sellPriceStr = df.format(sellPrice);
		sellPrice = Double.parseDouble(sellPriceStr);
				
		WebElement buyBtn = driver.findElement(By.className("buyBtn"));
		buyBtn.click();
//		String buyLink = "https://www.avanza.se/handla/warranter.html/kop/";
//		driver.get(buyLink + warantExtension);
		WebElement input = driver.findElement(By.name("price"));
		input.clear();
		input.sendKeys("" + sellPrice);
		int numberToBuy = new Double(limitAmount/sellPrice).intValue();
		
		WebElement amountInput = driver.findElement(By.name("volume"));
		amountInput.clear();
		amountInput.sendKeys("" + numberToBuy);
		Thread.sleep(1000);
		if (numberToBuy > 0) {
			LOGGER.info("buy, " + sellPrice);
		}
		Thread.sleep(3000);
		WebElement buyButton = driver.findElement(By.cssSelector("button.putorder.buy.buyBtn"));
		buyButton.click();
		LOGGER.info("clicked to buy ");
		Thread.sleep(5000);
	}
	
	public static void sell(WebDriver driver, HashMap<WebElement, Integer> holdings) throws InterruptedException {
		for (Entry<WebElement, Integer> entry : holdings.entrySet()) {
			WebElement key = entry.getKey();
			Integer value = entry.getValue();
			
			key.click();
			
			WebElement text = driver.findElement(By.className("buyPrice"));
			String sellPriceStr = text.getText();
			
			if (sellPriceStr.equals("-")) {
				return;
//				text = driver.findElement(By.className("lastPrice"));
//				sellPriceStr = text.getText();
			}
			
			sellPriceStr = sellPriceStr.replaceAll(",", ".");
			double sellPrice = Double.parseDouble(sellPriceStr) * 0.995;
			DecimalFormat df = new DecimalFormat("#.##");  
			sellPriceStr = df.format(sellPrice);
			sellPrice = Double.parseDouble(sellPriceStr);
			
			WebElement sellBtn = driver.findElement(By.className("sellBtn"));
			sellBtn.click();
			
			WebElement input = driver.findElement(By.name("price"));
			input.clear();
			input.sendKeys("" + sellPrice);
			
			WebElement amountInput = driver.findElement(By.name("volume"));
			amountInput.clear();
			amountInput.sendKeys("" + value);
			if (value > 0) {
				LOGGER.info("sell, " + sellPrice);
			}
			Thread.sleep(5000);
			WebElement buyButton = driver.findElement(By.cssSelector("button.putorder.sell.sellBtn"));
			buyButton.click();
			LOGGER.info("clicked to sell");
			Thread.sleep(1000);
		}		
	}
	
	public static void cancelPendingOrders(WebDriver driver, ArrayList<HashMap<String, WebElement>> ordersTable) {
	    for (HashMap<String, WebElement> order : ordersTable) {
	        WebElement cancelElem = order.get("");
	        List<WebElement> cancelButtons = cancelElem.findElements(By.cssSelector("a.icon.removeIcoSmall.deleteOrder"));
	        for (WebElement button : cancelButtons) {
				button.click();
				WebElement confirmButton = driver.findElement(By.cssSelector("button.focusBtn.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only"));
				confirmButton.click();
				int wait = 0;
			}
	    }
	}

}
