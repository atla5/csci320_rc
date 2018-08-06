package com.rainforestcommerce.rcdb.models;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StorePurchase {
    private long purchaseId;
    private long storeId;
    private long accountNumber;
    private boolean online;
    private String customerName;
    public HashMap<Long, ProductQuantityPrice> products;

    public StorePurchase(long purchaseId, long storeId, long accountNumber, String customerName){
        this.purchaseId = purchaseId;
        this.storeId = storeId;
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.products = new HashMap<>();
    }
    
    // Used to create a purchase from the view side of the application
    public StorePurchase(long storeId) {
    	this.storeId = storeId;
    	this.products = new HashMap<>();
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setProductPurchases(HashMap<Long, ProductQuantityPrice> products) {
        this.products = products;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public long getStoreId() {
        return storeId;
    }
    
    public void setAccountNumber(long accountNumber) {
    	this.accountNumber = accountNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }
    
    public void setCustomerName(String name) {
    	this.customerName = name;
    }

    public String getCustomerName(){ return customerName; }

    public int getNumberOfItems(){
        int tot = 0;
        for(ProductQuantityPrice item : products.values()){
            tot += item.getQuantity();
        }
        return tot;
    }

    public float getCost() {
        float total = 0;
        for (ProductQuantityPrice item : products.values()) {
        	total += item.getOverallPrice();
        }
        return total;
    }

    public boolean isOnline() {
        return online;
    }

    public int getPurchasePoints(){
        int total = 0;
        for (ProductQuantityPrice product: products.values()){
            total+= product.getQuantity();
        }
        return total;

    }
    
}
