package com.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private static DBUtil instance = new DBUtil();
	private Connection connection;

	public void executeStatement(String sql) throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.execute(sql);
	}

	public static DBUtil getInstance() {
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}