/*package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class ShipmentRequestProxy {

	private static final Logger LOGGER = Logger.getLogger( ShipmentRequestProxy.class.getName() );

	public static ArrayList<Product> getProductsForShipment(ShipmentRequest shipment){
        ArrayList<Product> products = null;
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product WHERE ID = ?");
            statement.setString(1, shipment.p_ID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getLong("upcCode"),
                        rs.getString("productName"),
                        rs.getInt("weight"),
                        rs.getString("brand")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
		return products;
	}

	public static void requestShipment(ShipmentRequest shipment){
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ShipmentRequest (PurchaseId, ProductId, Unit_Price, Quantity) VALUES (?, ?, ?, ?)");
            statement.setString(1, Long.toString(shipment.getPurchaseId()));
            statement.setString(2, Long.toString(shipment.getProductId()));
            statement.setString(3, Long.toString(shipment.getUnit_Price()));
            statement.setString(4, Long.toString(shipment.getQuantity()));
            statement.execute();
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
	}
}*/