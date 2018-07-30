package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.*;

import java.util.ArrayList;

import java.sql.*;

import java.util.HashMap;
import java.util.logging.*;

public class StoreProxy {

    private static final Logger LOGGER = Logger.getLogger( StoreProxy.class.getName() );

    public static ArrayList<Store> getStores(){
        ArrayList<Store> stores = new ArrayList<Store>();
         try{
             Connection conn = ConnectionProxy.connect();
             String statement = "SELECT * FROM Stores";
             ResultSet rs = conn.createStatement().executeQuery(statement);
             while(rs.next()){
                 Store store = new Store(
                     rs.getLong("store_id"),
                     rs.getString("store_name"),
                     //rs.getTime("opening_time"),
                     //rs.getTime("closing_time"),
                     rs.getString("opening_time"),
                     rs.getString("closing_time"),
                     rs.getString("addr_city"),
                     rs.getString("addr_state"),
                     rs.getInt("addr_zipcode"),
                     rs.getString("addr_street"),
                     rs.getInt("addr_num")
                 );
                 store.inventory = getInventoryForStore(store);
                 store.purchase = getPurchasesForStore(store);
                 store.shipment = getShipmentsForStore(store);
                 stores.add(store);
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
		    PreparedStatement statement = conn.prepareStatement("SELECT * FROM store_purchases WHERE store_id = ?");
            PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM product_purchases INNER JOIN product ON product.product_id = product_purchase.product_id WHERE purchase_id = ?");
            statement.setString(1, Long.toString(store.getStoreId()));
		    ResultSet rs = statement.executeQuery();
		    int place = 0;
	    	while(rs.next()){
		    	purchases.add(new StorePurchase(
					rs.getLong("purchase_id"),
					rs.getLong("store_id"),
					rs.getLong("account_number")
	    		));
                statement2 = conn.prepareStatement("SELECT * FROM product_purchases INNER JOIN product ON product.product_id = product_purchase.product_id WHERE purchase_id = ?");
                statement2.setString(1, Long.toString(rs.getLong("purchase_id")));
                ResultSet rs2 = statement2.executeQuery();
                HashMap<Long, ProductQuantityPrice> hmap = new HashMap<Long, ProductQuantityPrice>();
                while(rs2.next()){
                    hmap.put(rs.getLong("account_number"), new ProductQuantityPrice(
                        rs.getLong("purchase_id"),
                        rs.getInt("unit_price"),
                        rs.getInt("quantity"),
                        new Product(
                            rs.getLong("upc_code"),
                            rs.getString("product_name"),
                            rs.getString("weight"),
                            rs.getString("brand_name")
                        )
                    ));

                }
                purchases.get(place).products = hmap;
                place++;
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
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Shipments WHERE store_id = ?");
            statement.setString(1, Long.toString(store.getStoreId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shipments.add(new Shipment(
                    rs.getLong("shipment_id"),
                    rs.getString("store_name"),
                    rs.getString("order_date")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
		return shipments;
	}

	public static ArrayList<ProductQuantityPrice> getInventoryForStore(Store store){
        ArrayList<ProductQuantityPrice> products = new ArrayList<ProductQuantityPrice>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Store_inventory INNER JOIN product ON product.product_id = store_inventory.product_id WHERE store_id = ?");
            statement.setString(1, Long.toString(store.getStoreId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new ProductQuantityPrice(
                    rs.getFloat("unit_price"),
                    rs.getInt("quantity"),
                    new Product(
                        rs.getLong("upc_code"),
                        rs.getString("product_name"),
                        rs.getString("size"),
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

	public static ArrayList<Product> searchProducts(Store store, String search){ //Function will change once store inventory is added
        ArrayList<Product> products = new ArrayList<Product>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM store_inventory INNER JOIN product ON store_inventory.product_id = product.product_id WHERE store_id = ? AND CHARINDEX(?, name) > 0");
            statement.setString(1, Long.toString(store.getStoreId()));
            statement.setString(2, search);
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
}