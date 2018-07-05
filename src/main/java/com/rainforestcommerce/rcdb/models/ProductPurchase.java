package com.rainforestcommerce.rcdb.models;

public class ProductPurchase {

    private long purchaseId;
    private long productId;
    private float unit_price;
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

    public float getOverallPrice(){
        return this.unit_price * this.quantity;
    }
    
    public int getQuantity() {
    	return this.quantity;
    }
}
