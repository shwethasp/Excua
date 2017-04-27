package com.varsim.myexcua.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.model.AttendanceModel;

import java.util.ArrayList;

/**
 * Created by varsi on 26-03-2017.
 */

public class CreateEventAttendanceAdapter extends BaseAdapter {

    private ArrayList<AttendanceModel> mScheduleData;
    private Context mContext;
    LayoutInflater inflater;


    public CreateEventAttendanceAdapter(Context context, ArrayList<AttendanceModel> mScheduleData) {
        this.mContext = context;
        this.mScheduleData = mScheduleData;
        inflater = (LayoutInflater.from(context));
        Log.d("VARSIM99", "Size of array list = " + mScheduleData.size());
    }
    @Override
    public int getCount() {
        return mScheduleData.size();
    }

    @Override
    public AttendanceModel getItem(int position) {
        return mScheduleData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CreateEventAttendanceAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {
            viewHolder = new CreateEventAttendanceAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            result = inflater.inflate(R.layout.schedule_participant_listitem, parent, false);
            viewHolder.name = (TextView) result.findViewById(R.id.participant_name);
            viewHolder.cancel = (ImageView) result.findViewById(R.id.cancel);

            result.setTag(viewHolder);
        } else {
            viewHolder = (CreateEventAttendanceAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        final AttendanceModel attendanceModel = mScheduleData.get(position);
        Log.d("VARSIM99", "Adapter name = " + attendanceModel.getName() + " for position " + position);
        viewHolder.name.setText(attendanceModel.getName());

        return result;
    }

    public class ViewHolder{
        TextView name;
        ImageView cancel;
    }
}
