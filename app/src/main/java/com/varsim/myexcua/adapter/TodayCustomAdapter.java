package com.varsim.myexcua.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.varsim.myexcua.R;
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
  private ListView mListView;
  private ArrayList<Event> eventArrayList;
  private Date dateToSet;

  private boolean isDataLoading = true;

  private final int VIEW_TYPE_LOCATION = 0;
  private final int VIEW_TYPE_LOADING = 1;
  private final int VIEW_TYPE_EVENTS = 2;


  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View v) {
      super(v);
    }
  }

  private class TodayLocationsViewHolder extends ViewHolder {
    TextView day_text, date_text;
    ProgressBar mProgressBar;

    public TodayLocationsViewHolder(View v) {
      super(v);

      this.date_text = (TextView) v.findViewById(R.id.date_text);
      this.day_text = (TextView) v.findViewById(R.id.day_text);
    }
  }

  private class TodayDetailsViewHolder extends ViewHolder {
    public TodayDetailsViewHolder(View v) {
      super(v);
      mListView = (ListView) v.findViewById(R.id.details_listview);
      DetailListCustomAdapter detailListCustomAdapter = new DetailListCustomAdapter(mContext, eventArrayList);
      mListView.setAdapter(detailListCustomAdapter);
      mListView.setFocusable(false);

      mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          //mListener.onItemSelected(mSettingsListArray[i], i);
        }
      });
      TodayCustomAdapter.this.setListViewHeightBasedOnChildren(mListView);
    }
  }

  private class LoadingViewHolder extends ViewHolder {
    public ProgressBar progressBar;
    public LoadingViewHolder(View itemView) {
      super(itemView);
      progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
    }
  }

  //constructor
  public TodayCustomAdapter(Context mContexts, ArrayList<Event> eventArrayList, Date dateToSet) {
    this.mContext = mContexts;
    this.eventArrayList = eventArrayList;
    this.dateToSet = dateToSet;
  }

  @Override
  public int getItemCount() {
    return 2;
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return VIEW_TYPE_LOCATION;
    }
    return isDataLoading ? VIEW_TYPE_LOADING : VIEW_TYPE_EVENTS;
  }

  @Override
  public TodayCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v;

    if (viewType == VIEW_TYPE_LOCATION) {
      v = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.today_location_card, parent, false);
      return new TodayLocationsViewHolder(v);
    } else if (viewType == VIEW_TYPE_EVENTS) {
      v = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.today_details_card, parent, false);
      return new TodayDetailsViewHolder(v);
    } else if (viewType == VIEW_TYPE_LOADING) {
      v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent,  false);
      return new LoadingViewHolder(v);
    } else {
      return null;
    }
  }

  @Override
  public void onBindViewHolder(TodayCustomAdapter.ViewHolder holder, int position) {

    //    if (Connectivity.isConnected(mContext)) {
    if (holder.getItemViewType() == VIEW_TYPE_LOCATION) {

      final TodayLocationsViewHolder viewholder = (TodayLocationsViewHolder) holder;
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM,yyyy");
      String dateString = simpleDateFormat.format(this.dateToSet);
      viewholder.date_text.setText(dateString);
      simpleDateFormat.applyPattern("EEEE");
      String weekday = simpleDateFormat.format(this.dateToSet);
      viewholder.day_text.setText(weekday);
    } else if (holder.getItemViewType() == VIEW_TYPE_LOCATION) {
      TodayDetailsViewHolder viewholder = (TodayDetailsViewHolder) holder;
    }
  }

  public void populateListView() {

    // Create custom adapter object ( see below CustomAdapter.java )
  }

  public static void setListViewHeightBasedOnChildren(ListView listView) {
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null) {
      // pre-condition
      return;
    }

    int totalHeight = 0;
    for (int i = 0; i < listAdapter.getCount(); i++) {
      View listItem = listAdapter.getView(i, null, listView);
      listItem.measure(0, 0);
      totalHeight += listItem.getMeasuredHeight();
    }

    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    listView.setLayoutParams(params);
  }

  String getMonthName(int monthNumber) {
    String[] months = new DateFormatSymbols().getMonths();
    int n = monthNumber - 1;
    return (n >= 0 && n <= 11) ? months[n] : "wrong number";
  }

  public void setEventArrayList(ArrayList<Event> eventArrayList) {
    this.eventArrayList = eventArrayList;
  }

  public boolean isDataLoading() {
    return isDataLoading;
  }

  public void setDataLoading(boolean dataLoading) {
    isDataLoading = dataLoading;
    this.notifyItemChanged(1);
  }
}
