package com.varsim.myexcua.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.CreateEventAttendanceAdapter;
import com.varsim.myexcua.library.Library;
import com.varsim.myexcua.model.AttendanceModel;
import com.varsim.myexcua.model.Event;
import com.varsim.myexcua.model.FireDBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
  private ImageView eventTypeImageView;
  private TextView eventTypeTextView;
  private TextView eventDateTextView;
  private TextView eventStartTimeTextView;
  private TextView eventEndTimeTextView;
  private Spinner repetitionSpinner;

  private TextView participantCountTextView;
  private ImageButton imgBtnAddParticipants;

  private ListView mListView;

  private Button saveButton;

  //  Event newEvent = new Event(FirebaseAuth.getInstance().getCurrentUser().getUid());
  private String eventType;
  private Date _eventDate;
  private Date _eventStartTime;
  private Date _eventEndTime;
  private String eventRepetitionType;

  private SimpleDateFormat simpleDateFormat;
  ProgressDialog mProgressDialog;

  ArrayList<AttendanceModel> attendanceModelArray = new ArrayList<AttendanceModel>();

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.create_event_activty);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      TextView titleView = (TextView) findViewById(R.id.toolbar_text);
      titleView.setText("Add an Event");
    }

    initializeUI();
    saveButton.setOnClickListener(this);
    eventDateTextView.setOnClickListener(this);
    eventStartTimeTextView.setOnClickListener(this);
    eventEndTimeTextView.setOnClickListener(this);
    Calendar nowCalender = Calendar.getInstance();
    setEventDate(nowCalender.getTime());
    setEventStartTime(nowCalender.getTime());
    nowCalender.add(Calendar.MINUTE, 30);
    setEventEndTime(nowCalender.getTime());
    eventType = "Swimming";


    ArrayAdapter<String> repetitionArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.repetition_array));
    repetitionArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    repetitionSpinner.setAdapter(repetitionArray);
    repetitionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.dark_green));
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    ArrayList<String> mParticipantName = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.participants)));

    for (int i = 0; i < mParticipantName.size(); i++) {
      AttendanceModel attendanceModel = new AttendanceModel();
      attendanceModel.setName(mParticipantName.get(i));
      attendanceModelArray.add(attendanceModel);
    }
    // Create custom adapter object
    CreateEventAttendanceAdapter mCreateEventAttendanceAdapter = new CreateEventAttendanceAdapter(getApplicationContext(), attendanceModelArray);
    mListView.setAdapter(mCreateEventAttendanceAdapter);
    setListViewHeightBasedOnChildren(mListView);
    mListView.setFocusable(false);
    mListView.setTextFilterEnabled(true);
  }


  private void initializeUI() {
    eventDateTextView = (TextView) findViewById(R.id.date_text);
    eventStartTimeTextView = (TextView) findViewById(R.id.start_text);
    eventEndTimeTextView = (TextView) findViewById(R.id.end_text);
    mListView = (ListView) findViewById(R.id.schedule_participant_listview);
    repetitionSpinner = (Spinner) findViewById(R.id.repetition_spinner);
    saveButton = (Button) findViewById(R.id.btn_save);
  }

  /****
   * Method for Setting the Height of the ListView dynamically.
   * *** Hack to fix the issue of not showing all the items of the ListView
   * *** when placed inside a ScrollView
   ****/
  public static void setListViewHeightBasedOnChildren(ListView listView) {
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null)
      return;

    int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
    int totalHeight = 0;
    View view = null;
    for (int i = 0; i < listAdapter.getCount(); i++) {
      view = listAdapter.getView(i, view, listView);
      if (i == 0)
        view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
      view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
      totalHeight += view.getMeasuredHeight();
    }
    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    listView.setLayoutParams(params);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.date_text:
        openDatePickerDialog();
        break;

      case R.id.start_text:
        openTimePickerDialog(eventStartTimeTextView);
        break;

      case R.id.end_text:
        openTimePickerDialog(eventEndTimeTextView);
        break;

      case R.id.btn_save:
        saveButtonAction();
        break;
    }
  }

  private void saveButtonAction() {
    if (validateField()) {
      Event newEvent = new Event(FirebaseAuth.getInstance().getCurrentUser().getUid());
      newEvent.setEventStartDate(Library.mergeDates(getEventDate(), getEventStartTime()));
      newEvent.setEventEndDate(Library.mergeDates(getEventDate(), getEventEndTime()));
      newEvent.setEventType(eventType);
      if (mProgressDialog == null) {
        mProgressDialog = new ProgressDialog(this);
      }
      mProgressDialog.setMessage("Saving event...");
      mProgressDialog.show();

      newEvent.createEvent(new FireDBManager.EventCreationCompletion() {
        @Override
        public void successfullyCreateEvent(Event event) {
          mProgressDialog.hide();
          Intent homeIntent = new Intent(CreateEventActivity.this, EventsActivity.class);
          homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(homeIntent);
        }

        @Override
        public void failedToCreateEvent() {
          mProgressDialog.hide();
          Library.showToastLong(CreateEventActivity.this, "Failed to save event, please try again");
        }
      });
    }
  }

  private boolean validateField() {
    return true;
  }

  private void openDatePickerDialog() {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
    datePickerDialog.show();
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    Calendar selectedC = Calendar.getInstance();
    selectedC.set(Calendar.YEAR, year);
    selectedC.set(Calendar.MONTH, month);
    selectedC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    setEventDate(selectedC.getTime());
  }

  private void openTimePickerDialog(final TextView textView) {
    // Get Current Time
    final Calendar c = Calendar.getInstance();
    final int mHour = c.get(Calendar.HOUR_OF_DAY);
    final int mMinute = c.get(Calendar.MINUTE);

    // Launch Time Picker Dialog with hours,minutes
    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
            new TimePickerDialog.OnTimeSetListener() {
              @Override
              public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar selectedC = Calendar.getInstance();
                selectedC.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedC.set(Calendar.MINUTE, minute);

                Date selectedDate = selectedC.getTime();
                if (textView.getId() == R.id.start_text) {
                  setEventStartTime(selectedDate);
                } else {
                  setEventEndTime(selectedDate);
                }
                textView.setText(getSimpleDateFormat("hh:mm a").format(selectedDate));
              }
            }, mHour, mMinute, false);
    timePickerDialog.show();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      case android.R.id.home:

        Intent homeIntent = new Intent(this, EventsActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    return (super.onOptionsItemSelected(menuItem));
  }

  public SimpleDateFormat getSimpleDateFormat(String format) {
    if (simpleDateFormat == null) {
      simpleDateFormat = new SimpleDateFormat();
    }
    simpleDateFormat.applyPattern(format);
    return simpleDateFormat;
  }

  public void setEventDateTextViewText(Date date) {
    this.eventDateTextView.setText(getSimpleDateFormat("EEE, MMM dd").format(date));
  }

  public void setEventStartTimeTextViewText(Date date) {
    this.eventStartTimeTextView.setText(getSimpleDateFormat("hh:mm a").format(date));

  }

  public void setEventEndTimeTextViewText(Date date) {
    this.eventEndTimeTextView.setText(getSimpleDateFormat("hh:mm a").format(date));
  }

  public void setEventDate(Date eventDate) {
    this._eventDate = eventDate;
    setEventDateTextViewText(this._eventDate);
  }

  public void setEventStartTime(Date eventStartDate) {
    this._eventStartTime = eventStartDate;
    setEventStartTimeTextViewText(this._eventStartTime);
  }

  public void setEventEndTime(Date eventEndDate) {
    this._eventEndTime = eventEndDate;
    setEventEndTimeTextViewText(this._eventEndTime);
  }

  public Date getEventDate() {
    return _eventDate;
  }

  public Date getEventStartTime() {
    return _eventStartTime;
  }

  public Date getEventEndTime() {
    return _eventEndTime;
  }
}