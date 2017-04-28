package com.varsim.myexcua.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.varsim.myexcua.R;
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
    private final int VIEW_TYPE_DATE = 2;

    ArrayList<Object> listOfAllItemModels;

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

    private class DateViewHolder extends ViewHolder {
        TextView weekdayTetView;
        TextView dateTextView;
        public DateViewHolder(View v) {
            super(v);
            weekdayTetView = (TextView) v.findViewById(R.id.day_text);
            dateTextView = (TextView) v.findViewById(R.id.date_text);
        }
    }

    private class EventListItemViewHolder extends ViewHolder{
        ImageView sportTypeImageView;
        TextView apartmentNameTextView, startTimeTextView, endTimeTextView, countTextView;
        public EventListItemViewHolder(View v) {
            super(v);
            sportTypeImageView = (ImageView) v.findViewById(R.id.sport_type_image);
            apartmentNameTextView = (TextView) v.findViewById(R.id.apartment_name_text);
            startTimeTextView = (TextView) v.findViewById(R.id.start_time_textview);
            endTimeTextView = (TextView) v.findViewById(R.id.end_time_textview);
            countTextView = (TextView) v.findViewById(R.id.count_textview);
        }
    }

    private class AllViewHolder extends AllCustomAdapter.ViewHolder {
        //  TextView day_text, date_text;
        LinearLayout mAllDateRangeDynamicLayout, mAllListActivitiesDynamicLayout;

        public AllViewHolder(View v) {
            super(v);
            this.mAllDateRangeDynamicLayout = (LinearLayout) v.findViewById(R.id.all_daterange_dynamic_layout);
            mAllListActivitiesDynamicLayout = (LinearLayout) v.findViewById(R.id.all_listactivities_dyanamic_layout);
        }
    }

    public AllCustomAdapter(Context applicationContext, ArrayList<Date> dateArrayList, Map<Date, ArrayList<Event>> allEventsMap) {
        this.mContext = applicationContext;
        this.allEventsMap = allEventsMap;
        this.dateArrayList = dateArrayList;
//        inflater = (LayoutInflater.from(applicationContext));
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public int getItemCount() {
        if (isDataLoading) {
            return 1;
        }
        if (dateArrayList == null) {
            return 0;
        }
        if (dateArrayList.size() == 0) {
            return 0;
        }
        if (listOfAllItemModels == null) {
            listOfAllItemModels = new ArrayList<Object>();
        }else {
            listOfAllItemModels.clear();
        }
        int noOfAllEvents = 0;
        for (Date date : dateArrayList) {
            listOfAllItemModels.add(date);
            ArrayList<Event> eventsOnDate = allEventsMap.get(date);
            for(Event anEvent : eventsOnDate) {
                listOfAllItemModels.add(anEvent);
            }
        }
        return listOfAllItemModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isDataLoading) {
            return VIEW_TYPE_LOADING;
        }
        if (listOfAllItemModels.get(position) instanceof Date) {
            return VIEW_TYPE_DATE;
        }
        return VIEW_TYPE_EVENTS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == VIEW_TYPE_EVENTS) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.all_listactivities_dyanamic, parent, false);
            return new EventListItemViewHolder(v);
        } else if (viewType == VIEW_TYPE_LOADING) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(v);
        }else if (viewType == VIEW_TYPE_DATE) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.all_daterange_dynamic, parent, false);
            return new AllCustomAdapter.DateViewHolder(v);
        } else {
            return null;
        }
    }
    @Override
    public void onBindViewHolder(AllCustomAdapter.ViewHolder holder, int position) {

        //    if (Connectivity.isConnected(mContext)) {
        if (holder.getItemViewType() == VIEW_TYPE_DATE) {
            Date date = (Date) listOfAllItemModels.get(position);

            final DateViewHolder dateViewHolder = (DateViewHolder) holder;
            simpleDateFormat.applyPattern("dd MMM,yyyy");
            dateViewHolder.dateTextView.setText(simpleDateFormat.format(date));
            simpleDateFormat.applyPattern("EEEE");
            dateViewHolder.weekdayTetView.setText(simpleDateFormat.format(date));

        } else if (holder.getItemViewType() == VIEW_TYPE_EVENTS) {
            Event event = (Event) listOfAllItemModels.get(position);
            simpleDateFormat.applyPattern("hh:mm a");

            final EventListItemViewHolder eventItemHolder = (EventListItemViewHolder) holder;
            eventItemHolder.sportTypeImageView.setImageResource(R.mipmap.ic_swim);
            eventItemHolder.apartmentNameTextView.setText("ITPL");
            eventItemHolder.startTimeTextView.setText(simpleDateFormat.format(event.getEventStartDate()));
            eventItemHolder.endTimeTextView.setText(simpleDateFormat.format(event.getEventEndDate()));
            eventItemHolder.countTextView.setText("" + 15);
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
