package com.rainforestcommerce.rcdb.models;

import java.util.ArrayList;

/**
 * Created by aaa10 on 7/15/2018.
 */
public class Shipment {
    private long id;
    private String store;
    public ArrayList<ProductQuantityPrice> contents;

    public Shipment(long ID, String store){
        this.id = ID;
        this.store = store;
        this.contents = new ArrayList<ProductQuantityPrice>();
    }

    public long getID() {
        return id;
    }

    public String getStore() {
        return store;
    }
}
