	package com.rainforestcommerce.rcdb.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Customer {
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    private long accountNumber;
    private String customerName;
    private Date birthDate;
    private boolean isMale;
    private String phone;
    private int points= 100000;
    // points are a test value for now to make sure the view populates the table correctly

    public Customer(List<String> data) throws ParseException{
        this.accountNumber = Long.parseLong(data.get(0));
        this.customerName = data.get(1);
        this.birthDate = sdf.parse(data.get(2));
        this.isMale = "male".equalsIgnoreCase(data.get(3));
        //this.ethnicity = data.get(4);
        this.phone = (data.get(5)).replaceAll("\\D+","");
        //this.purchase_points = Integer.parseInt(data.get(6));
    }

    public Customer(Long accountNumber, String customerName, Date birthDate, boolean isMale, String phone) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.birthDate = birthDate;
        this.isMale = isMale;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birth_date) {
        this.birthDate = birth_date;
    }

    public boolean isIsMale() {
        return isMale;
    }

    public void setIsMale(boolean is_male) {
        this.isMale = is_male;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoints(){
        return this.points;
    }

    public void setPoints(Long points){
        this.points+=points;
    }
}
