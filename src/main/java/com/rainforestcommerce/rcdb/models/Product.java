package com.rainforestcommerce.rcdb.models;

import java.util.List;

public class Product {
    private long upcCode;
    private String productName;
    private int weight;
    private String brand;

    public Product(List<String> data){
        this.upcCode = Long.parseLong(data.get(0));
        this.productName = data.get(1);
        this.weight = Integer.parseInt(data.get(2));
        this.brand = data.get(3);
    }

    public Product(long upcCode, String productName, int weight, String brand){
        this.upcCode = upcCode;
        this.productName = productName;
        this.weight = weight;
        this.brand = brand;
    }

    public long getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(long upc_code) {
        this.upcCode = upc_code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String product_name) {
        this.productName = product_name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
