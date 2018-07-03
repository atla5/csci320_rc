package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import com.rainforestcommerce.rcdb.models.Product;

import com.rainforestcommerce.rcdb.models.Store;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class StoreProxy {
    public static ArrayList<Store> getStores(){
        Connection conn = ConnectionProxy.cp.getConnection();
        String statement"SELECT * FROM Stores";
        ResultSet rs = conn.createStatement().executeQuery(statement);
        ArrayList<Store> stores = null;
        while(rs.next()){
            stores.add(new Store(
                    rs.getLong("storeId"),
                    rs.getString("name"),
                    rs.getDate("openingTime"),
                    rs.getDate("closingTime"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("street"),
                    rs.getString("zip"),
                    rs.getString("number")
            ));
        }
        conn.close();
        //Write Database Access code here
        return stores;
    }

    public static ArrayList<ProductPurchase> getPurchasesForStore(Store store){
        Connection conn = ConnectionProxy.cp.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM ProductPurchases WHERE storeID = ?");
        statement.setString(1, Long.toString(store.getStoreId()));
        ResultSet rs = conn.createStatement().executeQuery(statement);
        ArrayList<ProductPurchase> purchases = null;
        while(rs.next()){
            purchases.add(new ProductPurchase(
                    rs.getLong("purchaseId"),
                    rs.getLong("productId"),
                    rs.getInt("unit_price"),
                    rs.getInt("quantity")
            ));
        }
        conn.close();
        //Write Database Access code here
        return purchases;
    }

    public static ArrayList<ShipmentRequest> getShipmentsForStore(Store store){
        Connection conn = ConnectionProxy.cp.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM ShipmentRequest WHERE storeID = ?");
        statement.setString(1, Long.toString(store.getStoreId()));
        ResultSet rs = conn.createStatement().executeQuery(statement);
        ArrayList<ShipmentRequest> shipments = null;
        while(rs.next()){
            shipments.add(new ShipmentRequest(
                    rs.getLong("purchaseId"),
                    rs.getLong("productId"),
                    rs.getInt("unit_price"),
                    rs.getInt("quantity")
            ));
        }
        conn.close();
        //Write Database Access code here
        return shipments;
    }

    public static ArrayList<Product> getProductsForStore(Store store){
        Connection conn = ConnectionProxy.cp.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Products WHERE storeID = ?");
        statement.setString(1, Long.toString(store.getStoreId()));
        ResultSet rs = conn.createStatement().executeQuery(statement);
        ArrayList<Product> products = null;
        while(rs.next()){
            products.add(new Product(
                    rs.getLong("upcCode"),
                    rs.getString("name"),
                    rs.getInt("weight"),
                    rs.getString("brand")
            ));
        }
        conn.close();
        //Write Database Access code here
        return products;
    }

    public static ArrayList<Product> searchProducts(Store store, String search){
        Connection conn = ConnectionProxy.cp.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Products WHERE storeID = ? AND CHARINDEX(?, name) > 0");
        statement.setString(1, Long.toString(store.getStoreId()));
        statement.setString(2, search);
        ResultSet rs = conn.createStatement().executeQuery(statement);
        ArrayList<Product> products = null;
        while(rs.next()){
            products.add(new Product(
                    rs.getLong("upcCode"),
                    rs.getString("name"),
                    rs.getInt("weight"),
                    rs.getString("brand")
            ));
        }
        conn.close();
        //Write Database Access code here
        return products;
    }
}
