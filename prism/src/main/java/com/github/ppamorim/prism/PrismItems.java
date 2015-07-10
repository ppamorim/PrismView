package com.github.ppamorim.prism;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

public class PrismItems extends PrismArray<PrismItem> {

  public PrismItems(Context context) {
    super(context);
  }

  public static Creator with(Context context) {
    return new Creator(context);
  }

  public static class Creator {

    private final PrismItems items;

    public Creator(Context context) {
      items = new PrismItems(context);
    }

    public Creator add(@StringRes int title, @LayoutRes int resource) {
      return add(PrismItem.of(items.getContext().getString(title), resource));
    }

    public Creator add(CharSequence title, @LayoutRes int resource) {
      return add(PrismItem.of(title, resource));
    }

    public Creator add(PrismItem item) {
      items.add(item);
      return this;
    }

    public PrismItems create() {
      return items;
    }

  }

}
