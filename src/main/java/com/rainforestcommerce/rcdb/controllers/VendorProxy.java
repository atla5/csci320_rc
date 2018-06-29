package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Vendor;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class VendorProxy {
    public static ArrayList<Vendor> getVendors() {
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<Vendor>();
    }

    public static ArrayList<ShipmentRequest> getShipmentsForVendor(Vendor vendor){
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<ShipmentRequest>();
    }
}
