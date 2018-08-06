package com.rainforestcommerce.rcdb.views;

import com.rainforestcommerce.rcdb.models.*;

/**
 * Provides access to session variables like current user, 
 * current store, shopping cart, etc.
 *
 */
public class SessionData {
	public static StorePurchase shoppingCart = null;
	public static Store store;
	public static Customer customer;
	public static Vendor vendor;
	public static ProductQuantityPrice productQuantityPrice;
	public static Shipment shipment;
}
