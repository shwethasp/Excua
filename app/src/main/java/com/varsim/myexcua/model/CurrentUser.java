package com.varsim.myexcua.model;

/**
 * Created by Deepu on 18-04-2017.
 */

public class CurrentUser extends User{
    private String role;
    private  String phoneNumber;
//    private List<>

    public CurrentUser() {
        super();
    }

    public CurrentUser(String uID) {
        super(uID);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
