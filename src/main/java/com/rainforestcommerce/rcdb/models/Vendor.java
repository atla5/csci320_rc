package com.rainforestcommerce.rcdb.models;

import java.util.HashMap;

/**
 * Created by aaa10 on 7/13/2018.
 */
public class Vendor {

    public HashMap<Integer,Shipment> shipments;
    private String name;
    private long ID;

    public Vendor(long ID, String name){
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return ID;
    }
}
