package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;

import com.rainforestcommerce.rcdb.models.Shipment;

import com.rainforestcommerce.rcdb.models.Product;

import com.rainforestcommerce.rcdb.models.Store;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class ShipmentRequestProxy {

	private static final Logger LOGGER = Logger.getLogger( ShipmentRequestProxy.class.getName() );

	public static ArrayList<ProductQuantityPrice> getProductsForShipment(Shipment shipment){
        ArrayList<ProductQuantityPrice> products = new ArrayList<ProductQuantityPrice>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Shipment_contents INNER JOIN Products ON Products.upc_code = Shipment_contents.product_id WHERE shipment_contents.shipment_id = ?");
            statement.setString(1, Long.toString(shipment.getID()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new ProductQuantityPrice(
                        0, //rs.getFloat("unit_price"),
                        rs.getInt("quantity"),
                        new Product(
                            rs.getLong("upc_code"),
                            rs.getString("product_name"),
                            rs.getString("brand_name")
                        )
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
		return products;
	}

	public static void receiveShipment(Shipment shipment){
        try {
            Connection conn = ConnectionProxy.connect();
            for (ProductQuantityPrice item : shipment.contents) {
                String query = String.format("UPDATE store_inventory SET quantity = quantity + %d WHERE store_id = SOME(SELECT store_id FROM stores WHERE store_name = '%s') AND product_id = %d", item.getQuantity(), shipment.getStore(), item.getUpcCode());
                PreparedStatement statement = conn.prepareStatement(query);
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
    }

	public static void requestShipment(Shipment shipment){
	    insertNewShipment(shipment);
	}

    public static boolean insertNewShipment(Shipment shipment){
        String values = String.format("(%d, '%s', '%s')", shipment.getID(), shipment.getStore());
        return DataLoader.insertValuesIntoTable(values, "Shipments");
    }
}