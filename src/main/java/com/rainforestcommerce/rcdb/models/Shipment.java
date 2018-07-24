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
    Date requestDate;
    Date arrivalDate;
    Integer cost;
    String vendorName;

    public Shipment(long ID, String store, String requestDate){
        this.id = ID;
        this.store = store;
        try {
            this.requestDate = sdf.parse(requestDate);
        } catch(ParseException pe){
            System.exit(1);
        }
        this.vendorName = vendorName;
        this.cost = cost;
        this.arrivalDate = arrivalDate;

    }

    public long getID() {
        return id;
    }

    public String getStore() {
        return store;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setArrivalDate(Date arrivalDate){
        this.arrivalDate = arrivalDate;
    }

    public Date getArrivalDate() {return arrivalDate;}

    public Integer getCost() {
        return cost;
    }

    public String getVendorName(){
        return vendorName;
    }
}
