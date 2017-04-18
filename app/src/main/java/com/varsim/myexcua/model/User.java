package com.varsim.myexcua.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Deepu on 18-04-2017.
 */

@IgnoreExtraProperties
public class User {
    private String name;
    private FirebaseUser firebaseUser;
    private String emailID;

    @Exclude
    private String uID;



}
