package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Vendor;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import java.util.ArrayList;

public class VendorProxy {
    public static ArrayList<Vendor> getVendors() {
        //Write Database Access code here
        return new ArrayList<Vendor>();
    }

    public static ArrayList<ShipmentRequest> getShipmentsForVendor(Vendor vendor){
        //Write Database Access code here
        return new ArrayList<ShipmentRequest>();
    }
}
