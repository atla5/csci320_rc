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
        String statement"SELECT * FROM ProductPurchases";
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
        return new ArrayList<ProductPurchase>();
    }

    public static ArrayList<ShipmentRequest> getShipmentsForStore(Store store){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<ShipmentRequest>();
    }

    public static ArrayList<Product> getProductsForStore(Store store){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<Product>();
    }

    public static ArrayList<Product> searchProducts(Store store, String search){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<Product>();
    }
}
