package com.investing.tradeexecutor.fetcher;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FetchBalance {
	
	public static int fetchAccountBalance(WebDriver driver) {
		String overviewPage = "https://www.avanza.se/mina-sidor/kontooversikt.html";
		driver.get(overviewPage);
		WebElement buyingPower = driver.findElement(By.cssSelector("span.buyingPower"));
		String balanceStr = buyingPower.getText();
		String digits = balanceStr.replaceAll("[^0-9.,]", "");
		digits = digits.replaceAll(",", ".");
		return Integer.parseInt(digits);	
	}

}
