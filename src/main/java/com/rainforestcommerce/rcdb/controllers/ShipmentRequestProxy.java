package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class ShipmentRequestProxy {

    public static ArrayList<Product> getProductsForShipment(ShipmentRequest shipment){
        Connection conn = ConnectionProxy.cp.getConnection();
        PreparedStatement statement = onnection.prepareStatment("SELECT data FROM Product WHERE ID = ?");
        statement.setString(1, shipment.prodID);
        ResultSet rs = conn.createStatement().executeQuery(statement);
        ArrayList<Product> products;
        products = rs.getArray("data");
        //while(rs.next()){
        //    products.add(new Product(rs.getString("data")));
        //}
        conn.close();
        //Write Database Access code here
        return products;
    }

    public static void requestShipment(ShipmentRequest shipment){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Create a shipment request in the database
    }
}
