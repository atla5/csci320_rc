package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class ShipmentRequestProxy {

    public static ArrayList<Product> getProductsForShipment(ShipmentRequest shipment){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<Product>();
    }

    public static void requestShipment(ShipmentRequest shipment){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Create a shipment request in the database
    }
}
