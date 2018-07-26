package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Customer;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class CustomerProxy {

	private static final Logger LOGGER = Logger.getLogger( CustomerProxy.class.getName() );

	public static ArrayList<Customer> getCustomers(){
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			Connection conn = ConnectionProxy.connect();
			String statement = "SELECT * FROM Customers";
			ResultSet rs = conn.createStatement().executeQuery(statement);
			while (rs.next()) {
				customers.add(new Customer(
						rs.getLong("account_number"),
						rs.getString("cust_name"),
						rs.getInt("points"),
						//rs.getDate("birth_date"),
						rs.getString("birth_date"),
						rs.getBoolean("male"),
						rs.getString("phone_number")
				));
			}
			conn.close();
		} catch(SQLException ex){
			LOGGER.log( Level.SEVERE, ex.toString(), ex );
		}
		return customers;
	}

	public static boolean insertNewCustomer(Customer customer){
		String values = String.format("(%d, '%s', '%s', %b, '%s', %d)",
				customer.getAccountNumber(), customer.getCustName(), customer.getBirthDate().toString(),
				customer.isIsMale(), customer.getPhone(), customer.getPoints());
		return DataLoader.insertValuesIntoTable(values, "customers");
	}
}