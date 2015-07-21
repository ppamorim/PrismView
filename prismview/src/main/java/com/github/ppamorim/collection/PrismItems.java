package com.github.ppamorim.collection;

import android.content.Context;
import android.support.annotation.LayoutRes;

public class PrismItems extends FragmentItems<PrismItem> {

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

    public Creator add(@LayoutRes int resource) {
      return add(PrismItem.of("teste", resource));
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
