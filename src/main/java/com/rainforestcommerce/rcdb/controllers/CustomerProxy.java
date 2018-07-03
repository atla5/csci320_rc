package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Customer;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class CustomerProxy {
	public static ArrayList<Customer> getCustomers() {
		Connection conn = ConnectionProxy.cp.getConnection();
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
		//Write Database Access code here
		return customers;
	}
}
