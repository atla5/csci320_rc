package com.rainforestcommerce.rcdb.controllers;

import com.rainforestcommerce.rcdb.models.Customer;

import java.util.ArrayList;

import java.sql.*;

import org.h2.jdbcx.JdbcConnectionPool;

public class CustomerProxy {

    public static ArrayList<Customer> getCustomers() {
        String statement = "";
        Connection conn = ConnectionProxy.cp.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
        //Write Database Access code here
        return new ArrayList<Customer>();
    }
}
