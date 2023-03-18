package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class LongRunningTask implements Runnable {
	final static Logger logger = LoggerFactory.getLogger(LongRunningTask.class);
	private static int n = 0;
	private String url1 = "";
	private String url2 = "";
	
	public LongRunningTask() {
        url1 = System.getenv("URL1");
        url2 = System.getenv("URL2");		
	}
	
	@Override
	public void run() {
		logger.info("begin" + n);
		
        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL you want to send the request to
        //String url = "https://undertow-sample.onrender.com/?key=hello";
        //String url = "https://undertow-sample-scheduler.onrender.com/?key=hello";
        String url = (n == 0)? url1 : url2;

        // Send the HTTP GET request and receive the response as a string
        String response = restTemplate.getForObject(url, String.class);
        
		logger.info("end" + n + ":" + response);
		
		n = 1 - n;
	}

}
