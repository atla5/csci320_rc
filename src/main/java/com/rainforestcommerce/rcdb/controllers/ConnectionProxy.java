package com.rainforestcommerce.rcdb.controllers;

import org.h2.jdbcx.JdbcConnectionPool;

public class ConnectionProxy {

	public static JdbcConnectionPool cp = JdbcConnectionPool.create(
			"jdbc:h2:~/test", "sa", "password");

	public static void endConnection(){
		cp.dispose();
	}
}