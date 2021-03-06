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
            String statement = "SELECT * FROM Vendors ORDER BY vendor_name";
            ResultSet rs = conn.createStatement().executeQuery(statement);
            while (rs.next()) {
                Vendor vendor = new Vendor(
                        rs.getLong("vendor_id"),
                        rs.getString("vendor_name")
                );
                vendor.shipments = getShipmentsForVendor(vendor);
                vendors.add(vendor);
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
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Shipments INNER JOIN stores ON stores.store_id = Shipments.store_id WHERE vendor_id = ? ORDER BY store_name");
            statement.setString(1, Long.toString(vendor.getId()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Shipment shipment = new Shipment(
                        rs.getLong("shipment_id"),
                        rs.getString("store_name")
                );
                shipment.contents = ShipmentRequestProxy.getProductsForShipment(shipment);
                shipments.add(shipment);
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