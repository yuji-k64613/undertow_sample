package com.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DBUtil {
	private static DBUtil instance = new DBUtil();
	private JedisPool pool;

	private DBUtil() {
		pool = new JedisPool("redis://red-cf55jb5a4992g5g872t0", 6379);
		try (Jedis jedis = pool.getResource()) {
			jedis.set("hello", "Redis");
			jedis.set("key", "value");
		}
	}

	public static DBUtil getInstance() {
		return instance;
	}

	public JedisPool getPool() {
		return pool;
	}
}