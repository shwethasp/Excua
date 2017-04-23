package com.varsim.myexcua.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Deepu on 18-04-2017.
 */

@IgnoreExtraProperties
public class User {
    private String name;
    private String emailID;
    private String uID;

    public User() {
    }

    public User(String uID) {
        this.uID = uID;
    }

    public void saveUser() {
        FireDBManager.getInstance().saveUser(this);
    }

    // Start: name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    // End: name

    // Start: emailID
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getEmailID() {
        return emailID;
    }
    // End: emailID

    // Start: uID
    @Exclude
    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
    // End: uID

}

