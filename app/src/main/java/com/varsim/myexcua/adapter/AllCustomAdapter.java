package com.varsim.myexcua.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.activity.TodayAttendanceActivity;
import com.varsim.myexcua.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by varsi on 18-03-2017.
 */

public class AllCustomAdapter extends RecyclerView.Adapter<AllCustomAdapter.ViewHolder> {

    //    private LayoutInflater inflater;
    private Context mContext;
    private Map<Date, ArrayList<Event>> allEventsMap;
    private ArrayList<Date> dateArrayList;
    private SimpleDateFormat simpleDateFormat;
    private boolean isDataLoading = true;

    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_EVENTS = 1;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    private class LoadingViewHolder extends ViewHolder {
        private ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    public class AllViewHolder extends AllCustomAdapter.ViewHolder {
        //  TextView day_text, date_text;
        LinearLayout mAllDateRangeDynamicLayout, mAllListActivitiesDynamicLayout;

        public AllViewHolder(View v) {
            super(v);

            this.mAllDateRangeDynamicLayout = (LinearLayout) v.findViewById(R.id.all_daterange_dynamic_layout);
            mAllListActivitiesDynamicLayout = (LinearLayout) v.findViewById(R.id.all_listactivities_dyanamic_layout);

        }
    }

    /*************
     * Constructor
     *****************/
    public AllCustomAdapter(Context applicationContext, ArrayList<Date> dateArrayList, Map<Date, ArrayList<Event>> allEventsMap) {
        this.mContext = applicationContext;
        this.allEventsMap = allEventsMap;
        this.dateArrayList = dateArrayList;
//        inflater = (LayoutInflater.from(applicationContext));
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public int getItemCount() {
        if (dateArrayList.size() == 0 && !isDataLoading){
            return 0;
        };
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return isDataLoading ? VIEW_TYPE_LOADING : VIEW_TYPE_EVENTS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == VIEW_TYPE_EVENTS) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.all_dynamic_layouts, parent, false);
            return new AllCustomAdapter.AllViewHolder(v);
        } else if (viewType == VIEW_TYPE_LOADING) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent,  false);
            return new LoadingViewHolder(v);
        } else {
            return null;
        }
    }
    @Override
    public void onBindViewHolder(AllCustomAdapter.ViewHolder holder, int position) {

        //    if (Connectivity.isConnected(mContext)) {
        if (holder.getItemViewType() == VIEW_TYPE_EVENTS) {
            final AllCustomAdapter.AllViewHolder viewholder = (AllCustomAdapter.AllViewHolder) holder;

            for (int j = 0; j < dateArrayList.size(); j++) {
                LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                View dynamicDateview = mInflater.inflate(R.layout.all_daterange_dynamic, null,
                        false);
                simpleDateFormat.applyPattern("dd MMM,yyyy");
                String dateString = simpleDateFormat.format(dateArrayList.get(j));
                simpleDateFormat.applyPattern("EEEE");
                String weekday = simpleDateFormat.format(dateArrayList.get(j));

                int noOfActivities = 15;

                TextView date_text = (TextView) dynamicDateview.findViewById(R.id.date_text);
                TextView day_texts = (TextView) dynamicDateview.findViewById(R.id.day_text);
                day_texts.setText(weekday);
                date_text.setText(dateString);

                if (dynamicDateview.getParent() != null)
                    ((ViewGroup) dynamicDateview.getParent()).removeView(dynamicDateview);
                viewholder.mAllDateRangeDynamicLayout.addView(dynamicDateview);

                ArrayList<Event> eventsList = allEventsMap.get(dateArrayList.get(j));
                for (int k = 0; k < eventsList.size(); k++) {
                    final View dynamicActivityview = mInflater.inflate(R.layout.all_listactivities_dyanamic, null,
                            false);
                    ImageView sport_type_image = (ImageView) dynamicActivityview.findViewById(R.id.sport_type_image);
                    TextView apartment_name_text = (TextView) dynamicActivityview.findViewById(R.id.apartment_name_text);
                    TextView start_time_textview = (TextView) dynamicActivityview.findViewById(R.id.start_time_textview);
                    TextView end_time_textview = (TextView) dynamicActivityview.findViewById(R.id.end_time_textview);
                    TextView count_textview = (TextView) dynamicActivityview.findViewById(R.id.count_textview);
                    TextView nextarrow_imageview = (TextView) dynamicActivityview.findViewById(R.id.nextarrow_imageview);

                    Event event = eventsList.get(k);
                    simpleDateFormat.applyPattern("hh:mm a");

                    sport_type_image.setImageResource(R.mipmap.ic_swim);
                    apartment_name_text.setText("ITPL");
                    start_time_textview.setText(simpleDateFormat.format(event.getEventStartDate()));
                    end_time_textview.setText(simpleDateFormat.format(event.getEventEndDate()));
                    count_textview.setText("" + noOfActivities);
                    nextarrow_imageview.setText(">");

                    if (dynamicActivityview.getParent() != null)
                        ((ViewGroup) dynamicActivityview.getParent()).removeView(dynamicActivityview);

                    viewholder.mAllDateRangeDynamicLayout.addView(dynamicActivityview);
                    dynamicActivityview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(mContext, TodayAttendanceActivity.class);
                            mContext.startActivity(i);
                        }
                    });
                }
            }
        }
    }

    public void setAllEventsMap(Map<Date, ArrayList<Event>> allEventsMap) {
        this.allEventsMap = allEventsMap;
    }

    public void setDateArrayList(ArrayList<Date> dateArrayList) {
        this.dateArrayList = dateArrayList;
    }

     public void setDataLoading(boolean dataLoading) {
        isDataLoading = dataLoading;
        this.notifyItemChanged(0);
    }
}
