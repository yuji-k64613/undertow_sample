package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class LongRunningTask implements Runnable {
	final static Logger logger = LoggerFactory.getLogger(LongRunningTask.class);
	@Override
	public void run() {
		logger.info("begin");
		
        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL you want to send the request to
        //String url = "https://undertow-sample.onrender.com/?key=hello";
        String url = "https://undertow-sample-scheduler.onrender.com/?key=hello";

        // Send the HTTP GET request and receive the response as a string
        String response = restTemplate.getForObject(url, String.class);
        
		logger.info("end: " + response);
	}

}
