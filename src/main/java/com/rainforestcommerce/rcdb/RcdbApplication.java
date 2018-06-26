package com.rainforestcommerce.rcdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RcdbApplication {

	private Connection connection;

	public static void main(String[] args) {

		RcdbApplication rcdb = new RcdbApplication();

		try {
			Class.forName("org.h2.Driver");
			rcdb.connection = DriverManager.getConnection("jdbc:h2:~/test", "sa","");
			System.out.println("connection established");
		} catch(ClassNotFoundException cnf){
			System.err.println("Error with h2 configuration. 'org.h2.Driver' not found.");
			cnf.printStackTrace();
			System.exit(1);
		} catch(SQLException sql){
			System.err.println("Error with sql connection.");
			sql.printStackTrace();
			System.exit(1);
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void closeConnection() throws SQLException{
		this.connection.close();
	}


}
