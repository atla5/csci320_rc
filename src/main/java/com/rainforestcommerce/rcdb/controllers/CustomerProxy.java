package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Customer;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class CustomerProxy {

	private static final Logger LOGGER = Logger.getLogger( CustomerProxy.class.getName() );

	public static ArrayList<Customer> getCustomers(){
		ArrayList<Customer> customers = null;
		try {
			Connection conn = ConnectionProxy.connect();
			String statement = "SELECT * FROM Customer";
			ResultSet rs = conn.createStatement().executeQuery(statement);
			while (rs.next()) {
				customers.add(new Customer(
						rs.getLong("account_number"),
						rs.getString("cust_name"),
						rs.getDate("birth_date"),
						rs.getBoolean("is_male"),
						rs.getString("phone_number")
				));
			}
			conn.close();
		} catch(SQLException ex){
			LOGGER.log( Level.SEVERE, ex.toString(), ex );
		}
		return customers;
	}
}