package com.varsim.myexcua.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public void getEventsForDate(Date date, final EventsRetrivevalCompletion eventsRetrivevalCompletion) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String dateString = simpleDateFormat.format(date);

        getmEventDB().child(dateString).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Event> eventArrayList = new ArrayList<>();

                Map<String, Object> objectMap = (Map<String, Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> eventData : objectMap.entrySet()) {
                    Event anEvent = new Event(eventData.getKey(), (Map<String, String>) eventData.getValue());
                    eventArrayList.add(anEvent);
                }
                eventsRetrivevalCompletion.successfullyRetrievedEventsForDate(eventArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllEventsForUser(String userID, final EventsRetrivevalCompletion eventsRetrivevalCompletion) {
        getmEventDB().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Map<Date, ArrayList<Event>> dateMap = new HashMap<Date, ArrayList<Event>>();
                Map<String, Object> serverDateMap = (Map<String, Object>) dataSnapshot.getValue();

                for (Map.Entry<String, Object> aDateMap : serverDateMap.entrySet()) {
                    try {
                        Date eventDate = simpleDateFormat.parse(aDateMap.getKey());
                        ArrayList<Event> eventArrayList = new ArrayList<>();
                        Map<String, Object> objectMap = (Map<String, Object>) aDateMap.getValue();
                        for (Map.Entry<String, Object> eventData : objectMap.entrySet()) {
                            Event anEvent = new Event(eventData.getKey(), (Map<String, String>) eventData.getValue());
                            eventArrayList.add(anEvent);
                        }
                        dateMap.put(eventDate, eventArrayList);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                eventsRetrivevalCompletion.successfullyRetrievedEventsForUser(dateMap);
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
        void successfullyRetrievedEventsForDate(ArrayList<Event> events);

        void successfullyRetrievedEventsForUser(Map<Date, ArrayList<Event>> eventsMap);

        void failedToRetrieve(DatabaseError var1);
    }
}
