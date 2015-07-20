package com.github.ppamorim.creator;

import android.support.v4.app.Fragment;

public class FragmentViewItemAdapter {

  private final FragmentViewItems fragmentViewItems;

  private int loadedPosition = -1;

  public FragmentViewItemAdapter(FragmentViewItems fragmentViewItems) {
    this.fragmentViewItems = fragmentViewItems;
  }

  public int getCount() {
    return fragmentViewItems.size();
  }

  public Fragment getItem(int position) {
    return getFragmentViewItem(position).instantiate(fragmentViewItems.getContext(), position);
  }

  public int getLoadedPosition() {
    return loadedPosition;
  }

  public void setLoadedPosition(int loadedPosition) {
    this.loadedPosition = loadedPosition;
  }

  protected FragmentViewItem getFragmentViewItem(int position) {
    if (position >= fragmentViewItems.size()) {
      throw new IllegalStateException("Your position is bigger than the size "
          + "of the fragmentViewItems");
    }
    return fragmentViewItems.get(position);
  }

}
