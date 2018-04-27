package com.investing.tradeexecutor.stepdefinition;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {
	
	WebDriver driver;
	int balance = 0;
	double factor = 1;
	final String warantExtension = "636656/mini-l-omx-ava-95";
	final String instrumentLink = "https://www.avanza.se/borshandlade-produkter/warranter-torg/om-warranten.html/";

	@Given("^Open the Firefox and launch the application$")			
	public void open_the_Firefox_and_launch_the_application() throws Throwable							
	{					
		File gecko = new File("/home/evasiba/gitlab/tradeexecutor/geckodriver");    
		System.setProperty("webdriver.gecko.driver", gecko.getAbsolutePath());
		
		driver= new FirefoxDriver();					
		driver.manage().window().maximize();			
		driver.get("https://www.avanza.se/start/forsta-oinloggad.html");		
	}	
		
	@When("^Buy two OMX index instruments for half current price$")					
	public void buy_two_OMX_index_instruments_for_half_current_price() throws Throwable 							
	{		
		List<WebElement> warants = FetchMonitoredList.fetch(ActionType.BUY, "omxs30", driver);
		warants.get(0).click();
//		String warantsAdress = warants.get(0).getAttribute("href");
		//driver.get(instrumentLink + warantExtension);
//		driver.get(warantsAdress);
		WebElement text = driver.findElement(By.className("sellPrice"));
		String sellPriceStr = text.getText();
//		String sellPriceStr = "718,45";
		sellPriceStr = sellPriceStr.replaceAll(",", ".");
		double sellPrice = Double.parseDouble(sellPriceStr)*factor;
		DecimalFormat df = new DecimalFormat("#.##");  
		sellPriceStr = df.format(sellPrice);
		sellPriceStr = sellPriceStr.replaceAll(",", ".");
		sellPrice = Double.parseDouble(sellPriceStr);
		
		String buyLink = "https://www.avanza.se/handla/warranter.html/kop/";
		driver.get(buyLink + warantExtension);
		WebElement input = driver.findElement(By.name("price"));
		input.clear();
		input.sendKeys("" + sellPrice);
		
		WebElement amountInput = driver.findElement(By.name("volume"));
		amountInput.clear();
		amountInput.sendKeys("2");
		Thread.sleep(1000);
		WebElement buyButton = driver.findElement(By.cssSelector("button.putorder.buy.buyBtn"));
//	    WebDriverWait wait = new WebDriverWait(driver, 45);
//	    wait.until(ExpectedConditions.visibilityOf(buyButton)); 
//	    wait.until(ExpectedConditions.elementToBeClickable(buyButton));
		buyButton.click();				

		
		System.out.println("Buy two OMX index instruments for half current price");					
	}		
	
	@When("^Sell two OMX index instruments$")					
	public void sell_two_OMX_index_instruments() throws Throwable 							
	{		
		//List<WebElement> holdings = FetchHoldings.fetchHoldings(driver);
		List<WebElement> warants = FetchMonitoredList.fetch(ActionType.BUY, "omxs30", driver);
		warants.get(0).click();
		String sellLink = "https://www.avanza.se/handla/warranter.html/salj/";
		driver.get(sellLink + warantExtension);
		
		WebElement text = driver.findElement(By.className("buyPrice"));
		
		String buyPriceStr = text.getText();
//		String buyPriceStr = "718,45";
		buyPriceStr = buyPriceStr.replaceAll(",", ".");
		double buyPrice = Double.parseDouble(buyPriceStr)*factor;
		DecimalFormat df = new DecimalFormat("#.##");  
		buyPriceStr = df.format(buyPrice);
		buyPriceStr = buyPriceStr.replaceAll(",", ".");
		buyPrice = Double.parseDouble(buyPriceStr);
		
		WebElement input = driver.findElement(By.name("price"));
		input.clear();
		input.sendKeys("" + buyPrice);
		
		WebElement amountInput = driver.findElement(By.name("volume"));
		amountInput.clear();
		amountInput.sendKeys("2");
		Thread.sleep(1000);
		WebElement buyButton = driver.findElement(By.cssSelector("button.noMarginRight.putorder.sell.sellBtn"));
		buyButton.click();				
		
		System.out.println("Sell two OMX index instruments");					
	}
	
	@When("^Cancel all pending orders$")					
	public void cancel_all_pending_orders() throws Throwable 							
	{		
		String overviewPage = "https://www.avanza.se/mina-sidor/kontooversikt.html";
		driver.get(overviewPage);
		List<WebElement> cancelButtons = driver.findElements(By.cssSelector("a.icon.removeIcoSmall.deleteOrder"));
		for (WebElement button : cancelButtons) {
			button.click();
			WebElement confirmButton = driver.findElement(By.cssSelector("button.focusBtn.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only"));
			confirmButton.click();
			int wait = 0;
		}
	}
	
	@When("^Fetch account balance$")					
	public void fetch_account_balance() throws Throwable 							
	{
		String overviewPage = "https://www.avanza.se/mina-sidor/kontooversikt.dhihcja.html";
		driver.get(overviewPage);
		WebElement buyingPower = driver.findElement(By.cssSelector("span.buyingPower"));
		String balanceStr = buyingPower.getText();
		String digits = balanceStr.replaceAll("[^0-9.,]", "");
		digits = digits.replaceAll(",", ".");
		balance = Integer.parseInt(digits);		
	}

	@When("^Enter the Username and Password$")					
	public void enter_the_Username_and_Password_and_login() throws Throwable 							
	{		
		System.out.println("This step enter the Username and Password on the login page.");					
	}		
	
	@When("^Enter the personal number and login$")					
	public void enter_the_personal_number_and_login() throws Throwable 							
	{		
		List<WebElement> elements = driver.findElements(By.name("pid"));
		for (WebElement element:elements) {
			boolean isDisplayed = element.isDisplayed();
			if (isDisplayed) {
				element.click();
				element.sendKeys("");
			}
		}
		
		List<WebElement> buttons = driver.findElements(By.className("mobileIdLogin"));
		for (WebElement element:buttons) {
			boolean isDisplayed = element.isDisplayed();
			if (isDisplayed) {
				element.click();
			}
		}
	}

	@Then("^Check name on details page$")					
	public void check_name_on_details_page() throws Throwable 							
	{    		
		driver.get("https://www.avanza.se/mina-sidor/min-profil/mina-uppgifter/mina-uppgifter.html");	
		if(driver.getPageSource().toLowerCase().contains("vasile baluta")) {
			int found = 1;
		}
		else {
			
		}
		int i = 0;
	}		
	
	@Then("^Check order for two pieces of an OMX minifuture has been placed$")					
	public void check_order_for_two_pieces_of_an_OMX_minifuture_has_been_placed() throws Throwable 							
	{
		System.out.println("Check order for two pieces of an OMX minifuture has been placed");
	}
}
