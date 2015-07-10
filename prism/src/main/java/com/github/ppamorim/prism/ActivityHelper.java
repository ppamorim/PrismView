/*
* Copyright (C) 2015 Pedro Paulo de Amorim.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.ppamorim.prism;

import android.graphics.Point;
import android.os.Build;
import android.view.Window;

/**
 * A Helper class to get the some parameters of the activity.
 */
public class ActivityHelper {

  private static int measuredWidth = -1;

  /**
   * Get the width of the activity, before, check the version
   * of the Android to use the proper method to get that value.
   *
   * @param window get the widow of the activity
   * @return the width of this activity
   */
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
