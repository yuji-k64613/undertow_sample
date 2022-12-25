package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hello implements HelloMBean {
	final static Logger logger = LoggerFactory.getLogger(Hello.class);

	@Override
	public String getMessage() {
		return "hello, world!";
	}

	@Override
	public void putLog(String message) {
		logger.info(message);
	}
}
