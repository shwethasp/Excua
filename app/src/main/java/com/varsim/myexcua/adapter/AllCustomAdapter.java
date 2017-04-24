package com.varsim.myexcua.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.activity.TodayAttendanceActivity;
import com.varsim.myexcua.fragment.AllFragment;
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
    private int[] mDatasetTypes;
    private Map<Date, ArrayList<Event>> allEventsMap;
    private ArrayList<Date> dateArrayList;
    private SimpleDateFormat simpleDateFormat;
//    ArrayList<Integer> mSportImage = new ArrayList<>();
//    ArrayList<String> mStartTime = new ArrayList<>();
//    ArrayList<String> mEndTime = new ArrayList<>();
//    int count;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public int getItemCount() {
        if (dateArrayList.size() == 0){
            return 0;
        };
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatasetTypes[position];
    }

    public class AllViewHolder extends AllCustomAdapter.ViewHolder {
        //  TextView day_text, date_text;
        LinearLayout mAllDateRangeDyanamicLayout, mAllListActivitiesDynamicLayout;


        public AllViewHolder(View v) {
            super(v);

            this.mAllDateRangeDyanamicLayout = (LinearLayout) v.findViewById(R.id.all_daterange_dynamic_layout);
            mAllListActivitiesDynamicLayout = (LinearLayout) v.findViewById(R.id.all_listactivities_dyanamic_layout);

        }
    }

    /*************
     * Constructor
     *****************/
    public AllCustomAdapter(Context applicationContext, int mDatasetTypes[], ArrayList<Date> dateArrayList, Map<Date, ArrayList<Event>> allEventsMap) {

        this.mContext = applicationContext;
        this.mDatasetTypes = mDatasetTypes;
        this.allEventsMap = allEventsMap;
        this.dateArrayList = dateArrayList;
//        inflater = (LayoutInflater.from(applicationContext));
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    }

    @Override
    public AllCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == AllFragment.ALL_ACTIVITY) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.all_dynamic_layouts, parent, false);
            return new AllCustomAdapter.AllViewHolder(v);

        } else {
            return null;
        }
    }

    public void setAllEventsMap(Map<Date, ArrayList<Event>> allEventsMap) {
        this.allEventsMap = allEventsMap;
    }

    public void setDateArrayList(ArrayList<Date> dateArrayList) {
        this.dateArrayList = dateArrayList;
    }

    @Override
    public void onBindViewHolder(AllCustomAdapter.ViewHolder holder, int position) {

        //    if (Connectivity.isConnected(mContext)) {
        if (holder.getItemViewType() == AllFragment.ALL_ACTIVITY) {

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
                viewholder.mAllDateRangeDyanamicLayout.addView(dynamicDateview);

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

                    viewholder.mAllDateRangeDyanamicLayout.addView(dynamicActivityview);
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
}
