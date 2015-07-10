package com.github.ppamorim.prism;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentViewItem extends FragmentItem {

  private static final String TAG = "FragmentViewItem";
  private static final String KEY_POSITION = TAG + ":Position";

  private final String className;
  private final Bundle args;

  public FragmentViewItem(CharSequence tag, String className, Bundle args) {
    super(tag);
    this.className = className;
    this.args = args;
  }

  public static FragmentViewItem of(CharSequence title, Class<? extends Fragment> clazz) {
    return new FragmentViewItem(title, clazz.getName(), new Bundle());
  }

  public static FragmentViewItem of(CharSequence title, Class<? extends Fragment> clazz,
      Bundle args) {
    return new FragmentViewItem(title, clazz.getName(), args);
  }

  public static boolean hasPosition(Bundle args) {
    return args != null && args.containsKey(KEY_POSITION);
  }

  public static int getPosition(Bundle args) {
    return (hasPosition(args)) ? args.getInt(KEY_POSITION) : 0;
  }

  static void setPosition(Bundle args, int position) {
    args.putInt(KEY_POSITION, position);
  }

  public Fragment instantiate(Context context, int position) {
    setPosition(args, position);
    return Fragment.instantiate(context, className, args);
  }

}
