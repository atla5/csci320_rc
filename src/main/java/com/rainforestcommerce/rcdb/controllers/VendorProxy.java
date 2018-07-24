package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Vendor;

import com.rainforestcommerce.rcdb.models.Shipment;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class VendorProxy {

	private static final Logger LOGGER = Logger.getLogger( VendorProxy.class.getName() );

	public static ArrayList<Vendor> getVendors(){
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();
	    try {
            Connection conn = ConnectionProxy.connect();
            String statement = "SELECT * FROM Vendors";
            ResultSet rs = conn.createStatement().executeQuery(statement);
            while (rs.next()) {
                vendors.add(new Vendor(
                        rs.getLong("vendor_id"),
                        rs.getString("vendor_name")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return vendors;
	}

	public static ArrayList<Shipment> getShipmentsForVendor(Vendor vendor){
        ArrayList<Shipment> shipments = new ArrayList<Shipment>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Shipments INNER JOIN Store ON Shipments.store_id = Store.store_id WHERE vendor_id = ?");
            statement.setString(1, Long.toString(vendor.getId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shipments.add(new Shipment(
                        rs.getInt("shipment_id"),
                        rs.getString("store_name"),
                        rs.getString("order_date")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return shipments;
	}

    public static boolean insertNewVendor(Vendor vendor){
        String values = String.format("(%d, '%s')", vendor.getId(), vendor.getName());
        return DataLoader.insertValuesIntoTable(values, "Vendor");
    }
}