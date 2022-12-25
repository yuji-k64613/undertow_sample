package com.demo;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnio.channels.StreamSinkChannel;

import com.demo.mat.MatTest;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class MainHttpHandler implements BaseHttpHandler {
	final static Logger logger = LoggerFactory.getLogger(MainHttpHandler.class);

	private final HttpHandler next;

	// for mat test
	private Map<String, String> map = new HashMap();
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<Map<String, String>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, String>> mapList) {
		this.mapList = mapList;
	}

	public List<MatTest> getMatList() {
		return matList;
	}

	public void setMatList(List<MatTest> matList) {
		this.matList = matList;
	}

	public static Logger getLogger() {
		return logger;
	}

	private List<Map<String, String>> mapList = new ArrayList();
	private List<MatTest> matList = new ArrayList();
	
	public MainHttpHandler(final HttpHandler next) {
		this.next = next;
		
		for (int i = 0; i < 1000; i++) {
			String val = "" + i;
			map.put(val, val + val);
		}
		mapList.add(map);
		
		for (int i = 0; i < 100; i++) {
			MatTest obj = new MatTest("Mat1_" + i, 1024 * 10);
			matList.add(obj);
		}
		for (int i = 0; i < 200; i++) {
			MatTest obj = new MatTest("Mat2_" + i, 1024 * 10 * 2);
			matList.add(obj);
		}
	}

	@Override
	public void handleRequest(final HttpServerExchange exchange) throws Exception {
		Map<String, Deque<String>> params = exchange.getQueryParameters();
		Deque<String> keys = params.get("key");
		String key = keys.getFirst();
		String value = "none";
		
		String sql;
		sql = "SELECT NAME FROM TEST WHERE ID = '" + key + "'";
		Connection connection  = DBCPDataSource.getInstance().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println(rs.getString("NAME"));
				value = rs.getString("NAME");
				logger.info("KEY=" + key + ", VALUE=" + value);
			}
			rs.close();
		} finally {
			stmt.close();
		}
        connection.close();

		ByteBuffer buffer = ByteBuffer.allocate(256);
		buffer.put(value.getBytes());
		buffer.flip();
		
		StreamSinkChannel channel = exchange.getResponseChannel();
		channel.write(buffer);
		
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
		 
		nextHandleRequest(exchange);
	}

	@Override
	public HttpHandler getNext() {
		return next;
	}
}
