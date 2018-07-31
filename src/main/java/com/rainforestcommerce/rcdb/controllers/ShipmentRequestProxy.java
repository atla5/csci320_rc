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
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Shipment_contents ON Product.product_id = Shipment_contents.product_id WHERE shipment_id = ?");
            statement.setString(1, Long.toString(shipment.getID()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new ProductQuantityPrice(
                        0, //MOCK UNIT PRICE
                        rs.getInt("quantity"),
                        new Product(
                            rs.getLong("upcCode"),
                            rs.getString("productName"),
                            rs.getString("brand")
                        )
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
		return products;
	}

	public static void recieveShipment(Shipment shipment, Store store){
        try {
            Connection conn = ConnectionProxy.connect();
            for (ProductQuantityPrice item : shipment.contents) {
                PreparedStatement statement = conn.prepareStatement("UPDATE store_inventory SET quantity = quantity + ? WHERE store_id = ? AND product_id = ?");
                statement.setString(1, Long.toString(item.getQuantity()));
                statement.setString(2, Long.toString(store.getStoreId()));
                statement.setString(3, Long.toString(item.getUpcCode()));
                statement.execute();
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