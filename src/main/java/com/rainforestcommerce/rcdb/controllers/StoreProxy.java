package com.rainforestcommerce.rcdb.controllers;

//import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import com.rainforestcommerce.rcdb.models.Product;

import com.rainforestcommerce.rcdb.models.Store;

import java.util.ArrayList;

import java.sql.*;

public class StoreProxy {
	public static ArrayList<Store> getStores() throws SQLException{
		Connection conn = ConnectionProxy.cp.getConnection();
		String statement = "SELECT * FROM Stores";
		ResultSet rs = conn.createStatement().executeQuery(statement);
		ArrayList<Store> stores = null;
		while(rs.next()){
			stores.add(new Store(
					rs.getLong("storeId"),
					rs.getString("name"),
					rs.getDate("openingTime"),
					rs.getDate("closingTime"),
					rs.getString("address.city"),
					rs.getString("address.state"),
					rs.getString("address.street"),
					rs.getString("address.zipcode"),
					rs.getString("address.number")
			));
		}
		conn.close();
		return stores;
	}

	public static ArrayList<ProductPurchase> getPurchasesForStore(Store store) throws SQLException{
		Connection conn = ConnectionProxy.cp.getConnection();
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM ProductPurchases WHERE storeID = ?");
		statement.setString(1, Long.toString(store.getStoreId()));
		ResultSet rs = statement.executeQuery();
		ArrayList<ProductPurchase> purchases = null;
		while(rs.next()){
			purchases.add(new ProductPurchase(
					rs.getLong("purchaseId"),
					rs.getLong("productId"),
					rs.getInt("OverallPrice"),
					rs.getInt("quantity")
			));
		}
		conn.close();
		return purchases;
	}

	/*public static ArrayList<ShipmentRequest> getShipmentsForStore(Store store) throws SQLException{
		Connection conn = ConnectionProxy.cp.getConnection();
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM ShipmentRequest WHERE storeID = ?");
		statement.setString(1, Long.toString(store.getStoreId()));
		ResultSet rs = statement.executeQuery();
		ArrayList<ShipmentRequest> shipments = null;
		while(rs.next()){
			shipments.add(new ShipmentRequest(
					rs.getLong("purchaseId"),
					rs.getLong("productId"),
					rs.getInt("OverallPrice"),
					rs.getInt("quantity")
			));
		}
		conn.close();
		return shipments;
	}*/

	public static ArrayList<Product> getProductsForStore(Store store) throws SQLException{
		Connection conn = ConnectionProxy.cp.getConnection();
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM Products WHERE storeID = ?");
		statement.setString(1, Long.toString(store.getStoreId()));
		ResultSet rs = statement.executeQuery();
		ArrayList<Product> products = null;
		while(rs.next()){
			products.add(new Product(
					rs.getLong("upcCode"),
					rs.getString("name"),
					rs.getInt("weight"),
					rs.getString("brand")
			));
		}
		conn.close();
		return products;
	}

	public static ArrayList<Product> searchProducts(Store store, String search) throws SQLException{
		Connection conn = ConnectionProxy.cp.getConnection();
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM Products WHERE storeID = ? AND CHARINDEX(?, name) > 0");
		statement.setString(1, Long.toString(store.getStoreId()));
		statement.setString(2, search);
		ResultSet rs = statement.executeQuery();
		ArrayList<Product> products = null;
		while(rs.next()){
			products.add(new Product(
					rs.getLong("upcCode"),
					rs.getString("name"),
					rs.getInt("weight"),
					rs.getString("brand")
			));
		}
		conn.close();
		return products;
	}
}