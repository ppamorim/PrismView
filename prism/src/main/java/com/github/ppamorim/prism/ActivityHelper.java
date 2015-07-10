package com.github.ppamorim.prism;

import android.graphics.Point;
import android.os.Build;
import android.view.Window;

public class ActivityHelper {

  private static int measuredWidth = -1;

  public static int getWidth(Window window) {
    if(measuredWidth == -1) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
        Point size = new Point();
        window.getWindowManager().getDefaultDisplay().getSize(size);
        measuredWidth = size.x;
      } else {
        measuredWidth = window.getWindowManager().getDefaultDisplay().getWidth();
      }
    }
    return measuredWidth;
  }

}
