package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.StorePurchase;
import com.rainforestcommerce.rcdb.models.ProductPurchase;
import com.rainforestcommerce.rcdb.models.Product;
import static com.rainforestcommerce.rcdb.controllers.DataLoader.insertValuesIntoTable;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.*;


public class PurchaseProxy {

    private static final Logger LOGGER = Logger.getLogger( PurchaseProxy.class.getName() );

	public static void purchaseProducts(StorePurchase storep, ProductPurchase productp){
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Purchase (purchase_id, date, total_price, store_id, account_number) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, Long.toString(storep.getPurchaseId()));
            statement.setString(2, storep.getDateOfPurchase().toString());
            statement.setString(3, Float.toString(productp.getOverallPrice()));
            statement.setString(4, Long.toString(storep.getStoreId()));
            statement.setString(5, Long.toString(storep.getAccountNumber()));
            statement.execute();
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
	}

	public static boolean purchasable(ProductPurchase purchase){
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Purchase WHERE purchase_id = ?");
            statement.setString(1, Long.toString(purchase.getPurchaseId()));
            ResultSet rs = statement.executeQuery();
            boolean canPurchase = (rs.getFloat("total_price") > 0);
            //I'm waiting for a store inventory to check whether the item is purchasable.
            //This will be based on checking whether each of the products in the purchase are held in the store inventory of the current store.
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return true; //Change when we have a store inventory
	}

	public static ArrayList<Product> getProductsForPurchase(ProductPurchase purchase){
        ArrayList<Product> products = null;
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Brand ON Product.brand_id = Brand.brand_id WHERE upc_code = ?");
            statement.setString(1, Long.toString(purchase.getProductId()));
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

	public static boolean insertNewStorePurchase(StorePurchase storePurchase){
        String values = String.format("(%s, %s, %s, '%s', %b)",
                storePurchase.getPurchaseId(), storePurchase.getStoreId(), storePurchase.getAccountNumber(),
                storePurchase.getDateOfPurchase(), storePurchase.isOnline());
        return insertValuesIntoTable(values, "store_purchases");
    }

    public static boolean insertNewProductPurchase(ProductPurchase productPurchase){
	    String values = String.format("(%s, %s, %d)", productPurchase.getPurchaseId(), productPurchase.getProductId(), productPurchase.getQuantity());
        return insertValuesIntoTable(values, "product_purchases");
    }

}