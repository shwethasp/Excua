package com.varsim.myexcua.library;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Deepu on 18-04-2017.
 */

public class Library {
  public static void showToastLong(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
  }

  public static void showToastShort(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }

  public static void logDebug(String message) {
    Log.d("vaRSim", message);
  }

  public static void hideKeyboard(Activity activity) {
    ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE))
            .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
  }

  public static Date mergeDates(Date date, Date time) {
    if (date == null) {
      return time;
    }
    if (time == null){
      return date;
    }

    Calendar aDate = Calendar.getInstance();
    aDate.setTime(date);

    Calendar aTime = Calendar.getInstance();
    aTime.setTime(time);

    Calendar aDateTime = Calendar.getInstance();
    aDateTime.set(Calendar.DAY_OF_MONTH, aDate.get(Calendar.DAY_OF_MONTH));
    aDateTime.set(Calendar.MONTH, aDate.get(Calendar.MONTH));
    aDateTime.set(Calendar.YEAR, aDate.get(Calendar.YEAR));
    aDateTime.set(Calendar.HOUR, aTime.get(Calendar.HOUR));
    aDateTime.set(Calendar.MINUTE, aTime.get(Calendar.MINUTE));

    return aDateTime.getTime();
  }

}
