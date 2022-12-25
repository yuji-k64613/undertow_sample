package com.demo;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

public class ResponseHeadersHttpHandler implements BaseHttpHandler {
	private final HttpString header;
	private final String value;
	private final HttpHandler next;

	public ResponseHeadersHttpHandler(final HttpHandler next, final String header, final String value) {
        this.next = next;
        this.value = value;
        this.header = new HttpString(header);
    }

	@Override
	public void handleRequest(final HttpServerExchange exchange) throws Exception {
		exchange.getResponseHeaders().put(header, value);
		nextHandleRequest(exchange);
	}

	@Override
	public HttpHandler getNext() {
		return next;
	}
}
