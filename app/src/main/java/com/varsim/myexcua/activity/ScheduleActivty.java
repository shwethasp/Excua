package com.varsim.myexcua.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.ScheduleAdapter;
import com.varsim.myexcua.model.AttendanceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScheduleActivty extends AppCompatActivity implements View.OnClickListener {
    private ScheduleAdapter mScheduleAdapter;
    private ListView mListView;
    ArrayList<AttendanceModel> attendanceModelArray = new ArrayList<AttendanceModel>();
    private Spinner mRepetitionSpinner;
    ImageView mDateSpinner, mStartSpinner, mEndSpinner;
    private static TextView mDateText;
    private static TextView mStartText;
    private static TextView mEndText;
    static final int TIME_DIALOG_ID = 01;
    private static final int TIME_DIALOG_ID1 = 02;
    private int hour;
    private int minute;

    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle("04, Mar 2017");
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            TextView titleView = (TextView) findViewById(R.id.toolbar_text);
            titleView.setText("Add an Event");
        }

        initializeui();
        mDateSpinner.setOnClickListener(this);
        mStartSpinner.setOnClickListener(this);
        mEndSpinner.setOnClickListener(this);

        ArrayList<String> mParticipantName = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.participants)));

        for (int i = 0; i < mParticipantName.size(); i++) {

            AttendanceModel attendanceModel = new AttendanceModel();
            attendanceModel.setName(mParticipantName.get(i));

            attendanceModelArray.add(attendanceModel);
            Log.d("VARSIM99", "Name = " + attendanceModel.getName());
        }

        // Create custom adapter object
        mScheduleAdapter = new ScheduleAdapter(getApplicationContext(), attendanceModelArray);
        mListView.setAdapter(mScheduleAdapter);
        setListViewHeightBasedOnChildren(mListView);
        mListView.setFocusable(false);

        ArrayAdapter<String> repetitionArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.repetition_array));
        repetitionArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRepetitionSpinner.setAdapter(repetitionArray);
        /** Get the current time */
        final Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }


    private void initializeui() {
        mListView = (ListView) findViewById(R.id.schedule_participant_listview);
        mDateSpinner = (ImageView) findViewById(R.id.datespinner);
        mDateText = (TextView) findViewById(R.id.date_text);
        mRepetitionSpinner = (Spinner) findViewById(R.id.repetition_spinner);

        mStartSpinner = (ImageView) findViewById(R.id.startspinner);
        mStartText = (TextView) findViewById(R.id.start_text);
        mEndSpinner = (ImageView) findViewById(R.id.endspinner);
        mEndText =(TextView)findViewById(R.id.end_text);
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
            case R.id.datespinner:
                OpenDatePickerDialog();
                break;

            case R.id.startspinner:
                OpenTimePickerDialog(mStartText);
                break;

            case R.id.endspinner:
                OpenTimePickerDialog(mEndText);
                break;

        }
    }


    private void OpenDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mDateText.setText(day + "/" + (month + 1) + "/" + year);
        }
    }



    private void OpenTimePickerDialog(final TextView textView) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);
        final int mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog with hours,minutes
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                            textView.setText(hourOfDay+":"+minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


}