package com.demo;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public interface BaseHttpHandler extends HttpHandler {
	public default void nextHandleRequest(final HttpServerExchange exchange) throws Exception {
		HttpHandler next = getNext();
		if (next != null) {
			next.handleRequest(exchange);
		}
	}
	
	public HttpHandler getNext();
}
