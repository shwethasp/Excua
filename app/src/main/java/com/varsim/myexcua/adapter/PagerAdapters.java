package com.varsim.myexcua.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.varsim.myexcua.fragment.AllFragment;
import com.varsim.myexcua.fragment.TodayFragment;
import com.varsim.myexcua.fragment.TomorrowFragment;

/**
 * Created by varsi on 16-03-2017.
 */

public class PagerAdapters extends FragmentStatePagerAdapter {

    //integer to cout the number of tabs
    int tabCount;
    String tabTitles[] = new String[] { "Today", "Tomorrow", "All" };
    Context mContext;

    public PagerAdapters(FragmentManager fm, int tabCount) {
        super(fm);
        //initializing the tabcount
        this.tabCount = tabCount;
//        this.mContext = ()fm;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("Position ",""+ position);
        //returning the current tabs
        switch (position){
            case 0 :
                TodayFragment tab1 = new TodayFragment();
                return tab1;

            case 1 :
                TomorrowFragment tab2 = new TomorrowFragment();
                return tab2;
            case 2 :
                AllFragment tab3 = new AllFragment();
                return tab3;
//            default:
//                Log.d("Position ",""+ position);
//                return null;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position){
        //return super.getPageTitle(position);
        switch (position) {
            case 0 : return "Today";
            case 1 : return "Tomorrow";
            case 2 : return "All";
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapters.POSITION_NONE;
    }


}
