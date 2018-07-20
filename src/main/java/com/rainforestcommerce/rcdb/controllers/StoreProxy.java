package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Shipment;

import com.rainforestcommerce.rcdb.models.StorePurchase;

import com.rainforestcommerce.rcdb.models.Product;

import com.rainforestcommerce.rcdb.models.Store;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class StoreProxy {

    private static final Logger LOGGER = Logger.getLogger( StoreProxy.class.getName() );

    public static ArrayList<Store> getStores(){
        ArrayList<Store> stores = new ArrayList<Store>();
         try{
             Connection conn = ConnectionProxy.connect();
             String statement = "SELECT * FROM Store";
             ResultSet rs = conn.createStatement().executeQuery(statement);
             while(rs.next()){
                 stores.add(new Store(
                     rs.getLong("store_id"),
                     rs.getString("store_name"),
                     rs.getTime("opening_time"),
                     rs.getTime("closing_time"),
                     rs.getString("addr_city"),
                     rs.getString("addr_state"),
                     rs.getInt("addr_zipcode"),
                     rs.getString("addr_street"),
                     rs.getInt("addr_num")
                 ));
             }
             conn.close();
        } catch(Exception ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return stores;
     }

	public static ArrayList<StorePurchase> getPurchasesForStore(Store store){
        ArrayList<StorePurchase> purchases = new ArrayList<StorePurchase>();
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

	public static ArrayList<Shipment> getShipmentsForStore(Store store){
        ArrayList<Shipment> shipments = new ArrayList<Shipment>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Shipment INNER JOIN Store ON Shipments.store_id = Store.store_id WHERE store_id = ?");
            statement.setString(1, Long.toString(store.getStoreId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shipments.add(new Shipment(
                        rs.getLong("shipment_id"),
                        rs.getString("store_name"),
                        rs.getDate("order_date")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
		return shipments;
	}

	public static ArrayList<Product> getProductsForStore(Store store){ //Function will change once store inventory is added
        ArrayList<Product> products = new ArrayList<Product>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Brand ON Product.brand_id = Brand.brand_id");
            //statement.setString(1, Long.toString(store.getStoreId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getLong("upc_code"),
                        rs.getString("product_name"),
                        rs.getString("size"),
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
        ArrayList<Product> products = new ArrayList<Product>();
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
                        rs.getString("size"),
                        rs.getString("brand_name")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log(Level.SEVERE, ex.toString(), ex );
        }
		return products;
	}

	public static boolean insertNewStore(Store store){
		String values = String.format("(%d, '%s', '%s', '%s', %s)",
			store.getStoreId(), store.getName(), store.getOpeningTime().toString(), store.getClosingTime().toString(), store.getAddress().toValues()
		);
		return DataLoader.insertValuesIntoTable(values, "stores");
	}

	public static boolean insertNewInventoryItemIntoStore(Store store, Product product, float unitPrice, int quantity){
		String values = String.format("(%d, %d, %f, %d)", store.getStoreId(), product.getUpcCode(), unitPrice, quantity);
		return DataLoader.insertValuesIntoTable(values, "store_inventory");
	}
	
	 public static void createStores() { //Testing Function
	       try{
	           Connection conn = ConnectionProxy.connect();
	           String statement = "CREATE TABLE Store ( store_id long, store_name varchar(255) NOT NULL, opening_time TIME, closing_time TIME, addr_num int, addr_street varchar(255), addr_city varchar(255), addr_state varchar(255), addr_zipcode int )";
	           conn.createStatement().execute(statement);
	           String statement2 = "INSERT INTO Store (store_id, store_name, opening_time, closing_time, addr_num, addr_street, addr_state, addr_city, addr_zipcode) VALUES (12, 'applesauce', '10:34:09', '09:43:08', 96, 'average drive', 'Texas', 'Austin', 98765)";
	           conn.createStatement().execute(statement2);
	           conn.close();
	       } catch(Exception ex){
	           LOGGER.log( Level.SEVERE, ex.toString(), ex );
	       }
	   }
}