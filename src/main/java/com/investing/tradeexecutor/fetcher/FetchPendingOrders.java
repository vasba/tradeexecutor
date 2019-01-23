package com.investing.tradeexecutor.fetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FetchPendingOrders {
    
    public static ArrayList<HashMap<String, WebElement>> fetchPendingOrders(WebDriver driver, String underLying, ActionType type) {
        String overviewPage = "https://www.avanza.se/mina-sidor/kontooversikt.html";
        driver.get(overviewPage);
        
//        find table which contains the keyword
        WebElement tableElement = driver.findElement(By.xpath("//table[contains(@class, 'currentOrdersTable')]"));
        
     // create empty table object and iterate through all rows of the found table element
        ArrayList<HashMap<String, WebElement>> ordersTable = new ArrayList<HashMap<String, WebElement>>();
        List<WebElement> rowElements = tableElement.findElements(By.xpath(".//tr"));

        // get column names of table from table headers
        ArrayList<String> columnNames = new ArrayList<String>();
        List<WebElement> headerElements = rowElements.get(0).findElements(By.xpath(".//th"));
        for (WebElement headerElement: headerElements) {
          columnNames.add(headerElement.getText());
        }

        // iterate through all rows and add their content to table array
        for (WebElement rowElement: rowElements) {
          HashMap<String, WebElement> row = new HashMap<String, WebElement>();
          
          // add table cells to current row
          int columnIndex = 0;
          List<WebElement> cellElements = rowElement.findElements(By.xpath(".//td"));
          for (WebElement cellElement: cellElements) {
            row.put(columnNames.get(columnIndex), cellElement);
            columnIndex++;
          }
          
          WebElement instrument = row.get(columnNames.get(3));
          if (instrument != null) {
              String text = instrument.getText();
              int storp = 1;
              // TO DO include type in the check below so that we do not sell
              // pending orders for other type than given one
              if (text.toLowerCase().contains(underLying.toLowerCase())) {
                  WebElement orderTypeElem = row.get(columnNames.get(1));
                  String orderType = orderTypeElem.getText();
                  String localeTypeText = localeTypeText(type);
                  if (orderType.toLowerCase().contains(localeTypeText.toLowerCase()))
                      ordersTable.add(row);
              }
          }
          
//          ordersTable.add(row);
        }

        // finally fetch the desired data
//        WebElement cellInSecondRowOrderType = ordersTable.get(1).get("K/S");
        return ordersTable;
    }
    
    
    public static String localeTypeText(ActionType type) {
    	if (type == ActionType.BUY)
    		return "Köp";
    	
    	return "Sälj";
    }

}
