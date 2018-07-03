package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class PurchaseProxy {

	public static void purchaseProducts(ProductPurchase purchase){
		String statement = "";
		Connection conn = ConnectionProxy.cp.getConnection();
		conn.createStatement().execute(statement);
		conn.close();
		//Add Product Purchases to database
	}

	public static boolean purchasable(ProductPurchase purchase){
		String statement = "";
		Connection conn = ConnectionProxy.cp.getConnection();
		conn.createStatement().execute(statement);
		conn.close();
		return true;
	}

	public static ArrayList<Product> getProductsForPurchase(ProductPurchase purchase){
		String statement = "";
		Connection conn = ConnectionProxy.cp.getConnection();
		conn.createStatement().execute(statement);
		conn.close();
		return new ArrayList<Product>();
	}
}
