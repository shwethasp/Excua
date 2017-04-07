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
import com.varsim.myexcua.adapter.TomorrowCustomAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TomorrowFragment extends Fragment {

    public static final int TomorrowLocation = 0;
    public static final int TomorrowDetails = 1;
    private ProgressDialog authDialog;
    private static final String TAG = TomorrowFragment.class.getCanonicalName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    TomorrowCustomAdapter tomorrowCustomAdapter;
    private int mDatasetTypes[] = {TomorrowLocation, TomorrowDetails};
    private String[] mDataset = new String[3];

    public TomorrowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tomorrow, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.tomorrow_recyclerView);
      // mRecyclerView.setHasFixedSize(true);

        tomorrowCustomAdapter = new TomorrowCustomAdapter(getActivity(), mDatasetTypes, mDataset);
        mRecyclerView.setAdapter(tomorrowCustomAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

}
