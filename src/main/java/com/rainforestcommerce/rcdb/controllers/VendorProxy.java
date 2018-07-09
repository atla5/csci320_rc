/*package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Vendor;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class VendorProxy {

	private static final Logger LOGGER = Logger.getLogger( VendorProxy.class.getName() );

	public static ArrayList<Vendor> getVendors(){
        ArrayList<Vendor> vendors = null;
	    try {
            Connection conn = ConnectionProxy.connect();
            String statement = "SELECT * FROM Vendors";
            ResultSet rs = conn.createStatement().executeQuery(statement);
            while (rs.next()) {
                vendors.add(new Vendor(
                        rs.getLong("storeId"),
                        rs.getString("name"),
                        rs.getDate("openingTime"),
                        rs.getDate("closingTime")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return vendors;
	}

	public static ArrayList<ShipmentRequest> getShipmentsForVendor(Vendor vendor){
        ArrayList<ShipmentRequest> shipments = null;
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ShipmentRequest WHERE vendorID = ?");
            statement.setString(1, vendor.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shipments.add(new ShipmentRequest(
                        rs.getLong("upcCode"),
                        rs.getString("productName"),
                        rs.getInt("weight"),
                        rs.getString("brand")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return shipments;
	}
}*/