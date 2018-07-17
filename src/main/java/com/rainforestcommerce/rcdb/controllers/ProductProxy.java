package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Product;

import static com.rainforestcommerce.rcdb.controllers.DataLoader.insertValuesIntoTable;

public class ProductProxy {

    public static boolean insertNewProduct(Product product){
        String values = String.format("(%s, '%s', %s, '%s')", product.getUpcCode(), product.getProductName(), product.getWeight(), product.getBrand());
        return insertValuesIntoTable(values, "products");
    }
}
