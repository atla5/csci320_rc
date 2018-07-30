package com.rainforestcommerce.rcdb.models;

import com.rainforestcommerce.rcdb.controllers.StoreProxy;
import com.rainforestcommerce.rcdb.controllers.VendorProxy;

/**
 * Created by aaa10 on 7/15/2018.
 */
public class Shipment {
    private long id;
    private long storeId;
    private long vendorId;

    public Shipment(long ID, long storeId, long vendorId){
        this.id = ID;
        this.storeId = storeId;
        this.vendorId = vendorId;
    }

    public long getID() {
        return id;
    }

    public String getStoreName() {
        return StoreProxy.getStoreNameByStoreId(this.storeId);
    }

    public String getVendorName(){
        return VendorProxy.getVendorNameByVendorId(this.vendorId);
    }
}
