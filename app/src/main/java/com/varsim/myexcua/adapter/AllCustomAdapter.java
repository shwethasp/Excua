package com.varsim.myexcua.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.fragment.AllFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by varsi on 18-03-2017.
 */

public class AllCustomAdapter extends RecyclerView.Adapter<AllCustomAdapter.ViewHolder> {

    LayoutInflater inflater;
    private Context mContext;
    private int[] mDatasetTypes;

    ArrayList<Integer> mSportImage = new ArrayList<>();
    ArrayList<String> mStartTime = new ArrayList<>();
    ArrayList<String> mEndTime = new ArrayList<>();
    int count;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public int getItemCount() {
        return mDatasetTypes.length;
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
    public AllCustomAdapter(Context applicationContext, int mDatasetTypes[],
                            ArrayList<Integer> mSportImage, ArrayList<String> mStartTime, ArrayList<String> mEndTime, int count) {

        /********** Take passed values **********/
        this.mContext = applicationContext;
        this.mDatasetTypes = mDatasetTypes;
        this.mSportImage = mSportImage;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.count = count;
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater.from(applicationContext));

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

    @Override
    public void onBindViewHolder(AllCustomAdapter.ViewHolder holder, int position) {

        //    if (Connectivity.isConnected(mContext)) {
        if (holder.getItemViewType() == AllFragment.ALL_ACTIVITY) {

            final AllCustomAdapter.AllViewHolder viewholder = (AllCustomAdapter.AllViewHolder) holder;


            ArrayList<String> dayarray = new ArrayList<String>();
            dayarray.add("Monday");
            dayarray.add("Tuesday");
            dayarray.add("Wednesday");

            ArrayList<String> datearray = new ArrayList<String>();
            datearray.add("04 Mar 2017");
            datearray.add("05 Mar 2017");
            datearray.add("06 Mar 2017");

            HashMap<String, Integer> activityHashmap = new HashMap<>();
            activityHashmap.put(datearray.get(0), 2);
            activityHashmap.put(datearray.get(1), 4);
            activityHashmap.put(datearray.get(2), 3);

            mSportImage.add(R.drawable.ic_menu_camera);
            mSportImage.add(R.drawable.ic_menu_share);
            mSportImage.add(R.drawable.ic_menu_camera);
            mSportImage.add(R.drawable.ic_menu_camera);


            for (int j = 0; j < datearray.size(); j++) {
                LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                View dynamicDateview = mInflater.inflate(R.layout.all_daterange_dynamic, null,
                        false);

                String date = datearray.get(j);
                String day = dayarray.get(j);
                int noOfActivities = activityHashmap.get(date);

                TextView date_text = (TextView) dynamicDateview.findViewById(R.id.date_text);
                TextView day_texts = (TextView) dynamicDateview.findViewById(R.id.day_text);
                day_texts.setText(day);
                date_text.setText(date);

                if (dynamicDateview.getParent() != null)
                    ((ViewGroup) dynamicDateview.getParent()).removeView(dynamicDateview);
                viewholder.mAllDateRangeDyanamicLayout.addView(dynamicDateview);

                for (int k = 0; k < noOfActivities; k++) {
                    View dynamicActivityview = mInflater.inflate(R.layout.all_listactivities_dyanamic, null,
                            false);
                    ImageView sport_type_image = (ImageView) dynamicActivityview.findViewById(R.id.sport_type_image);
                    TextView apartment_name_text = (TextView) dynamicActivityview.findViewById(R.id.apartment_name_text);
                    TextView start_time_textview = (TextView) dynamicActivityview.findViewById(R.id.start_time_textview);
                    TextView end_time_textview = (TextView) dynamicActivityview.findViewById(R.id.end_time_textview);
                    TextView count_textview = (TextView) dynamicActivityview.findViewById(R.id.count_textview);
                    ImageView nextarrow_imageview = (ImageView) dynamicActivityview.findViewById(R.id.nextarrow_imageview);

                    sport_type_image.setImageResource(mSportImage.get(k));
                    apartment_name_text.setText("ITPL");
                    start_time_textview.setText("10:01 AM");
                    end_time_textview.setText("11:01 AM");
                    count_textview.setText("12");
                    nextarrow_imageview.setImageResource(R.drawable.ic_menu_slideshow);


                    if (dynamicActivityview.getParent() != null)
                        ((ViewGroup) dynamicActivityview.getParent()).removeView(dynamicActivityview);
                    viewholder.mAllDateRangeDyanamicLayout.addView(dynamicActivityview);
                }

                //   }

                //  String[] startDatearray = mDataSet[0].split("-");
                //String[] endDatearray = mDataSet[1].split("-");
                // viewholder.date_text.setText(startDatearray[2] + "-" + getMonthName(Integer.parseInt(startDatearray[1])).substring(0, 3).toUpperCase() + "-" + startDatearray[0]);
                //viewholder.end_date.setText(endDatearray[2] + "-" + getMonthName(Integer.parseInt(endDatearray[1])).substring(0, 3).toUpperCase() + "-" + endDatearray[0]);
            }


        }

    }
}
