package com.varsim.myexcua.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Deepu on 18-04-2017.
 */

public class FireDBManager {
    private static final FireDBManager ourInstance = new FireDBManager();
    private DatabaseReference mDatabase;
    private DatabaseReference mUsersDB;
    private DatabaseReference mEventsDB;

    public static FireDBManager getInstance() {
        return ourInstance;
    }

    private FireDBManager() {
    }

    public DatabaseReference getmDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return mDatabase;
    }

    public DatabaseReference getmUsersDB() {
        if (mUsersDB == null) {
            mUsersDB = getmDatabase().child("Users");
        }
        return mUsersDB;
    }

    public DatabaseReference getmEventDB() {
        if (mEventsDB == null) {
            mEventsDB = getmDatabase().child("Events");
        }
        return mEventsDB;
    }

    public void saveUser(User user) {
        getmUsersDB().child(user.getuID()).setValue(user);
    }

    public void createEvent(Event event) {
        String dateString = event.getEventStartDateString().split("T")[0];
        getmEventDB().child(dateString).child(event.getEventUID()).setValue(event);
    }


    public void getUser(String userID, final UserRetrievalCompletion userRetrievalCompletion) {
        getmUsersDB().child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userRetrievalCompletion.successfullyRetrieved(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userRetrievalCompletion.failedToRetrieve(databaseError);
            }
        });
    }

    public void getEventsForUser(String userID, final EventsRetrivevalCompletion eventsRetrivevalCompletion) {
        getmEventDB().orderByChild("startDate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //Interface declarations
    public interface UserRetrievalCompletion {
        void successfullyRetrieved(User user);

        void failedToRetrieve(DatabaseError var1);
    }

    public interface EventsRetrivevalCompletion {
        void successfullyRetrieved(List<Event> user);

        void failedToRetrieve(DatabaseError var1);
    }
}
