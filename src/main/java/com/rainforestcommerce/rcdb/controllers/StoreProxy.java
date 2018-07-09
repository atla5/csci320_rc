package com.rainforestcommerce.rcdb.controllers;

//import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.StorePurchase;

import com.rainforestcommerce.rcdb.models.Product;

import com.rainforestcommerce.rcdb.models.Store;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class StoreProxy {

    private static final Logger LOGGER = Logger.getLogger( StoreProxy.class.getName() );

	public static ArrayList<Store> getStores(){
        ArrayList<Store> stores = null;
	    try{
		    Connection conn = ConnectionProxy.connect();
		    String statement = "SELECT * FROM Store";
		    ResultSet rs = conn.createStatement().executeQuery(statement);
		    while(rs.next()){
		    	stores.add(new Store(
					rs.getLong("store_id"),
					rs.getString("store_name"),
					rs.getDate("opening_time"),
					rs.getDate("closing_time"),
					rs.getString("addr_city"),
					rs.getString("addr_state"),
					rs.getString("addr_street"),
					rs.getString("addr_zipcode"),
					rs.getString("addr_number")
	    		));
	    	}
	    	conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return stores;
	}

	public static ArrayList<StorePurchase> getPurchasesForStore(Store store){
        ArrayList<StorePurchase> purchases = null;
	    try{
		    Connection conn = ConnectionProxy.connect();
		    PreparedStatement statement = conn.prepareStatement("SELECT * FROM Purchase WHERE store_id = ?");
		    statement.setString(1, Long.toString(store.getStoreId()));
		    ResultSet rs = statement.executeQuery();
	    	while(rs.next()){
		    	purchases.add(new StorePurchase(
					rs.getLong("purchase_id"),
					rs.getLong("store_id"),
					rs.getLong("account_number")
	    		));
	    	}
		    conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return purchases;
	}

	/*public static ArrayList<ShipmentRequest> getShipmentsForStore(Store store){
        ArrayList<ShipmentRequest> shipments = null;
	    try {
            Connection conn = ConnectionProxy.cp.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Shipment WHERE store_id = ?");
            statement.setString(1, Long.toString(store.getStoreId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shipments.add(new ShipmentRequest(
                        rs.getLong("shipment_id"),
                        rs.getLong("store_id"),
                        rs.getLong("vendor_id"),
                        rs.getDate("order_date")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
		return shipments;
	}*/

	public static ArrayList<Product> getProductsForStore(Store store){ //Function will change once store inventory is added
        ArrayList<Product> products = null;
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Brand ON Product.brand_id = Brand.brand_id");
            //statement.setString(1, Long.toString(store.getStoreId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getLong("upc_code"),
                        rs.getString("product_name"),
                        rs.getInt("weight"),
                        rs.getString("brand_name")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
		return products;
	}

	public static ArrayList<Product> searchProducts(Store store, String search){ //Function will change once store inventory is added
        ArrayList<Product> products = null;
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Brand ON Product.brand_id = Brand.brand_id WHERE CHARINDEX(?, name) > 0");
            //statement.setString(1, Long.toString(store.getStoreId()));
            statement.setString(1, search);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getLong("upc_code"),
                        rs.getString("product_name"),
                        rs.getInt("weight"),
                        rs.getString("brand_name")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log(Level.SEVERE, ex.toString(), ex );
        }
		return products;
	}
}