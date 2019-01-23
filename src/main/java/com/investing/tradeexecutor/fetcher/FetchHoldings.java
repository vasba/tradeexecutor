package com.investing.tradeexecutor.fetcher;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FetchHoldings {
	
	final static String holdingPage = "https://www.avanza.se/mina-sidor/kontooversikt.html";
	
	final static String accountNumber = "3787290";
	
	public static HashMap<WebElement, Integer> fetchHoldings(WebDriver driver, String underlying, ActionType type) {
		switchToAccountPage(driver);
		
//      find table which contains the keyword
		WebElement holdingsElement = driver.findElement(By.cssSelector("div.content"));
     
		HashMap<WebElement, Integer> linkElements = new HashMap<>();
		List<WebElement> instruments = holdingsElement.findElements(By.xpath(".//*//td[@class='instrumentName']"));
		for (WebElement instrumentName : instruments) {
			try {
				WebElement linkElement = instrumentName.findElement(By.tagName("a"));
				if (linkElement != null) {				
					String text = instrumentName.getText();	
					String underlyingType = getTypeString(type) + underlying;
					if (text.toLowerCase().contains(underlyingType.toLowerCase())) {
						WebElement parent = instrumentName.findElement(By.xpath(".."));
						List<WebElement> cellElements = parent.findElements(By.tagName("td"));
						String amount = "";
						int index = 0;
						for (WebElement cellElement: cellElements) {
							if (index == 3) {
								amount = cellElement.getText();					
								linkElements.put(linkElement, Integer.parseInt(amount));
							}
							index++;
						}	

						boolean wait = true;
					}
				}
			} catch(NoSuchElementException e) {

			}
		}
		return linkElements;
	}

	public static void switchToAccountPage(WebDriver driver) {
		driver.get(holdingPage);
		List<WebElement> accounts = driver.findElements(By.className("accountLabel"));
		for (WebElement account : accounts) {
			List<WebElement> linkElements = account.findElements(By.tagName("a"));
			for (WebElement link : linkElements) {
				String linkText = link.getAttribute("title");
				if (linkText.equals(accountNumber)) {
					link.click();
					return;
				}
			}
		}
	}
	
	public static String getTypeString(ActionType type) {
		String result = " S ";
		
		if (type == ActionType.BUY)
			result = " L ";
		
		return result;
	}
	
	public static HashMap<WebElement, Double> fetchHoldingsValue(WebDriver driver, String underlying, ActionType type) {
		switchToAccountPage(driver);
		
//      find table which contains the keyword
		WebElement holdingsElement = driver.findElement(By.cssSelector("div.content"));
      
		HashMap<WebElement, Double> linkElements = new HashMap<>();
		List<WebElement> instruments = holdingsElement.findElements(By.xpath(".//*//td[@class='instrumentName']"));
		for (WebElement instrumentName : instruments) {
			try {
				WebElement linkElement = instrumentName.findElement(By.tagName("a"));
				if (linkElement != null) {				
					String text = instrumentName.getText();	
					String underlyingType = getTypeString(type) + underlying;
					if (text.toLowerCase().contains(underlyingType.toLowerCase())) {
						WebElement parent = instrumentName.findElement(By.xpath(".."));
						List<WebElement> cellElements = parent.findElements(By.tagName("td"));
						int amount = 0;
						int index = 0;
						for (WebElement cellElement: cellElements) {
							if (index == 3) {
								amount = Integer.parseInt(cellElement.getText());					
							}
							if (index == 7) {
								String priceString = cellElement.getText();
								priceString = priceString.replaceAll(",", ".");
								double boughtPrice = Double.parseDouble(priceString); 
								linkElements.put(linkElement, amount*boughtPrice);
							}
							index++;
						}	

						boolean wait = true;
					}
				}
			} catch(NoSuchElementException e) {

			}
		}
		return linkElements;
	}
}
