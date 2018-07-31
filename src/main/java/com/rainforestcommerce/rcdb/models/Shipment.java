package com.rainforestcommerce.rcdb.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aaa10 on 7/15/2018.
 */
public class Shipment {
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    long id;
    String store;
    Integer cost;
    String vendorName;

    public Shipment(long ID, String store){
        this.id = ID;
        this.store = store;
        this.vendorName = vendorName;
        this.cost = cost;

    }

    public long getID() {
        return id;
    }

    public String getStore() {
        return store;
    }

    public Integer getCost() {
        return cost;
    }

    public String getVendorName(){
        return vendorName;
    }
}
