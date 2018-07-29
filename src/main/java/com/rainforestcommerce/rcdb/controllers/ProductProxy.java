package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Product;

public class ProductProxy {

    public static boolean insertNewProduct(Product product){
        String values = String.format("(%d, '%s', '%s', '%s')", product.getUpcCode(), product.getProductName(), product.getSize(), product.getBrand());
        return DataLoader.insertValuesIntoTable(values, "products");
    }
}
