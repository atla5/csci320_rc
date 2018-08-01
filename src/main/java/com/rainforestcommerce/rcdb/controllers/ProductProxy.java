package com.rainforestcommerce.rcdb.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rainforestcommerce.rcdb.models.Product;

public class ProductProxy {
	
	private static final Logger LOGGER = Logger.getLogger( ProductProxy.class.getName() );

    public static boolean insertNewProduct(Product product){
        String values = String.format("(%d, '%s', '%s', '%s')", product.getUpcCode(), product.getProductName(), product.getBrand());
        return DataLoader.insertValuesIntoTable(values, "products");
    }
    
    public static ArrayList<Product> getAllProducts() {
    	ArrayList<Product> products = new ArrayList<>();
    	String command = "SELECT * FROM Products ORDER BY product_name";
    	ResultSet results;
		try {
			Connection connection = ConnectionProxy.connect();
			results = connection.prepareStatement(command).executeQuery();
			while (results.next()) {
	    		products.add(new Product(
		                    results.getLong("upc_code"),
		                    results.getString("product_name"),
		                    results.getString("brand_name")));
	    	}
			connection.close();
		} catch (SQLException ex) {
			LOGGER.log( Level.SEVERE, ex.toString(), ex );
		}
		return products;
    }
}
