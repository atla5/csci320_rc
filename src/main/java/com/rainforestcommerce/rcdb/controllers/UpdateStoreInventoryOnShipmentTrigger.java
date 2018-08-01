package com.rainforestcommerce.rcdb.controllers;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateStoreInventoryOnShipmentTrigger implements Trigger {

    public static final String update_store_inventory_on_shipment_trigger_sql = "" +
            " CREATE TRIGGER update_store_inventory_on_shipment " +
            "   AFTER INSERT ON shipments " +
            "   FOR EACH ROW " +
            " CALL \"com.rainforestcommerce.rcdb.controllers.UpdateStoreInventoryOnShipmentTrigger\";";

    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type)
        throws SQLException{
        //initialize the trigger object
    }


    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        long store_id, product_id; int quantity;
        if(oldRow != null && oldRow.length == 3){
            store_id = (long) oldRow[0];
            product_id = (long) oldRow[1];
            quantity = (int) oldRow[2];
        }else{
            return;
        }
        PreparedStatement inventoryInsertStatement = conn.prepareStatement(
            "INSERT INTO store_inventory VALUES (?, ?, 0.0, ?)"
        );
        inventoryInsertStatement.setLong(1, store_id);
        inventoryInsertStatement.setLong(2, product_id);
        inventoryInsertStatement.setInt(3, quantity);
        inventoryInsertStatement.execute();
    }

    @Override
    public void close() throws SQLException {
        //nothing
    }

    @Override
    public void remove() throws SQLException {
        //nothing
    }
}
