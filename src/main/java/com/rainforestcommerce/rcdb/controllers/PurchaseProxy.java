package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

import java.sql.*;

public class PurchaseProxy {

	public static void purchaseProducts(ProductPurchase purchase) throws SQLException{
        Connection conn = ConnectionProxy.connect();
	    PreparedStatement statement = conn.prepareStatement("INSERT INTO ProductPurchase (purchaseId, productId, overallPrice, quantity) VALUES (?, ?, ?, ?)");
	    statement.setString(1, Long.toString(purchase.getPurchaseId()));
	    statement.setString(2, Long.toString(purchase.getProductId()));
	    statement.setString(3, Float.toString(purchase.getOverallPrice()));
        statement.setString(4, Long.toString(purchase.getQuantity()));
        statement.execute();
        conn.close();
	}

	public static boolean purchasable(ProductPurchase purchase) throws SQLException{
        Connection conn = ConnectionProxy.connect();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM ProductPurchase WHERE ID = ?");
        statement.setString(1, Long.toString(purchase.getPurchaseId()));
        ResultSet rs = statement.executeQuery();
        boolean canPurchase = rs.getBoolean("purchaseable");
        conn.close();
        return canPurchase;
	}

	public static ArrayList<Product> getProductsForPurchase(ProductPurchase purchase) throws SQLException{
        Connection conn = ConnectionProxy.connect();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product WHERE ID = ?");
        statement.setString(1, Long.toString(purchase.getProductId()));
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