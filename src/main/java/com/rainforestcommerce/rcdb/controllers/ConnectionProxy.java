package com.rainforestcommerce.rcdb.controllers;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;

import java.util.logging.*;
import static com.rainforestcommerce.rcdb.RcdbApplication.USE_TRANSIENT_PRODUCTION_DB;

public class ConnectionProxy {

	private static final Logger LOGGER = Logger.getLogger( ConnectionProxy.class.getName() );

	private static JdbcConnectionPool cp;

	public static void startConnection(){
		String dbName = USE_TRANSIENT_PRODUCTION_DB? "new" : "backup";
		cp = JdbcConnectionPool.create("jdbc:h2:~/"+dbName+";AUTO_SERVER=TRUE", "sa", "");
		LOGGER.info("initial connection established to: " + dbName);
	}

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