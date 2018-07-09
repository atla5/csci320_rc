package com.rainforestcommerce.rcdb.controllers;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;

import java.util.logging.*;

public class ConnectionProxy {

	private static final Logger LOGGER = Logger.getLogger( ConnectionProxy.class.getName() );

	private static JdbcConnectionPool cp = JdbcConnectionPool.create(
			"jdbc:h2:~/test", "sa", "password");

	public static Connection connect(){
		Connection conn = null;
		try {
			conn = cp.getConnection();
		} catch(SQLException ex){
			LOGGER.log( Level.SEVERE, ex.toString(), ex );
		}
		return conn;
	}

	public static void endConnection(){
		cp.dispose();
	}
}