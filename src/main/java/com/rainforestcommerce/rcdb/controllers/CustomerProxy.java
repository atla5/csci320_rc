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
			String statement = "SELECT * FROM Customers ORDER BY cust_name";
			ResultSet rs = conn.createStatement().executeQuery(statement);
			while (rs.next()) {
				Customer customer = new Customer(
						rs.getLong("account_number"),
						rs.getString("cust_name"),
						rs.getString("phone_number")
				);
				customer.setPoints(PurchaseProxy.getPointsForCustomer(customer));
				customers.add(customer);
			}
			conn.close();
		} catch(SQLException ex){
			LOGGER.log( Level.SEVERE, ex.toString(), ex );
		}
		return customers;
	}

	public static boolean insertNewCustomer(Customer customer){
		String values = String.format("(%d, '%s', '%s', %b, '%s', %d)",
				customer.getAccountNumber(), customer.getCustName(), customer.getPhone());
		return DataLoader.insertValuesIntoTable(values, "customers");
	}
}