package com.varsim.myexcua.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.AttendanceAdapter;
import com.varsim.myexcua.model.AttendanceModel;

import java.util.ArrayList;
import java.util.Arrays;

public class TodayAttendanceActivity extends AppCompatActivity {
    ArrayList<AttendanceModel> attendanceModelArray = new ArrayList<AttendanceModel>();
    ListView listView;
    private static AttendanceAdapter attendanceAdapter;
    private Button attendanceButton;
    private boolean attendanceButtonSelected;
    //private SearchView mSearchView;

    EditText inputSearch;


//    ArrayAdapter<AttendanceModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle("04, Mar 2017");
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            TextView titleView = (TextView) findViewById(R.id.toolbar_text);
            titleView.setText("04, Mar 2017");
        }

        listView = (ListView) findViewById(R.id.participant_listview);
        attendanceButton = (Button) findViewById(R.id.attendance_done_btn);
        //  mSearchView = (SearchView) findViewById(R.id.searchview);
        inputSearch = (EditText) findViewById(R.id.inputSearch);


        ArrayList<String> mParticipantName = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.participants)));
        // mParticipantName.addAll(Arrays.asList(getResources().getStringArray(R.array.participants)));

        for (int i = 0; i < mParticipantName.size(); i++) {

            AttendanceModel attendanceModel = new AttendanceModel();
            attendanceModel.setName(mParticipantName.get(i));
            attendanceModel.setIsattended(false);

            attendanceModelArray.add(attendanceModel);
        }

        // Create custom adapter object
        attendanceAdapter = new AttendanceAdapter(getApplicationContext(), attendanceModelArray);
        listView.setAdapter(attendanceAdapter);
        listView.setTextFilterEnabled(true);
        listView.setFocusable(false);

        // setupSearchView();

        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceButtonSelected = !attendanceButtonSelected;
                if (attendanceButtonSelected) {
                    attendanceButton.setText("Done");
                } else {
                    attendanceButton.setText("Attendance");
                }

                attendanceAdapter.setShowCheckBoxes(attendanceButtonSelected);
                attendanceAdapter.notifyDataSetChanged();
            }
        });
//searching
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                attendanceAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

/*
    *//*mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    mSearchView.setIconifiedByDefault(false);*//*
    SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String newText) {
            // this is your adapter that will be filtered
            attendanceAdapter.getFilter().filter(newText);
            System.out.println("on text change text: " + newText);
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            // this is your adapter that will be filtered
//                mAdapter.getFilter().filter(query);
            System.out.println("on query submit: " + query);
            return true;
        }
    };
    mSearchView.setOnQueryTextListener(textChangeListener);*/





   /* private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }*/
    }
}