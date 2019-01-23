package com.investing.tradeexecutor.startup;

import java.io.File;
import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.handler.ImplicitlyWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.joran.action.TimestampAction;

@Component
public class StartUp {
	
	static WebDriver driver;
	
	static public boolean orderPending = false;
	
	@Value("${avanza.personalNumber}")
	String personalNumber;
	
	@Value("${avanza.name}")
	String avanzaName;

	public String getAvanzaName() {
		return avanzaName;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public StartUp() {
		super();
		
		
	}
	
	@PostConstruct
	public void init() {
		openFirefoxAndStartApplication();
		enterThePersonalNumberAndLogin();
	}
	
	public void openFirefoxAndStartApplication() {
		File gecko = new File("/home/evasiba/gitlab/tradeexecutor/geckodriver");    
		System.setProperty("webdriver.gecko.driver", gecko.getAbsolutePath());
		
		driver= new FirefoxDriver();					
		driver.manage().window().maximize();			
		driver.get("https://www.avanza.se/start/forsta-oinloggad.html");
	}
	
	public void enterThePersonalNumberAndLogin() {
		List<WebElement> elements = driver.findElements(By.name("pid"));
		for (WebElement element:elements) {
			boolean isDisplayed = element.isDisplayed();
			if (isDisplayed) {
				element.click();
				element.sendKeys(personalNumber);
			}
		}
		
		List<WebElement> buttons = driver.findElements(By.className("mobileIdLogin"));
		for (WebElement element:buttons) {
			boolean isDisplayed = element.isDisplayed();
			if (isDisplayed) {
				element.click();
			}
		}

		WebElement oldPage = driver.findElement(By.tagName("html"));
		WebDriverWait wait = new WebDriverWait(driver, 300);
		wait.until((Function<? super WebDriver, Boolean>) ExpectedConditions.stalenessOf(oldPage));
//		wait.until(ExpectedConditions.stalenessOf(oldPage));
		int i = 0;
//		ImplicitlyWait<IWebDriver> wait = new OpenQA.Selenium.Support.UI.WebDriverWait(driver, TimeSpan.FromSeconds(30.00));
//
//		 wait.Until(driver1 => ((IJavaScriptExecutor)driver).ExecuteScript("return document.readyState").Equals("complete"));
	}
	
}
