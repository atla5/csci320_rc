/*package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Vendor;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import java.util.ArrayList;

import java.sql.*;

public class VendorProxy {
	public static ArrayList<Vendor> getVendors() throws SQLException{
        Connection conn = ConnectionProxy.cp.getConnection();
        String statement = "SELECT * FROM Vendors";
        ResultSet rs = conn.createStatement().executeQuery(statement);
        ArrayList<Vendor> vendors = null;
        while(rs.next()){
            vendors.add(new Vendor(
                    rs.getLong("storeId"),
                    rs.getString("name"),
                    rs.getDate("openingTime"),
                    rs.getDate("closingTime")
            ));
        }
        conn.close();
        return vendors;
	}

	public static ArrayList<ShipmentRequest> getShipmentsForVendor(Vendor vendor) throws SQLException{
        Connection conn = ConnectionProxy.cp.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM ShipmentRequest WHERE vendorID = ?");
        statement.setString(1, vendor.getID());
        ResultSet rs = statement.executeQuery();
        ArrayList<ShipmentRequest> shipments = null;
        while(rs.next()){
            shipments.add(new ShipmentRequest(
                    rs.getLong("upcCode"),
                    rs.getString("productName"),
                    rs.getInt("weight"),
                    rs.getString("brand")
            ));
        }
        conn.close();
        return shipments;
	}
}*/