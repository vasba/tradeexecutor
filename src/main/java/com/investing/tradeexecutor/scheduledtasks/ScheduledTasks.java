package com.investing.tradeexecutor.scheduledtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.investing.tradeexecutor.startup.StartUp;

@Component
public class ScheduledTasks {
    
	
    @Scheduled(fixedRate = 300000)
    public static void refreshDriver() {
        try {
        	while(StartUp.orderPending == true) {        		
        		Thread.sleep(10000);
        	}
        	StartUp.getDriver().navigate().refresh();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
}
