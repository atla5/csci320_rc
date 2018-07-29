package com.rainforestcommerce.rcdb.models;

import java.text.ParseException;
import java.util.List;

public class Customer {

    private long accountNumber;
    private String customerName;
    private String phone;
    private long points = 0;

    public Customer(List<String> data) throws ParseException{
        this.accountNumber = Long.parseLong(data.get(0));
        this.customerName = data.get(1);
        this.phone = (data.get(5)).replaceAll("\\D+","");
    }

    public Customer(long accountNumber, String customerName, String phone){
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.phone = phone;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getCustName() {
        return customerName;
    }

    public void setCustName(String cust_name) {
        this.customerName = cust_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getPoints(){
        return this.points;
    }

    public void setPoints(Long points){
        this.points = points;
    }
}
