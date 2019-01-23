package com.investing.tradeexecutor.fetcher;

import org.openqa.selenium.WebDriver;

public class Checks {

	public static boolean checkNameOnDetailsPage(WebDriver driver, String name) throws Throwable 							
	{    		
		driver.get("https://www.avanza.se/mina-sidor/min-profil/mina-uppgifter/mina-uppgifter.html");	
		if(driver.getPageSource().toLowerCase().contains(name.toLowerCase())) {
			return true;
		}
		return false;
	}	
}
