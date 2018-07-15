package com.rainforestcommerce.rcdb.models;

import java.util.List;

public class Product {
    protected long upcCode;
    protected String productName;
    protected String size;
    protected String brand;

    public Product(List<String> data){
        this.upcCode = Long.parseLong(data.get(0));
        this.productName = data.get(1);
        this.size = data.get(2);
        this.brand = data.get(3);
    }

    public Product(Long upcCode, String productName, String size, String brand){
        this.upcCode = upcCode;
        this.productName = productName;
        this.size = size;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
