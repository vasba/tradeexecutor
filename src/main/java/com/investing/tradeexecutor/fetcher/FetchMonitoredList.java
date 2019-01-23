package com.investing.tradeexecutor.fetcher;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.investing.tradeexecutor.ordering.OrderHandler;

public class FetchMonitoredList {
	
	final static String monitorsPage = "https://www.avanza.se/mina-sidor/bevakningslistor.html";
	
	final static String longPage = "https://www.avanza.se/borshandlade-produkter/warranter-torg/om-warranten.html/636656/mini-l-omx-ava-95";
	final static String shortPage = "https://www.avanza.se/borshandlade-produkter/warranter-torg/om-warranten.html/672873/mini-s-omx-ava-129";
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(FetchMonitoredList.class);
	
	public static List<WebElement> fetch(ActionType monitorType, String name, WebDriver driver) throws InterruptedException {
		String listName = monitorType.getOperation() + "_" + name; 
		driver.get(monitorsPage);
		List<WebElement> monitorsElements = driver.findElements(By.cssSelector("select.fLeft.select_watchlist.marginRight1_5"));
		WebElement monitorsElement = driver.findElement(By.cssSelector("select.fLeft.select_watchlist.marginRight1_5"));
		Select monitorsList = new Select(monitorsElement);
		List<WebElement> selectOptions = monitorsList.getOptions();
		
		Thread.sleep(5000);
		for(WebElement option : selectOptions) {
			if (option.getText().equals(listName.toLowerCase())) {
//				monitorsList.selectByVisibleText(option.getText());
				option.click();
				LOGGER.info("Selected on: " + listName);
				break;
			}
		}
		Thread.sleep(2000);
		WebElement instrumentName = driver.findElement(By.cssSelector("td.instrumentName"));
		List<WebElement> linkElements = instrumentName.findElements(By.tagName("a"));	
//		WebElement link = instrumentName.findElement(By.cssSelector("a.link"));
//		ArrayList<WebElement> list = new ArrayList<WebElement>();
//		list.add(link);
		return linkElements;
	}
	
	public static String fetchAsLink(ActionType monitorType, String name, WebDriver driver) throws InterruptedException {
		if (name.toLowerCase().contains("omx")) {
			if (monitorType.equals(ActionType.BUY))
				return longPage;
			else
				return shortPage;
		}
		return "";
	}

}
