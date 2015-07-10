package com.github.ppamorim.prism;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PrismItem extends Fragment {

  private final CharSequence tag;
  private final int resource;

  public PrismItem(CharSequence tag, int resource) {
    this.tag = tag;
    this.resource = resource;
  }

  public static PrismItem of(CharSequence tag, @LayoutRes int resource) {
    return new PrismItem(tag, resource);
  }

  public View initiate(LayoutInflater inflater, ViewGroup container) {
    return inflater.inflate(resource, container, false);
  }

}
