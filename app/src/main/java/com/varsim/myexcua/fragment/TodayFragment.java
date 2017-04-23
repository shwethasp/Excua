package com.varsim.myexcua.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.TodayCustomAdapter;
import com.varsim.myexcua.library.Library;
import com.varsim.myexcua.model.Event;
import com.varsim.myexcua.model.FireDBManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {
    public static final int TodayLocation = 0;
    public static final int TodayDetails = 1;
    private ProgressDialog authDialog;
    private static final String TAG = TodayFragment.class.getCanonicalName();
    TodayCustomAdapter todayCustomAdapter;
    private int mDatasetTypes[] = {TodayLocation, TodayDetails};
    private ArrayList<Event> eventArrayList = new ArrayList<>();


    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FireDBManager.getInstance().getEventsForDate(new Date(), new FireDBManager.EventsRetrivevalCompletion() {
            @Override
            public void successfullyRetrieved(ArrayList<Event> eventsList) {
                Library.logDebug(""+eventsList);
                for (Event anEvent: eventsList) {
                    Library.logDebug(anEvent.getEventCreaterUID());
                    Library.logDebug(anEvent.getEventType());
                    Library.logDebug(anEvent.getEventUID());
                    Library.logDebug(anEvent.getEventStartDateString());
                }
                eventArrayList = eventsList;
                todayCustomAdapter.setEventArrayList(eventArrayList);
                todayCustomAdapter.populateListView();
                todayCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void failedToRetrieve(DatabaseError var1) {

            }
        });
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);

        todayCustomAdapter = new TodayCustomAdapter(getActivity(), mDatasetTypes, eventArrayList);
        mRecyclerView.setAdapter(todayCustomAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }


}
