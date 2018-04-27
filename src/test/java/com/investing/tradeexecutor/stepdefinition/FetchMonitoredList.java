package com.investing.tradeexecutor.stepdefinition;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FetchMonitoredList {
	
	final static String monitorsPage = "https://www.avanza.se/mina-sidor/bevakningslistor.html";
	
	
	public static List<WebElement> fetch(ActionType monitorType, String name, WebDriver driver) {
		String listName = monitorType.getOperation() + "_" + name; 
		driver.get(monitorsPage);
		List<WebElement> monitorsElements = driver.findElements(By.cssSelector("select.fLeft.select_watchlist.marginRight1_5"));
		WebElement monitorsElement = driver.findElement(By.cssSelector("select.fLeft.select_watchlist.marginRight1_5"));
		Select monitorsList = new Select(monitorsElement);
		List<WebElement> selectOptions = monitorsList.getOptions();
		for(WebElement option : selectOptions) {
			if (option.getText().equals(listName)) {
//				monitorsList.selectByVisibleText(option.getText());
				option.click();
				break;
			}
		}
		
		WebElement instrumentName = driver.findElement(By.cssSelector("td.instrumentName"));
		List<WebElement> linkElements = instrumentName.findElements(By.tagName("a"));	
//		WebElement link = instrumentName.findElement(By.cssSelector("a.link"));
//		ArrayList<WebElement> list = new ArrayList<WebElement>();
//		list.add(link);
		return linkElements;
	}

}
