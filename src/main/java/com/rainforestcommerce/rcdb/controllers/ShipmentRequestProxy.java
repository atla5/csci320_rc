package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

public class ShipmentRequestProxy {

    public static ArrayList<Product> getProductsForShipment(ShipmentRequest shipment){
        //Write Database Access code here
        return new ArrayList<Product>();
    }

    public static void requestShipment(ShipmentRequest shipment){
        //Create a shipment request in the database
    }
}
