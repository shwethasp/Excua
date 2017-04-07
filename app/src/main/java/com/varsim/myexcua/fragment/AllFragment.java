package com.varsim.myexcua.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.AllCustomAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    AllCustomAdapter allCustomAdapter;
    public static final int ALL_ACTIVITY = 0;
    private int mDatasetTypes[] = {ALL_ACTIVITY};
    ArrayList<Integer> mSportImage = new ArrayList<>();
    ArrayList<String> mStartTime = new ArrayList<>();
    ArrayList<String> mEndTime = new ArrayList<>();
    int count;


    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.all_recyclerView);
        // mRecyclerView.setHasFixedSize(true);

        allCustomAdapter = new AllCustomAdapter(getContext(), mDatasetTypes, mSportImage, mStartTime, mEndTime, count);
        mRecyclerView.setAdapter(allCustomAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

}
