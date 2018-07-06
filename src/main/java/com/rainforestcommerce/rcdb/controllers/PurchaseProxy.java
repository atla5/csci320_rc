package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.StorePurchase;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

import java.sql.*;

public class PurchaseProxy {

	public static void purchaseProducts(StorePurchase storep, ProductPurchase productp) throws SQLException{
        Connection conn = ConnectionProxy.connect();
	    PreparedStatement statement = conn.prepareStatement("INSERT INTO Purchase (purchase_id, date, total_price, store_id, account_number) VALUES (?, ?, ?, ?, ?)");
	    statement.setString(1, Long.toString(storep.getPurchaseId()));
	    statement.setString(2, storep.getDateOfPurchase().toString());
        statement.setString(3, Float.toString(productp.getOverallPrice()));
        statement.setString(4, Long.toString(storep.getStoreId()));
        statement.setString(5, Long.toString(storep.getAccountNumber()));
        statement.execute();
        conn.close();
	}

	public static boolean purchasable(ProductPurchase purchase) throws SQLException{
        Connection conn = ConnectionProxy.connect();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Purchase WHERE purchase_id = ?");
        statement.setString(1, Long.toString(purchase.getPurchaseId()));
        ResultSet rs = statement.executeQuery();
        boolean canPurchase = (rs.getFloat("total_price") > 0);
        //I'm not sure what defines whether it's purchasable, but this looks good as anything.
        conn.close();
        return canPurchase;
	}

	public static ArrayList<Product> getProductsForPurchase(ProductPurchase purchase) throws SQLException{
        Connection conn = ConnectionProxy.connect();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Product INNER JOIN Brand ON Product.brand_id = Brand.brand_id WHERE upc_code = ?");
        statement.setString(1, Long.toString(purchase.getProductId()));
        ResultSet rs = statement.executeQuery();
        ArrayList<Product> products = null;
        while(rs.next()){
            products.add(new Product(
                    rs.getLong("upc_code"),
                    rs.getString("product_name"),
                    rs.getInt("weight"),
                    rs.getString("brand_name")
            ));
        }
        conn.close();
        return products;
	}
}