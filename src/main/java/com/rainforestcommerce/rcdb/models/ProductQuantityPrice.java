package com.rainforestcommerce.rcdb.models;

/**
 * Represents a product with a quantity and unit price.
 * Used to represent a product in a store's inventory and
 * a product in a purchase.
 * @author Graham
 *
 */
public class ProductQuantityPrice extends Product {

    private long purchaseId;
    private float unit_price;
    private int quantity;


    public ProductQuantityPrice(long purchaseId, int unit_price, int quantity, Product product){
        super(product.upcCode, product.productName, product.brand);
    	this.purchaseId = purchaseId;
        this.unit_price = unit_price;
        this.quantity = quantity;
    }
    
    /**
     * Used to create a ProductPurchase from the View.
     * @param product
     * @param unit_price
     * @param quantity
     */
    public ProductQuantityPrice(float unit_price, int quantity, Product product) {
    	super(product.upcCode, product.productName, product.brand);
        this.unit_price = unit_price;
        this.quantity = quantity;
    }

    public long getPurchaseId() {
        return purchaseId;
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
