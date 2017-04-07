package com.varsim.myexcua.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.fragment.TomorrowFragment;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

/**
 * Created by varsi on 19-03-2017.
 */

public class TomorrowCustomAdapter extends RecyclerView.Adapter<TomorrowCustomAdapter.ViewHolder> {

    private Context mContext;
    DetailListTomorrowCustomAdapter detailListTomorrowCustomAdapter;

    private ListView mListView;

    ArrayList<Integer> sporticon = new ArrayList<Integer>();
    ArrayList<String> startTime = new ArrayList<String>();
    ArrayList<String> endTime = new ArrayList<String>();
    ArrayList<Integer> locationNumberIcon = new ArrayList<>();

    int[] mDatasetType;
    private String[] mDateset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class TomorrowLocationsViewHolder extends TomorrowCustomAdapter.ViewHolder {
        TextView day_text, date_text;
        ProgressBar mProgressBar;


        public TomorrowLocationsViewHolder(View v) {
            super(v);

            this.date_text = (TextView) v.findViewById(R.id.date_text);
            this.day_text = (TextView) v.findViewById(R.id.day_text);

            //  this.pieChartValues = new int[3];
            //   this.mProgressBar = (ProgressBar) v.findViewById(R.id.reach_progressbar);
            // mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public class TomorrowDetailsViewHolder extends TomorrowCustomAdapter.ViewHolder {


        public TomorrowDetailsViewHolder(View v) {
            super(v);
            mListView = (ListView) v.findViewById(R.id.tomorrow_details_listview);
        }
    }

    //constructor
    public TomorrowCustomAdapter(Context mContexts, int mDatasetType[], String[] mDateset) {
        this.mContext = mContexts;
        this.mDatasetType = mDatasetType;
        this.mDateset = mDateset;

    }

    @Override
    public TomorrowCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == TomorrowFragment.TomorrowLocation) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tomorrow_location_card, parent, false);
            return new TomorrowCustomAdapter.TomorrowLocationsViewHolder(v);

        } else if (viewType == TomorrowFragment.TomorrowDetails) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tomorrow_details_card, parent, false);
            return new TomorrowCustomAdapter.TomorrowDetailsViewHolder(v);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(TomorrowCustomAdapter.ViewHolder holder, int position) {

        //    if (Connectivity.isConnected(mContext)) {
        if (holder.getItemViewType() == TomorrowFragment.TomorrowLocation) {

            final TomorrowCustomAdapter.TomorrowLocationsViewHolder viewholder = (TomorrowCustomAdapter.TomorrowLocationsViewHolder) holder;

            //  String[] startDatearray = mDataSet[0].split("-");
            //String[] endDatearray = mDataSet[1].split("-");
            // viewholder.date_text.setText(startDatearray[2] + "-" + getMonthName(Integer.parseInt(startDatearray[1])).substring(0, 3).toUpperCase() + "-" + startDatearray[0]);
            //viewholder.end_date.setText(endDatearray[2] + "-" + getMonthName(Integer.parseInt(endDatearray[1])).substring(0, 3).toUpperCase() + "-" + endDatearray[0]);
            viewholder.date_text.setText("15 March,2017");
            viewholder.day_text.setText("Wednesday");

        } else if (holder.getItemViewType() == TomorrowFragment.TomorrowDetails) {
            TomorrowCustomAdapter.TomorrowDetailsViewHolder viewholder = (TomorrowCustomAdapter.TomorrowDetailsViewHolder) holder;

            sporticon.add(R.drawable.ic_menu_gallery);
            sporticon.add(R.drawable.ic_menu_camera);
            sporticon.add(R.drawable.ic_menu_manage);
            sporticon.add(R.drawable.ic_menu_manage);

            startTime.add("10:00 AM");
            startTime.add("11:00 AM");
            startTime.add("12:15 PM");
            startTime.add("12:15 PM");


            endTime.add("10:50 AM");
            endTime.add("12:10 PM");
            endTime.add("2:15 PM");
            endTime.add("2:15 PM");

            locationNumberIcon.add(R.drawable.ic_menu_gallery);
            locationNumberIcon.add(R.drawable.ic_menu_slideshow);
            locationNumberIcon.add(R.drawable.ic_menu_share);
            locationNumberIcon.add(R.drawable.ic_menu_share);


            // Create custom adapter object ( see below CustomAdapter.java )
            detailListTomorrowCustomAdapter = new DetailListTomorrowCustomAdapter(mContext, sporticon, startTime, endTime, locationNumberIcon);
            mListView.setAdapter(detailListTomorrowCustomAdapter);
            mListView.setFocusable(false);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //mListener.onItemSelected(mSettingsListArray[i], i);
                }
            });
        }
        // } else {
        //    Snackbar.make(holder.itemView, "Lost Internet connection", Snackbar.LENGTH_LONG).show();
        //}
    }


    String getMonthName(int monthNumber) {
        String[] months = new DateFormatSymbols().getMonths();
        int n = monthNumber - 1;
        return (n >= 0 && n <= 11) ? months[n] : "wrong number";
    }

    @Override
    public int getItemCount() {
        return mDatasetType.length;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatasetType[position];
    }
}


