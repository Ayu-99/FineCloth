package com.example.finecloth.customer.model;

import com.google.firebase.firestore.Exclude;

public class suggestions {

    String sugg;
    String email;
    String userId;

    suggestions(){

    }

    @Exclude
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public suggestions(String sugg, String email, String userId) {
        this.sugg = sugg;
        this.email = email;
        this.userId = userId;
    }

    public String getSugg() {
        return sugg;
    }

    public void setSugg(String sugg) {
        this.sugg = sugg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
