package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.*;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class StoreProxy {

    private static final Logger LOGGER = Logger.getLogger( StoreProxy.class.getName() );

    public static ArrayList<Store> getStores(){
        ArrayList<Store> stores = new ArrayList<Store>();
         try{
             Connection conn = ConnectionProxy.connect();
             String statement = "SELECT * FROM Stores INNER JOIN Store_inventory ON Stores.store_id = Store_inventory.store_id INNER JOIN Products ON Products.upc_code = Store_inventory.product_id ORDER BY store_id";
             ResultSet rs = conn.createStatement().executeQuery(statement);
             ArrayList<Long> places = new ArrayList<Long>();
             Store placeholder = null;
             while(rs.next()){
                 if(placeholder != null && placeholder.getStoreId() != rs.getLong("store_id")){
                     stores.add(placeholder);
                     places.add(placeholder.getStoreId());
                     placeholder = null;
                 }
                 if(placeholder == null){
                     placeholder = new Store(
                             rs.getLong("store_id"),
                             rs.getString("store_name"),
                             rs.getInt("addr_num"),
                             rs.getString("addr_street"),
                             rs.getString("addr_city"),
                             rs.getString("addr_state"),
                             rs.getInt("addr_zipcode")
                     );
                 }
                 ProductQuantityPrice pqp = new ProductQuantityPrice(rs.getLong("unit_price"), rs.getInt("quantity"), new Product(rs.getLong("product_id"), rs.getString("product_name"), rs.getString("weight"), rs.getString("brand_name")));
                 placeholder.inventory.put(pqp.getUpcCode(), pqp);
             }

             statement = "SELECT * FROM Stores INNER JOIN store_purchases ON Stores.store_id = store_purchases.store_id INNER JOIN product_purchases ON product_purchases.purchase_id = store_purchases.purchase_id INNER JOIN store_inventory ON product_purchases.product_id = store_inventory.product_id AND Stores.store_id = store_inventory.store_id INNER JOIN Products ON product_purchases.product_id = Products.upc_code ORDER BY store_id";
             rs = conn.createStatement().executeQuery(statement);
             StorePurchase placeholder2 = null;
             int place = 0;
             while(rs.next()){
                 if(placeholder2 != null && placeholder2.getStoreId() != rs.getLong("store_id")){
                     stores.get(place).purchase.put(placeholder2.getPurchaseId(), placeholder2);
                     placeholder2 = null;
                 }
                 if(placeholder2 == null){
                     placeholder2 = new StorePurchase(
                             rs.getLong("purchase_id"),
                             rs.getLong("store_id"),
                             rs.getLong("account_number")
                     );
                 }
                 while(placeholder2 != null && place < places.size() && places.get(place) != rs.getLong("store_id")){
                     place++;
                 }
                 ProductQuantityPrice pqp = new ProductQuantityPrice(rs.getLong("purchase_id"), rs.getInt("unit_price"), rs.getInt("quantity"), new Product(rs.getLong("upc_code"), rs.getString("product_name"), rs.getString("weight"), rs.getString("brand_name")));
                 placeholder2.products.put(pqp.getPurchaseId(), pqp);
                 if(place > places.size()){
                     break;
                 }
             }

             statement = "SELECT * FROM Stores INNER JOIN shipments ON Stores.store_id = shipments.store_id ORDER BY store_id";
             rs = conn.createStatement().executeQuery(statement);
             Shipment placeholder3 = null;
             place = 0;
             while(rs.next()){
                 if(placeholder3 != null && placeholder3.getID() != rs.getLong("shipment_id")){
                     stores.get(place).shipment.put(placeholder3.getID(), placeholder3);
                     placeholder3 = null;
                 }
                 if(placeholder3 == null){
                     placeholder3 = new Shipment(
                             rs.getLong("shipment_id"),
                             rs.getString("store_name"),
                             rs.getString("order_date")
                     );
                 }
                 while(placeholder3 != null && place < places.size() && places.get(place) != rs.getLong("store_id")){
                     place++;
                 }
                 if(place >= places.size()){
                     break;
                 }
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
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Shipment INNER JOIN Stores ON Shipments.store_id = Stores.store_id WHERE store_id = ?");
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

	public static ArrayList<Product> getProductsForStore(Store store){ //Function will change once store inventory is added
        ArrayList<Product> products = new ArrayList<Product>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Products INNER JOIN Brand ON Products.brand_id = Brand.brand_id");
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
		String values = String.format("(%d, '%s', '%s')",
			store.getStoreId(), store.getName(), store.getAddress().toValues()
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
	           String statement = "CREATE TABLE Stores ( store_id long, store_name varchar(255) NOT NULL, opening_time TIME, closing_time TIME, addr_num int, addr_street varchar(255), addr_city varchar(255), addr_state varchar(255), addr_zipcode int )";
	           conn.createStatement().execute(statement);
	           String statement2 = "INSERT INTO Stores (store_id, store_name, opening_time, closing_time, addr_num, addr_street, addr_state, addr_city, addr_zipcode) VALUES (12, 'applesauce', '10:34:09', '09:43:08', 96, 'average drive', 'Texas', 'Austin', 98765)";
	           conn.createStatement().execute(statement2);
	           conn.close();
	       } catch(Exception ex){
	           LOGGER.log( Level.SEVERE, ex.toString(), ex );
	       }
	   }
}