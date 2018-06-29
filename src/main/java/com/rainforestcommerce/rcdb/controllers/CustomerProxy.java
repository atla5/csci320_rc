package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Customer;

import com.rainforestcommerce.rcdb.models.Product;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import java.util.ArrayList;

public class CustomerProxy {
    public static ArrayList<Customer> getCustomers() {
        //Write Database Access code here
        return new ArrayList<Customer>();
    }

    public static ArrayList<Product> getProducts() {
        //Write Database Access code here
        return new ArrayList<Product>();
    }

    public static void purchaseProducts(ProductPurchase purchase){
        //Add Product Purchases to database
    }

    public static boolean purchasable(ProductPurchase purchase){
        return true;
    }

    public static ArrayList<Product> searchProducts(String search){
        return new ArrayList<Product>();
    }
}
