package com.rainforestcommerce.rcdb.models;

public class ProductPurchase {

    private long purchaseId;
    private long productId;
    private int unit_price;
    private int quantity;


    public ProductPurchase(long purchaseId, long productId, int unit_price, int quantity){
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.unit_price = unit_price;
        this.quantity = quantity;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public long getProductId() {
        return productId;
    }

    public int getOverallPrice(){
        return this.unit_price * this.quantity;
    }
}
