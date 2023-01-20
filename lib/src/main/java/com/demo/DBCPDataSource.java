package com.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.DriverManager;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
	// PoolingDataSource
	private static DBCPDataSource instance = new DBCPDataSource();
	private BasicDataSource ds = new BasicDataSource();
	private DBUtil util = DBUtil.getInstance();

	public static DBCPDataSource getInstance() {
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	private DBCPDataSource() {
		ds.setUrl("jdbc:postgresql://dpg-cev7tbqrrk0a2joo07fg-a.oregon-postgres.render.com/mydb_qwhk");
		ds.setUsername("mydb_qwhk_user");
		ds.setPassword("O0A7WQxaXw4fnLFzcYMKHiapusZyuPIw");
		ds.setMaxIdle(3);
		ds.setMaxIdle(10);
		ds.setMaxOpenPreparedStatements(10);

		ds.setMaxTotal(10);
		ds.setMaxWaitMillis(1000);

		Connection connection;
		try {
			connection = this.getConnection();
	        util.setConnection(connection);
            String sql;
            int cnt = 0;
            sql = "SELECT count(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'test'";
            Statement stmt = connection.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery(sql);
                rs.next();
				cnt = rs.getInt(1);
                rs.close();
            } finally {
                stmt.close();
            }
            if (cnt > 0){
                return;
            }

	        sql = "CREATE TABLE TEST (ID VARCHAR(256), NAME VARCHAR(256))";
	        util.executeStatement(sql);
	        sql = "INSERT INTO TEST VALUES('hello', 'world')";
	        util.executeStatement(sql);
	        sql = "INSERT INTO TEST VALUES('key', 'value')";
	        util.executeStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
