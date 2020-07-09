package com.example.finecloth.customer.model;

import com.example.finecloth.Item.item;
import com.google.firebase.firestore.Exclude;

public class order {


    @Exclude
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    String length;
    String address;
    String phone;
    item i;
    String totalAmount;
    String email;

    order(){

    }


    public order(String length, String address, String phone, item i, String totalAmount,String email) {
        this.length = length;
        this.address = address;
        this.phone = phone;
        this.i = i;
        this.totalAmount = totalAmount;
        this.email=email;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public item getI() {
        return i;
    }

    public void setI(item i) {
        this.i = i;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
