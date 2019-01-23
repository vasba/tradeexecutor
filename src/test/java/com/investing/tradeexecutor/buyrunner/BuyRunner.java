package com.investing.tradeexecutor.buyrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)				
@CucumberOptions(features="Features/buy.feature",glue={"com.investing.tradeexecutor.buyrunner"})	
public class BuyRunner {

}
