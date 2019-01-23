package com.investing.tradeexecutor.testRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)				
@CucumberOptions(features="Features",glue={"com.investing.tradeexecutor.testRunner"})						
public class Runner 				
{		

}