package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Vendor;

import com.rainforestcommerce.rcdb.models.Shipment;

import java.util.ArrayList;

import java.sql.*;

import java.util.logging.*;

public class VendorProxy {

	private static final Logger LOGGER = Logger.getLogger( VendorProxy.class.getName() );

	public static ArrayList<Vendor> getVendors(){
        ArrayList<Vendor> vendors = new ArrayList<>();
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
        ArrayList<Shipment> shipments = new ArrayList<>();
	    try {
            Connection conn = ConnectionProxy.connect();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Shipments INNER JOIN Store ON Shipments.store_id = Store.store_id WHERE vendor_id = ?");
            statement.setString(1, Long.toString(vendor.getId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shipments.add(new Shipment(
                        rs.getLong("shipment_id"),
                        rs.getLong("store_id"),
                        rs.getLong("vendor_id")
                ));
            }
            conn.close();
        } catch(SQLException ex){
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return shipments;
	}

	public static String getVendorNameByVendorId(long vendorId){
	    String vendorName = "";
	    try{
	        Connection conn = ConnectionProxy.connect();
	        PreparedStatement ps = conn.prepareStatement("SELECT vendor_name FROM vendors WHERE vender_id=?;");
	        ps.setString(1, Long.toString(vendorId));
	        ResultSet rs = ps.executeQuery();
            vendorName = rs.getString("vendor_name");
	        conn.close();
        }catch(SQLException sqle){
	        LOGGER.warning("could net get vendorNameById for vendorId: "+vendorId);
        }
        return vendorName;
    }

    public static boolean insertNewVendor(Vendor vendor){
        String valuesFragment = String.format("(%d, '%s'", vendor.getId(), vendor.getName());
        if(vendor.getAddress() != null){
            valuesFragment += ", " + vendor.getAddress().toValues();
        }
        return DataLoader.insertValuesIntoTable(valuesFragment+")", "Vendor");
    }
}