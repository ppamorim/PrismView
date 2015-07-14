package com.github.ppamorim.collection;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PrismItem extends FragmentItem {

  private final int resource;

  public PrismItem(CharSequence tag, int resource) {
    super(tag);
    this.resource = resource;
  }

  public static PrismItem of(CharSequence tag, @LayoutRes int resource) {
    return new PrismItem(tag, resource);
  }

  public View initiate(LayoutInflater inflater, ViewGroup container) {
    return inflater.inflate(resource, container, false);
  }

}
