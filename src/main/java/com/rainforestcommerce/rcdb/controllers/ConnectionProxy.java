package com.rainforestcommerce.rcdb.controllers;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;

public class ConnectionProxy {

	private static JdbcConnectionPool cp = JdbcConnectionPool.create(
			"jdbc:h2:~/test", "sa", "password");

	public static Connection connect() throws SQLException{
		return cp.getConnection();
	}

	public static void endConnection(){
		cp.dispose();
	}
}