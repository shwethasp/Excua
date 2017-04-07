package com.varsim.myexcua.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.varsim.myexcua.R;
import com.varsim.myexcua.model.AttendanceModel;

import java.util.ArrayList;

public class AttendanceAdapter extends BaseAdapter {

    private ArrayList<AttendanceModel> mAttendanceDataset;
    private Context mContext;
    LayoutInflater inflater;

    private ArrayList<AttendanceModel> orgData;
    private ArrayList<AttendanceModel> mArrayListModel;
    ContactsFilter mContactsFilter;




    private boolean showCheckBoxes;

    public boolean isShowCheckBoxes() {
        return showCheckBoxes;
    }

    public void setShowCheckBoxes(boolean showCheckBoxes) {
        this.showCheckBoxes = showCheckBoxes;
    }

    public AttendanceAdapter(Context context, ArrayList<AttendanceModel> mAttendanceDataset) {
        this.mContext = context;
        this.mAttendanceDataset = mAttendanceDataset;
       // this.originalData = mAttendanceDataset;

        //this.filteredData = data ;
        //this.originalData = data ;
        inflater = (LayoutInflater.from(context));
    }




    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // Get the data item for this position
        //   AttendanceModel attendanceModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            result = inflater.inflate(R.layout.participant_list_items, parent, false);
            viewHolder.txtName = (TextView) result.findViewById(R.id.participant_name);
            viewHolder.check = (CheckBox) result.findViewById(R.id.checkbox_attendance);
            viewHolder.txtattend = (TextView) result.findViewById(R.id.attended_text);
            viewHolder.layItem = (LinearLayout) result.findViewById(R.id.participant_item_layout);

            result.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        final AttendanceModel attendanceModel = mAttendanceDataset.get(position);
        viewHolder.txtName.setText(attendanceModel.getName());
        if (showCheckBoxes) {
            viewHolder.check.setVisibility(View.VISIBLE);
            viewHolder.check.setOnCheckedChangeListener(null);
            viewHolder.check.setChecked(attendanceModel.isattended());
            viewHolder.txtattend.setVisibility(View.GONE);
        } else {
            viewHolder.check.setVisibility(View.GONE);
            if (attendanceModel.isattended()) {
                viewHolder.txtattend.setVisibility(View.VISIBLE);
            } else {
                viewHolder.txtattend.setVisibility(View.GONE);
            }
        }

        //  viewHolder.check.setText(attendanceModel.getIsChecked());
        // Return the completed view to render on screen
        viewHolder.layItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (showCheckBoxes) {
                    viewHolder.check.setChecked(!attendanceModel.isattended());
                }
            }
        });

        viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                attendanceModel.setIsattended(!attendanceModel.isattended());
            }
        });

        return result;
    }

/*    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<String> list = orgData;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<String>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }*/
    public Filter getFilter() {

        if (mContactsFilter == null)
            mContactsFilter = new ContactsFilter(this, mArrayListModel);

        return mContactsFilter;
    }

    // Filter

    private class ContactsFilter extends Filter {

        private AttendanceAdapter adapter;

        private ArrayList<AttendanceModel> filteredData;

        public ContactsFilter(AttendanceAdapter customAdapter, ArrayList<AttendanceModel> websiteModels) {
            adapter = customAdapter;
            orgData = websiteModels;
            filteredData = new ArrayList<AttendanceModel>();
        }

        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {

            filteredData.clear();
            final FilterResults results = new FilterResults();

            Log.d("performFiltering: ", constraint.toString());

            if (TextUtils.isEmpty(constraint.toString())) {
                filteredData.addAll(orgData);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final AttendanceModel user : orgData) {
                    // set condition for filter here
                    if (user.getName().toLowerCase().contains(filterPattern)) {
                        filteredData.add(user);
                    }
                }
            }

            results.values = filteredData;
            results.count = filteredData.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mArrayListModel = (ArrayList<AttendanceModel>) results.values;
            notifyDataSetChanged();
            //listAdapter.getData().clear();

        }

    }

    @Override
    public int getCount() {
        return mAttendanceDataset.size();
    }

    @Override
    public AttendanceModel getItem(int position) {
        return mAttendanceDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

















    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        CheckBox check;
        TextView txtattend;
        LinearLayout layItem;
    }

}
