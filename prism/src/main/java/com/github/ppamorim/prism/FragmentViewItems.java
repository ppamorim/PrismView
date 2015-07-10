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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

public class FragmentViewItems extends FragmentItems<FragmentViewItem> {

  public FragmentViewItems(Context context) {
    super(context);
  }

  public static Creator with(Context context) {
    return new Creator(context);
  }

  public static class Creator {

    private final FragmentViewItems items;

    public Creator(Context context) {
      items = new FragmentViewItems(context);
    }

    public Creator add(@StringRes int title, Class<? extends Fragment> clazz) {
      return add(FragmentViewItem.of(items.getContext().getString(title), clazz));
    }

    public Creator add(@StringRes int title, Class<? extends Fragment> clazz, Bundle args) {
      return add(FragmentViewItem.of(items.getContext().getString(title), clazz, args));
    }

    public Creator add(CharSequence title, Class<? extends Fragment> clazz) {
      return add(FragmentViewItem.of(title, clazz));
    }

    public Creator add(CharSequence title, Class<? extends Fragment> clazz, Bundle args) {
      return add(FragmentViewItem.of(title, clazz, args));
    }

    public Creator add(FragmentViewItem item) {
      items.add(item);
      return this;
    }

    public FragmentViewItems create() {
      return items;
    }
  }

}
