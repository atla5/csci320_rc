package com.rainforestcommerce.rcdb.views;

import com.rainforestcommerce.rcdb.models.StorePurchase;

/**
 * Provides access to session variables like current user, 
 * current store, shopping cart, etc.
 *
 */
public class SessionData {
	public static StorePurchase shoppingCart = null;
	public static int storeId;
	public static long userId;
}
