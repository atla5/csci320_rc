package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.StorePurchase;
import com.rainforestcommerce.rcdb.models.ProductQuantityPrice;
import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;
import java.sql.*;
import java.util.logging.*;


public class PurchaseProxy {

    private static final Logger LOGGER = Logger.getLogger( PurchaseProxy.class.getName() );

	public static void purchaseProducts(StorePurchase storep){
	    insertNewStorePurchase(storep);
	}

	public static boolean purchasable(ProductQuantityPrice purchase){
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

	public static ArrayList<Product> getProductsForPurchase(ProductQuantityPrice purchase){
        ArrayList<Product> products = new ArrayList<Product>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Brand ON Product.brand_id = Brand.brand_id WHERE upc_code = ?");
            statement.setString(1, Long.toString(purchase.getUpcCode()));
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

	public static void makePurchase(StorePurchase storePurchase){
	    insertNewStorePurchase(storePurchase);
        for (ProductQuantityPrice item : storePurchase.products.values()) {
            insertNewProductPurchase(item);
        }
    }

	public static boolean insertNewStorePurchase(StorePurchase storePurchase){
        String values = String.format("(%d, %d, %d, '%s', %b)",
                storePurchase.getPurchaseId(), storePurchase.getStoreId(), storePurchase.getAccountNumber(),
                storePurchase.getDateOfPurchase().toString(), storePurchase.isOnline());
        return DataLoader.insertValuesIntoTable(values, "store_purchases");
    }

    public static boolean insertNewProductPurchase(ProductQuantityPrice productPurchase){
	    String values = String.format("(%d, %d, %d)", productPurchase.getPurchaseId(), productPurchase.getUpcCode(), productPurchase.getQuantity());
        return DataLoader.insertValuesIntoTable(values, "product_purchases");
    }

}