package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.StorePurchase;
import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.models.Customer;
import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.*;


public class PurchaseProxy {

    private static final Logger LOGGER = Logger.getLogger( PurchaseProxy.class.getName() );

	public static void purchaseProducts(StorePurchase storep){
	    insertNewStorePurchase(storep);
	}

	public static ArrayList<Product> getProductsForPurchase(ProductQuantityPrice purchase){
        ArrayList<Product> products = new ArrayList<Product>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Brand ON Product.brand_id = Brand.brand_id WHERE upc_code = ? ORDER BY product_name");
            statement.setString(1, Long.toString(purchase.getUpcCode()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getLong("upc_code"),
                        rs.getString("product_name"),
                        rs.getString("brand_name")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return products;
	}

	public static void makePurchase(StorePurchase storePurchase){
        Long newStorePurchaseId = insertNewStorePurchase(storePurchase);
        if(newStorePurchaseId == null) {
			LOGGER.severe("Error creating new StorePurchase. ");
			return;
		}
        try {
            Connection conn = ConnectionProxy.connect();
            for (ProductQuantityPrice item : storePurchase.products.values()) {
                PreparedStatement statement = conn.prepareStatement("UPDATE store_inventory SET quantity = quantity - ? WHERE store_id = ? AND product_id = ?");
                statement.setString(1, Long.toString(item.getQuantity()));
                statement.setString(2, Long.toString(storePurchase.getStoreId()));
                statement.setString(3, Long.toString(item.getUpcCode()));
                statement.execute();
                item.setPurchaseId(newStorePurchaseId);
                insertNewProductPurchase(item);
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
    }

	private static Long insertNewStorePurchase(StorePurchase storePurchase){
        String values = String.format("(DEFAULT, %d, %d, %b)",
				storePurchase.getStoreId(), storePurchase.getAccountNumber(), storePurchase.isOnline());
        boolean insertSuccessful = DataLoader.insertValuesIntoTable(values, "store_purchases");
		if(!insertSuccessful){ return null; }
		else{
			Connection conn = null;
			Long newId = null;
			try{
				conn = ConnectionProxy.connect();
				PreparedStatement id_current_statement = conn.prepareStatement("SELECT MAX(purchase_id) FROM store_purchases;");
				ResultSet rs = id_current_statement.executeQuery();
				if(rs.next()) {
					newId = rs.getLong(1);
				}
			}catch(SQLException sqle){
				LOGGER.warning("new ProductPurchase inserted successfully, but unable to acquire new ID!");
				sqle.printStackTrace();
			}finally{
				if(conn != null){
					try{ conn.close(); }
					catch (SQLException sqle){
						LOGGER.warning("error closing connection in insertNewStorePurchase");
					}
				}
			}
			return newId;
		}
    }

    private static boolean insertNewProductPurchase(ProductQuantityPrice productPurchase){
	    String values = String.format("(%d, %d, %d)", productPurchase.getPurchaseId(), productPurchase.getUpcCode(), productPurchase.getQuantity());
        return DataLoader.insertValuesIntoTable(values, "product_purchases");
    }
    
    public static long[] getPurchaseStatsForProduct(Product product) {
    	String command = "SELECT COUNT(quantity) AS quantity_purchased, COUNT(DISTINCT(account_number)) AS distinct_purchasers FROM product_purchases inner join store_purchases on store_id WHERE product_id=" + product.getUpcCode() + ";";
    	try {
    		Connection connection = ConnectionProxy.connect();
    		if (connection != null) {
				ResultSet results = connection.prepareStatement(command).executeQuery();
				if (results.next()) {
					long[] count = {results.getLong("quantity_purchased"), results.getLong("distinct_purchasers")};
					connection.close();
					return count;
				}
				connection.close();
    		}
		} catch (SQLException ex) {
			LOGGER.log( Level.SEVERE, ex.toString(), ex );
		}
    	long[] count = {0,0};
		return count;
    }
    
    public static long getPointsForCustomer(Customer customer) {
    	String command = "select sum(quantity) from (product_purchases inner join store_purchases on product_purchases.purchase_id = store_purchases.purchase_id) where account_number = " + customer.getAccountNumber();
    	try {
    		Connection connection = ConnectionProxy.connect();
    		if (connection != null) {
    			ResultSet results = connection.prepareStatement(command).executeQuery();
    			if (results.next()) {
    				long result = results.getLong("sum(quantity)");
    				connection.close();
    				return result;
    			}
    		}
    		connection.close();
		} catch (SQLException ex) {
			LOGGER.log( Level.SEVERE, ex.toString(), ex );
		}
    	return 0;
    }

	public static long getNumPurchasesPerStore(Long store_id, Long product_id) {
		String command = "select sum(quantity) from product_purchases inner join store_purchases on product_purchases.purchase_id = store_purchases.purchase_id where product_purchases.product_id = "+ product_id +" and store_purchases.store_id = "+ store_id;
		try {
			Connection connection = ConnectionProxy.connect();
			if (connection != null) {
				ResultSet results = connection.prepareStatement(command).executeQuery();
				if (results.next()) {
					long result = results.getLong("sum(quantity)");
					connection.close();
					return result;
				}
			}
			connection.close();
		} catch (SQLException ex) {
			LOGGER.log( Level.SEVERE, ex.toString(), ex );
		}
		return 0;
	}

}