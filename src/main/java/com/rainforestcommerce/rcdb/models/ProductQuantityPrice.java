package com.rainforestcommerce.rcdb.models;

/**
 * Represents a product with a quantity and unit price.
 * Used to represent a product in a store's inventory and
 * a product in a purchase.
 * @author Graham
 *
 */
public class ProductQuantityPrice {

    private long purchaseId;
    private long productId;
    private float unit_price;
    private int quantity;


    public ProductQuantityPrice(long purchaseId, long productId, int unit_price, int quantity){
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.unit_price = unit_price;
        this.quantity = quantity;
    }
    /**
     * Used to create a ProductPurchase from the View.
     * @param productId
     * @param unit_price
     * @param quantity
     */
    public ProductQuantityPrice(long productId, float unit_price, int quantity) {
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
    
    public float getUnitPrice() {
    	return this.unit_price;
    }
}
