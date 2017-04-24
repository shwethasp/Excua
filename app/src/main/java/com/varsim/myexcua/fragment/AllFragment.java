package com.varsim.myexcua.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.AllCustomAdapter;
import com.varsim.myexcua.model.Event;
import com.varsim.myexcua.model.FireDBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    AllCustomAdapter allCustomAdapter;
    public static final int ALL_ACTIVITY = 0;
    private int mDatasetTypes[] = {ALL_ACTIVITY};
    private FirebaseRecyclerAdapter<String, ViewHolder> recyclerAdapter;
    private ArrayList<Date> sortedDateList = new ArrayList<>(0);
    private Map<Date, ArrayList<Event>> allEventsMap;

    public AllFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.all_recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        allCustomAdapter = new AllCustomAdapter(getContext(), mDatasetTypes, sortedDateList, allEventsMap);
        mRecyclerView.setAdapter(allCustomAdapter);

        FireDBManager.getInstance().getAllEventsForUser("", new FireDBManager.EventsRetrivevalCompletion() {
            @Override
            public void successfullyRetrievedEventsForDate(ArrayList<Event> events) {

            }

            @Override
            public void successfullyRetrievedEventsForUser(Map<Date, ArrayList<Event>> eventsMap) {
                AllFragment.this.allEventsMap = eventsMap;
                AllFragment.this.sortedDateList = new ArrayList(eventsMap.keySet());
                Collections.sort(AllFragment.this.sortedDateList);
                allCustomAdapter.setAllEventsMap(allEventsMap);
                allCustomAdapter.setDateArrayList(AllFragment.this.sortedDateList);
                allCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void failedToRetrieve(DatabaseError var1) {

            }
        });
        return view;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
