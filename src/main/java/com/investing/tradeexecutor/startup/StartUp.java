package com.investing.tradeexecutor.startup;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

@Component
public class StartUp {
	
	WebDriver driver;

	public StartUp() {
		super();		
	}
	
	public void openFirefoxAndStartApplication() {
		File gecko = new File("/home/evasiba/gitlab/tradeexecutor/geckodriver");    
		System.setProperty("webdriver.gecko.driver", gecko.getAbsolutePath());
		
		driver= new FirefoxDriver();					
		driver.manage().window().maximize();			
		driver.get("https://www.avanza.se/start/forsta-oinloggad.html");
	}
	
}
