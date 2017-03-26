package com.varsim.myexcua.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.ScheduleAdapter;
import com.varsim.myexcua.model.AttendanceModel;

import java.util.ArrayList;
import java.util.Arrays;

public class ScheduleActivty extends AppCompatActivity {
    private ScheduleAdapter mScheduleAdapter;
    private ListView mListView;
    ArrayList<AttendanceModel> attendanceModelArray = new ArrayList<AttendanceModel>();

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

    }

    private void initializeui() {
        mListView = (ListView) findViewById(R.id.schedule_participant_listview);
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
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
}
