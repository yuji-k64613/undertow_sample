package com.demo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/*
 * ToDo
 * AccessLogHandlerが使えない("combined"でエラー)
 * ResponseCodeHandlerが使えない
 * Renderで動かす(MySQLに対応させる)
 * 
 * Done
 * slf4jを使うとコンソールにログが出ない
 */
public class App {
	final static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(final String[] args) {
		logger.info("main() start");
		HelloAgent agent = new HelloAgent();

		int port = 8080;
		String value = System.getenv("PORT");
		if (value != null && value.length() > 0) {
			port = Integer.parseInt(value);
		}

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringMainConfig.class);
		UserService userService = context.getBean(UserService.class);
		AccountService accountService = userService.getAccountService();
//		logger.info(accountService.getName());

		HttpHandler handler = new HttpHandler() {
			@Override
			public void handleRequest(HttpServerExchange exchange) throws Exception {
				logger.info("Hello World");
				exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
				exchange.getResponseSender().send("Hello World");
			}
		};
//		handler = new MainHttpHandler(handler);
//		handler = new ResponseHeadersHttpHandler(handler, "key", "value");

		Undertow server = Undertow.builder().addHttpListener(port, "0.0.0.0").setHandler(handler).build();
		server.start();
		(new Scheduler()).schedule();
		logger.info("main() end");
	}
}
