package com.investing.tradeexecutor.stepdefinition;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FetchHoldings {
	
	final static String holdingPage = "https://www.avanza.se/mina-sidor/kontooversikt.html";
	
	final static String accountNumber = "";
	
	public static List<WebElement> fetchHoldings(WebDriver driver) {
		driver.get(holdingPage);
		List<WebElement> accounts = driver.findElements(By.className("accountLabel"));
		for (WebElement account : accounts) {
			List<WebElement> linkElements = account.findElements(By.tagName("a"));
			for (WebElement link : linkElements) {
				String linkText = link.getAttribute("title");
				if (linkText.equals(accountNumber)) {
					link.click();
					break;
				}
			}
		}
		
		List<WebElement> captions = driver.findElements(By.tagName("caption"));
		
		WebElement instrumentName = driver.findElement(By.cssSelector("td.instrumentName"));
		List<WebElement> linkElements = instrumentName.findElements(By.tagName("a"));	
		return linkElements;
	}

}
