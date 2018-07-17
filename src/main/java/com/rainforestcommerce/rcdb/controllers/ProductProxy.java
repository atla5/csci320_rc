package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Product;

import static com.rainforestcommerce.rcdb.controllers.DataLoader.insertValuesIntoTable;

public class ProductProxy {

    public static boolean insertNewProduct(Product product){
        return insertNewProduct(product.getUpcCode(), product.getProductName(), product.getWeight(), product.getBrand());
    }

    public static boolean insertNewProduct(long upcCode, String productName, int weight, String brandName){
        String values = String.format("(%s, '%s', %s, '%s')", upcCode, productName, weight, brandName);
        return insertValuesIntoTable(values, "products");
    }
}
