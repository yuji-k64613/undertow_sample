package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;

//import io.undertow.examples.UndertowExample;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;
import static io.undertow.Handlers.websocket;

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
		logger.info(accountService.getName());

		HttpHandler app_handler = null;
		app_handler = new MainHttpHandler(app_handler);
		app_handler = new ResponseHeadersHttpHandler(app_handler, "key", "value");

		HttpHandler toppage_handler = resource(
				new ClassPathResourceManager(App.class.getClassLoader(), App.class.getPackage()))
				.addWelcomeFiles("index.html");

		HttpHandler websocket_handler = path().addPrefixPath("/chat", websocket(new WebSocketConnectionCallback() {
			@Override
			public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
				channel.getReceiveSetter().set(new AbstractReceiveListener() {

					@Override
					protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
						String msg = message.getData();
						logger.info("WebSocket " + msg);
						logger.info("WebSocket length=" + msg.length());
						WebSockets.sendText(msg, channel, null);
					}
				});
				channel.resumeReceives();
			}
		})).addPrefixPath("/app", app_handler).addPrefixPath("/", toppage_handler);

		Undertow server = Undertow.builder().addHttpListener(port, "0.0.0.0").setHandler(websocket_handler).build();
		server.start();
	}
}
