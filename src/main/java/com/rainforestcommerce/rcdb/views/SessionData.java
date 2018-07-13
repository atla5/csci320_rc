package com.rainforestcommerce.rcdb.views;

import com.rainforestcommerce.rcdb.models.Store;
import com.rainforestcommerce.rcdb.models.StorePurchase;
import com.rainforestcommerce.rcdb.models.Vendor;

/**
 * Provides access to session variables like current user, 
 * current store, shopping cart, etc.
 *
 */
public class SessionData {
	public static StorePurchase shoppingCart = null;
	public static Store store;
	public static long userId;
	public static Vendor vendor;
}
