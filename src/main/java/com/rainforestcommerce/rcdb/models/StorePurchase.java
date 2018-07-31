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
    public HashMap<Long, ProductQuantityPrice> products;

    public StorePurchase(long purchaseId, long storeId, long accountNumber){
        this.purchaseId = purchaseId;
        this.storeId = storeId;
        this.accountNumber = accountNumber;
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

    public long getAccountNumber() {
        return accountNumber;
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
