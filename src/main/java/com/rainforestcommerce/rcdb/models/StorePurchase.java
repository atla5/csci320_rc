package com.rainforestcommerce.rcdb.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StorePurchase {
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    private long purchaseId;
    private Date dateOfPurchase;
    private long storeId;
    private long accountNumber;
    private boolean online;
    private List<Long> productPurchases;

    public StorePurchase(List<String> data) throws ParseException{
        this.purchaseId = Long.parseLong(data.get(0));
        this.dateOfPurchase = sdf.parse(data.get(1));
        this.storeId = Long.parseLong(data.get(3).replaceAll("\\D+",""));
        this.accountNumber = Long.parseLong(data.get(4).replaceAll("\\D+",""));
        this.online = "true".equalsIgnoreCase(data.get(5));
        //this.productPurchases = Long.parseLong(data.get(6));
    }

    public StorePurchase(long purchaseId, long storeId, long accountNumber){
        this.purchaseId = purchaseId;
        this.storeId = storeId;
        this.accountNumber = accountNumber;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setProductPurchases(List<Long> productPurchases) {
        this.productPurchases = productPurchases;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public long getStoreId() {
        return storeId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public boolean isOnline() {
        return online;
    }
    
}
