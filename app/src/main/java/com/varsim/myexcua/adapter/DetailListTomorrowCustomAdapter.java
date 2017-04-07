package com.varsim.myexcua.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.activity.TodayAttendanceActivity;

import java.util.ArrayList;

/**
 * Created by varsi on 19-03-2017.
 */

public class DetailListTomorrowCustomAdapter extends BaseAdapter {

    private ArrayList<Integer> mSportImage;
    private ArrayList<String> mStartTime;
    private ArrayList<String> mEndTime;
    private ArrayList<Integer> mLocationNumberImage;

    LayoutInflater inflater;
    Context context;

    /*************
     * Constructor
     *****************/
    public DetailListTomorrowCustomAdapter(Context applicationContext,
                                           ArrayList<Integer> mSportImage, ArrayList<String> mStartTime, ArrayList<String> mEndTime, ArrayList<Integer> mLocationNumberImage) {

        /********** Take passed values **********/
        this.context = applicationContext;
        this.mSportImage = mSportImage;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mLocationNumberImage = mLocationNumberImage;
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater.from(applicationContext));

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.list_item, parent, false);

        ImageView curent_time_bar = (ImageView) row.findViewById(R.id.current_time_bar);
        ImageView sport_image = (ImageView) row.findViewById(R.id.sportimage);
        TextView start_time_text = (TextView) row.findViewById(R.id.start_time_text);
        TextView end_time_text = (TextView) row.findViewById(R.id.end_time_text);
        TextView count_text = (TextView) row.findViewById(R.id.count_text);
        ImageView location_count_image = (ImageView) row.findViewById(R.id.location_count_image);
        ImageView nextarrow_image = (ImageView) row.findViewById(R.id.nextarrow_image);
      /*
        if (WebSitePage.website_clickedposition == position) {
            website_name.setTextColor(context.getResources().getColor(R.color.white));
            website_typename.setTextColor(context.getResources().getColor(R.color.white));
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.background_color));
        } else {
            *//*website_name.setTextColor(context.getResources().getColor(R.color.black));
            website_typename.setTextColor(context.getResources().getColor(R.color.black));
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));*//*
        }
*/

        return row;
    }


    @Override
    public int getCount() {
        return mStartTime.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.list_item_tomorrow, parent, false);

        /***** Get each Model object from Arraylist ********/
        LinearLayout list_item_tomo_layout = (LinearLayout) row.findViewById(R.id.list_item_layout);
        ImageView curent_time_bar = (ImageView) row.findViewById(R.id.current_time_bar);
        ImageView sport_image = (ImageView) row.findViewById(R.id.sportimage);
        TextView start_time_text = (TextView) row.findViewById(R.id.start_time_text);
        TextView end_time_text = (TextView) row.findViewById(R.id.end_time_text);
        TextView count_text = (TextView) row.findViewById(R.id.count_text);
        ImageView location_count_image = (ImageView) row.findViewById(R.id.location_count_image);
        ImageView nextarrow_image = (ImageView) row.findViewById(R.id.nextarrow_image);

        if (position == 0)
            curent_time_bar.setImageResource(R.color.dark_green);
        else
            curent_time_bar.setImageResource(R.color.white);
        sport_image.setImageResource(R.drawable.ic_menu_camera);
        start_time_text.setText("10:00 AM");
        end_time_text.setText("11:23 AM");
        count_text.setText("15");
        location_count_image.setImageResource(R.drawable.ic_menu_slideshow);
        nextarrow_image.setImageResource(R.drawable.ic_menu_manage);

        list_item_tomo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TodayAttendanceActivity.class);
                context.startActivity(i);
            }
        });
        return row;
    }
}
