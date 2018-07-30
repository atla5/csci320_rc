package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Shipment;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class ShipmentRequestProxy {

	private static final Logger LOGGER = Logger.getLogger( ShipmentRequestProxy.class.getName() );

	public static ArrayList<Product> getProductsForShipment(Shipment shipment){
        ArrayList<Product> products = new ArrayList<Product>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Shipment_contents ON Product.product_id = Shipment_contents.product_id WHERE shipment_id = ?");
            statement.setString(1, Long.toString(shipment.getID()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getLong("upcCode"),
                        rs.getString("productName"),
                        rs.getString("size"),
                        rs.getString("brand")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
		return products;
	}

    public static boolean insertNewShipment(Shipment shipment){
        String values = String.format("(%d, '%s', '%s')", shipment.getID(), shipment.getStore(), shipment.getRequestDate().toString());
        return DataLoader.insertValuesIntoTable(values, "Shipments");
    }
}