package com.investing.tradeexecutor.testRunner;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.investing.tradeexecutor.fetcher.ActionType;
import com.investing.tradeexecutor.fetcher.Checks;
import com.investing.tradeexecutor.fetcher.FetchBalance;
import com.investing.tradeexecutor.fetcher.FetchMonitoredList;
import com.investing.tradeexecutor.fetcher.FetchPendingOrders;
import com.investing.tradeexecutor.ordering.OrderHandler;
import com.investing.tradeexecutor.startup.StartUp;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class Steps {
	
	
	@Autowired
	StartUp startUp;
	
	int balance = 0;
	double factor = 1;
	final String warantExtension = "636656/mini-l-omx-ava-95";
	final String instrumentLink = "https://www.avanza.se/borshandlade-produkter/warranter-torg/om-warranten.html/";

	@Given("^Open the Firefox and launch the application$")			
	public void open_the_Firefox_and_launch_the_application() throws Throwable							
	{				
//		startUp.openFirefoxAndStartApplication();				
	}	
		
	@When("^Buy two OMX index instruments for half current price$")					
	public void buy_two_OMX_index_instruments_for_half_current_price() throws Throwable 							
	{		
		List<WebElement> warants = FetchMonitoredList.fetch(ActionType.BUY, "omxs30", startUp.getDriver());
		warants.get(0).click();
//		String warantsAdress = warants.get(0).getAttribute("href");
		//driver.get(instrumentLink + warantExtension);
//		driver.get(warantsAdress);
		WebElement text = startUp.getDriver().findElement(By.className("sellPrice"));
		String sellPriceStr = text.getText();
//		String sellPriceStr = "718,45";
		sellPriceStr = sellPriceStr.replaceAll(",", ".");
		double sellPrice = Double.parseDouble(sellPriceStr)*factor;
		DecimalFormat df = new DecimalFormat("#.##");  
		sellPriceStr = df.format(sellPrice);
		sellPriceStr = sellPriceStr.replaceAll(",", ".");
		sellPrice = Double.parseDouble(sellPriceStr);
		
		String buyLink = "https://www.avanza.se/handla/warranter.html/kop/";
		startUp.getDriver().get(buyLink + warantExtension);
		WebElement input = startUp.getDriver().findElement(By.name("price"));
		input.clear();
		input.sendKeys("" + sellPrice);
		
		WebElement amountInput = startUp.getDriver().findElement(By.name("volume"));
		amountInput.clear();
		amountInput.sendKeys("2");
		Thread.sleep(1000);
		WebElement buyButton = startUp.getDriver().findElement(By.cssSelector("button.putorder.buy.buyBtn"));
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
		List<WebElement> warants = FetchMonitoredList.fetch(ActionType.BUY, "omxs30", startUp.getDriver());
		warants.get(0).click();
		String sellLink = "https://www.avanza.se/handla/warranter.html/salj/";
		startUp.getDriver().get(sellLink + warantExtension);
		
		WebElement text = startUp.getDriver().findElement(By.className("buyPrice"));
		
		String buyPriceStr = text.getText();
//		String buyPriceStr = "718,45";
		buyPriceStr = buyPriceStr.replaceAll(",", ".");
		double buyPrice = Double.parseDouble(buyPriceStr)*factor;
		DecimalFormat df = new DecimalFormat("#.##");  
		buyPriceStr = df.format(buyPrice);
		buyPriceStr = buyPriceStr.replaceAll(",", ".");
		buyPrice = Double.parseDouble(buyPriceStr);
		
		WebElement input = startUp.getDriver().findElement(By.name("price"));
		input.clear();
		input.sendKeys("" + buyPrice);
		
		WebElement amountInput = startUp.getDriver().findElement(By.name("volume"));
		amountInput.clear();
		amountInput.sendKeys("2");
		Thread.sleep(1000);
		WebElement buyButton = startUp.getDriver().findElement(By.cssSelector("button.noMarginRight.putorder.sell.sellBtn"));
		buyButton.click();				
		
		System.out.println("Sell two OMX index instruments");					
	}
	
	@When("^Cancel all pending orders$")					
	public void cancel_all_pending_orders() throws Throwable 							
	{		
		String overviewPage = "https://www.avanza.se/mina-sidor/kontooversikt.html";
		startUp.getDriver().get(overviewPage);
		List<WebElement> cancelButtons = startUp.getDriver().findElements(By.cssSelector("a.icon.removeIcoSmall.deleteOrder"));
		for (WebElement button : cancelButtons) {
			button.click();
			WebElement confirmButton = startUp.getDriver().findElement(By.cssSelector("button.focusBtn.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only"));
			confirmButton.click();
			int wait = 0;
		}
	}
	
	@When("^Fetch account balance$")					
	public void fetch_account_balance() throws Throwable 							
	{
		FetchBalance.fetchAccountBalance(startUp.getDriver());	
	}
	
	@When("^Fetch pending orders$")                   
    public void fetch_pending_orders() throws Throwable                            
    {
	    ArrayList<HashMap<String, WebElement>> ordersTable = FetchPendingOrders.fetchPendingOrders(startUp.getDriver(), "OMX", ActionType.BUY);
	    OrderHandler.cancelPendingOrders(startUp.getDriver(), ordersTable);
    }

	@When("^Enter the Username and Password$")					
	public void enter_the_Username_and_Password_and_login() throws Throwable 							
	{		
		System.out.println("This step enter the Username and Password on the login page.");					
	}		
	
	@When("^Enter the personal number and login$")					
	public void enter_the_personal_number_and_login() throws Throwable 							
	{		
//		startUp.enterThePersonalNumberAndLogin();
	}

	@Then("^Check name on details page$")					
	public void check_name_on_details_page() throws Throwable 							
	{    		
		Checks.checkNameOnDetailsPage(startUp.getDriver(), startUp.getAvanzaName());
	}		
	
	@Then("^Check order for two pieces of an OMX minifuture has been placed$")					
	public void check_order_for_two_pieces_of_an_OMX_minifuture_has_been_placed() throws Throwable 							
	{
		System.out.println("Check order for two pieces of an OMX minifuture has been placed");
	}
	
	@Then("^Get holdings$")					
	public void get_holdings() throws Throwable 							
	{
		// A list with holdings links and amount for each holding
		System.out.println("Get holdings");
	}
	
	@Then("^Get underlaying instrument$")					
	public void get_underlaying_instrument() throws Throwable 							
	{
		System.out.println("Get underlaying instrument");
	}
	
	@Then("^Check if short minifuture$")					
	public void check_if_short_minifuture() throws Throwable 							
	{
		System.out.println("Check if short minifuture");
	}
	
	@Then("^Check if long minifuture$")					
	public void check_if_long_minifuture() throws Throwable 							
	{
		System.out.println("Check if long minifuture");
	}
	
	@Then("^Check if stock$")					
	public void check_if_stock() throws Throwable 							
	{
		System.out.println("Check if stock");
	}
}
