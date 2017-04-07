package com.varsim.myexcua.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.TodayCustomAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {
    public static final int TodayLocation = 0;
    public static final int TodayDetails = 1;
    private ProgressDialog authDialog;
    private static final String TAG = TodayFragment.class.getCanonicalName();
    private RecyclerView mRecyclerView;
   // private Reach_CustomAdapter mReachAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TodayCustomAdapter todayCustomAdapter;
    private int mDatasetTypes[] = {TodayLocation, TodayDetails};
    private String[] mDataset = new String[3];

    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        todayCustomAdapter = new TodayCustomAdapter(getActivity(), mDatasetTypes, mDataset);
        mRecyclerView.setAdapter(todayCustomAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

}
