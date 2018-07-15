package com.rainforestcommerce.rcdb.models;

import java.util.HashMap;

/**
 * Created by aaa10 on 7/13/2018.
 */
public class Vendor {

    public HashMap<Integer,Shipment> shipments;
    private String name;

    public Vendor(Integer ID, String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
