package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;

/*
 * ToDo
 * https://github.com/undertow-io/undertow/blob/master/examples/src/main/java/io/undertow/examples/reverseproxy/ReverseProxyServer.java
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
    	logger.info(accountService.getName());
    	
    	HttpHandler handler = null;
        handler = new MainHttpHandler(handler);
        handler = new ResponseHeadersHttpHandler(handler, "key", "value");
//		handler = new AccessLogHandler(handler, new AccessLogReceiver() {
//			@Override
//			public void logMessage(String message) {
//				logger.info("AccessLogHandler.logMessage()");
//			}}, "combined", App.class.getClassLoader());
//        handler = new AccessLogHandler(handler, logger::info, "combined", App.class.getClassLoader());
        
        Undertow server = Undertow.builder()
                .addHttpListener(port, "0.0.0.0")
                .setHandler(handler).build();
        server.start();
    }
}
