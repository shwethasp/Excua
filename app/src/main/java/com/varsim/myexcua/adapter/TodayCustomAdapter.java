package com.varsim.myexcua.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.fragment.TodayFragment;
import com.varsim.myexcua.model.Event;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by varsi on 18-03-2017.
 */

public class TodayCustomAdapter extends RecyclerView.Adapter<TodayCustomAdapter.ViewHolder> {

    private Context mContext;
    DetailListCustomAdapter detailListCustomAdapter;

    private ListView mListView;
    private CardView mToday_details_cardview;

    ArrayList<Integer> sporticon = new ArrayList<Integer>();
    ArrayList<String> startTime = new ArrayList<String>();
    ArrayList<String> endTime = new ArrayList<String>();
    ArrayList<Integer> locationNumberIcon = new ArrayList<>();

    int[] mDatasetTypes;
    private ArrayList<Event> eventArrayList;

    // private List<LoyaltyOutlet> mapdata;
    private Context context;
    FragmentManager fragmentManager;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class TodayLocationsViewHolder extends ViewHolder {
        TextView day_text, date_text;
        ProgressBar mProgressBar;


        public TodayLocationsViewHolder(View v) {
            super(v);

            this.date_text = (TextView) v.findViewById(R.id.date_text);
            this.day_text = (TextView) v.findViewById(R.id.day_text);
            //mMapView = (MapView) v.findViewById(R.id.today_mapView);


            //  this.pieChartValues = new int[3];
            //   this.mProgressBar = (ProgressBar) v.findViewById(R.id.reach_progressbar);
            // mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public class TodayDetailsViewHolder extends ViewHolder {


        public TodayDetailsViewHolder(View v) {
            super(v);
            mListView = (ListView) v.findViewById(R.id.details_listview);
            mToday_details_cardview = (CardView)v.findViewById(R.id.today_details_cardview);
        }
    }

    //constructor
    public TodayCustomAdapter(Context mContexts, int mDatasetTypes[], ArrayList<Event> eventArrayList/*,List<LoyaltyOutlet> mapdata,FragmentManager fm*/) {
        this.mContext = mContexts;
        this.mDatasetTypes = mDatasetTypes;
        this.eventArrayList = eventArrayList;
        //this.mapdata = mapdata;
        //  this.fragmentManager = fm;

    }

    @Override
    public TodayCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == TodayFragment.TodayLocation) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.today_location_card, parent, false);
            return new TodayLocationsViewHolder(v);

        } else if (viewType == TodayFragment.TodayDetails) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.today_details_card, parent, false);
            return new TodayDetailsViewHolder(v);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(TodayCustomAdapter.ViewHolder holder, int position) {

        //    if (Connectivity.isConnected(mContext)) {
        if (holder.getItemViewType() == TodayFragment.TodayLocation) {

            final TodayLocationsViewHolder viewholder = (TodayLocationsViewHolder) holder;

            //  String[] startDatearray = mDataSet[0].split("-");
            //String[] endDatearray = mDataSet[1].split("-");
            // viewholder.date_text.setText(startDatearray[2] + "-" + getMonthName(Integer.parseInt(startDatearray[1])).substring(0, 3).toUpperCase() + "-" + startDatearray[0]);
            //viewholder.end_date.setText(endDatearray[2] + "-" + getMonthName(Integer.parseInt(endDatearray[1])).substring(0, 3).toUpperCase() + "-" + endDatearray[0]);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM,yyyy");
            String dateString = simpleDateFormat.format(new Date());
            viewholder.date_text.setText(dateString);
            simpleDateFormat.applyPattern("EEEE");
            String weekday = simpleDateFormat.format(new Date());
            viewholder.day_text.setText(weekday);


            //  mMapView.onCreate(savedInstanceState);

            // mMapView.onResume();// needed to get the map to display immediately
/*

               try {
                   MapsInitializer.initialize(mContext.getApplicationContext());
               } catch (Exception e) {
                   e.printStackTrace();
               }

               googleMap = mMapView.getMap();
               // latitude and longitude
               double latitude = 17.385044;
               double longitude = 78.486671;

               // create marker
               MarkerOptions marker = new MarkerOptions().position(
                       new LatLng(latitude, longitude)).title("Hello Maps");

               // Changing marker icon
               marker.icon(BitmapDescriptorFactory
                       .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

               // adding marker
               googleMap.addMarker(marker);
               CameraPosition cameraPosition = new CameraPosition.Builder()
                       .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
               googleMap.animateCamera(CameraUpdateFactory
                       .newCameraPosition(cameraPosition));

*/

        } else if (holder.getItemViewType() == TodayFragment.TodayDetails) {
            TodayDetailsViewHolder viewholder = (TodayDetailsViewHolder) holder;


            sporticon.add(R.mipmap.ic_swim);
            sporticon.add(R.mipmap.ic_swim);
            sporticon.add(R.mipmap.ic_swim);
            sporticon.add(R.mipmap.ic_swim);

            startTime.add("10:00 AM");
            startTime.add("11:00 AM");
            startTime.add("12:15 PM");
            startTime.add("12:15 PM");


            endTime.add("10:50 AM");
            endTime.add("12:10 PM");
            endTime.add("2:15 PM");
            endTime.add("2:15 PM");

            locationNumberIcon.add(R.mipmap.ic_location_pin);
            locationNumberIcon.add(R.mipmap.ic_location_pin);
            locationNumberIcon.add(R.mipmap.ic_location_pin);
            locationNumberIcon.add(R.mipmap.ic_location_pin);



        }
        // } else {
        //    Snackbar.make(holder.itemView, "Lost Internet connection", Snackbar.LENGTH_LONG).show();
        //}
    }

    public void populateListView() {

        // Create custom adapter object ( see below CustomAdapter.java )
        detailListCustomAdapter = new DetailListCustomAdapter(mContext, eventArrayList);
        mListView.setAdapter(detailListCustomAdapter);
        mListView.setFocusable(false);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //mListener.onItemSelected(mSettingsListArray[i], i);
            }
        });
    }


    String getMonthName(int monthNumber) {
        String[] months = new DateFormatSymbols().getMonths();
        int n = monthNumber - 1;
        return (n >= 0 && n <= 11) ? months[n] : "wrong number";
    }

    @Override
    public int getItemCount() {
        return mDatasetTypes.length;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatasetTypes[position];
    }

    public void setEventArrayList(ArrayList<Event> eventArrayList) {
        this.eventArrayList = eventArrayList;
    }
}
