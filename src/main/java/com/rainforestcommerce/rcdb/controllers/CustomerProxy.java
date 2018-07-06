package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Customer;

import java.util.ArrayList;

import java.sql.*;

public class CustomerProxy {
	public static ArrayList<Customer> getCustomers() throws SQLException{
		Connection conn = ConnectionProxy.connect();
		String statement = "SELECT * FROM Customer";
		ResultSet rs = conn.createStatement().executeQuery(statement);
		ArrayList<Customer> customers = null;
		while(rs.next()){
			customers.add(new Customer(
					rs.getLong("accountNumber"),
					rs.getString("customerName"),
					rs.getDate("birthDate"),
					rs.getBoolean("isMale"),
					rs.getString("phone")
			));
		}
		conn.close();
		return customers;
	}
}