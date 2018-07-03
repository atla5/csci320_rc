package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class ShipmentRequestProxy {

	public static ArrayList<Product> getProductsForShipment(ShipmentRequest shipment){
		Connection conn = ConnectionProxy.cp.getConnection();
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product WHERE ID = ?");
		statement.setString(1, shipment.p_ID);
		ResultSet rs = conn.createStatement().executeQuery(statement);
		ArrayList<Product> products = null;
		while(rs.next()){
			products.add(new Product(
					rs.getLong("upcCode"),
					rs.getString("productName"),
					rs.getInt("weight"),
					rs.getString("brand")
			));
		}
		conn.close();
		//Write Database Access code here
		return products;
	}

	public static void requestShipment(ShipmentRequest shipment){
		String statement = "";
		Connection conn = ConnectionProxy.cp.getConnection();
		conn.createStatement().execute(statement);
		conn.close();
		//Create a shipment request in the database
	}
}
