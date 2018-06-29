package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.ProductPurchase;

import com.rainforestcommerce.rcdb.models.Product;

import java.util.ArrayList;

public class PurchaseProxy {

    public static void purchaseProducts(ProductPurchase purchase){
        //Add Product Purchases to database
    }

    public static boolean purchasable(ProductPurchase purchase){
        return true;
    }

    public static ArrayList<Product> getProductsForPurchase(ProductPurchase purchase){
        return new ArrayList<Product>();
    }
}
