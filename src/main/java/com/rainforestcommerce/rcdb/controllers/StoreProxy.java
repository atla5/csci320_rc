package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ShipmentRequest;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import com.rainforestcommerce.rcdb.models.Product;

import com.rainforestcommerce.rcdb.models.Store;

import java.util.ArrayList;

public class StoreProxy {
    public static ArrayList<Store> getStores(){
        //Write Database Access code here
        return new ArrayList<Store>();
    }

    public static ArrayList<ProductPurchase> getPurchasesForStore(Store store){
        //Write Database Access code here
        return new ArrayList<ProductPurchase>();
    }

    public static ArrayList<ShipmentRequest> getShipmentsForStore(Store store){
        //Write Database Access code here
        return new ArrayList<ShipmentRequest>();
    }

    public static ArrayList<Product> getProductsForStore(Store store){
        //Write Database Access code here
        return new ArrayList<Product>();
    }

    public static ArrayList<Product> searchProducts(Store store, String search){
        return new ArrayList<Product>();
    }
}
