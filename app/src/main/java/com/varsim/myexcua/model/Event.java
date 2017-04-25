package com.varsim.myexcua.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Venus and varghese on 4/21/2017.
 */

@IgnoreExtraProperties
public class Event {
    private String eventUID;
    private String eventCreaterUID;
    private String eventType;
    private Date eventStartDate;
    private Date eventEndDate;



    private String eventStartDateString;
    private String eventEndDateString;

    @Exclude
    private FireDBManager fireDBM = FireDBManager.getInstance();
    @Exclude
    private SimpleDateFormat simpleDateFormat;
    public Event() {}

    public Event(String eventCreaterUID) {
        this.eventCreaterUID = eventCreaterUID;
        this.eventUID = fireDBM.getmEventDB().push().getKey();
    }

    public Event(String uniqID, Map<String, String> eventData) {
        this.eventUID = uniqID;
        this.eventCreaterUID = eventData.get("eventCreaterUID");
        this.eventType = eventData.get("eventType");
        this.eventStartDateString = eventData.get("eventStartDateString");
        this.eventEndDateString = eventData.get("eventEndDateString");

        try {
            this.eventStartDate = getSimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(this.eventStartDateString);
            this.eventEndDate = getSimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(this.eventEndDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void createEvent() {
        FireDBManager.getInstance().createEvent(this);
    }

//    Start of locators
    public String getEventUID() {
        return eventUID;
    }

    @Exclude
    public void setEventUID(String eventUID) {
        this.eventUID = eventUID;
    }

    public String getEventCreaterUID() {
        return eventCreaterUID;
    }

    public void setEventCreaterUID(String eventCreaterUID) {
        this.eventCreaterUID = eventCreaterUID;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Exclude
    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        if (eventStartDate == null) {
            this.eventStartDateString = null;
            this.eventStartDate = null;
            return;
        }

        if (!eventStartDate.equals(this.eventStartDate)) {
            eventStartDateString = getSimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(eventStartDate);
        }
        this.eventStartDate = eventStartDate;
    }

    @Exclude
    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        if (eventEndDate == null) {
            eventEndDateString = null;
            this.eventEndDate = null;
            return;
        }

        if (!eventEndDate.equals(this.eventEndDate)) {
            eventEndDateString = getSimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(eventEndDate);
        }
        this.eventEndDate = eventEndDate;
    }

    public String getEventStartDateString() {
        return eventStartDateString;
    }

    public String getEventEndDateString() {
        return eventEndDateString;
    }

    public SimpleDateFormat getSimpleDateFormat(String format) {
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat();
        }
        simpleDateFormat.applyPattern(format);
        return simpleDateFormat;
    }
}
