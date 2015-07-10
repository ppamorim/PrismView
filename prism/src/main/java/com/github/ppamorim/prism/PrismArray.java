package com.github.ppamorim.prism;

import android.content.Context;
import android.support.v4.app.Fragment;
import java.util.ArrayList;

public abstract class PrismArray <T extends Fragment> extends ArrayList<T> {
  private final Context context;
  protected PrismArray(Context context) {
    this.context = context;
  }
  public Context getContext() {
    return context;
  }
}
