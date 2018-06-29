package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import com.rainforestcommerce.rcdb.models.Product;

import com.rainforestcommerce.rcdb.models.Store;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class StoreProxy {
    public static ArrayList<Store> getStores(JdbcConnectionPool cp){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<Store>();
    }

    public static ArrayList<ProductPurchase> getPurchasesForStore(JdbcConnectionPool cp, Store store){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<ProductPurchase>();
    }

    public static ArrayList<ShipmentRequest> getShipmentsForStore(JdbcConnectionPool cp, Store store){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<ShipmentRequest>();
    }

    public static ArrayList<Product> getProductsForStore(JdbcConnectionPool cp, Store store){
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
