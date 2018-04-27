package com.investing.tradeexecutor.TestRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)				
@CucumberOptions(features="Features",glue={"com.investing.tradeexecutor.stepdefinition"})						
public class Runner 				
{		

}