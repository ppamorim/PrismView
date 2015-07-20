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
package com.github.ppamorim.prism.sample.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.ppamorim.PrismPosition;
import com.github.ppamorim.SpringType;
import com.github.ppamorim.creator.FragmentViewItemAdapter;
import com.github.ppamorim.creator.FragmentViewItems;
import com.github.ppamorim.prism.PrismActivity;
import com.github.ppamorim.prism.sample.R;
import com.github.ppamorim.prism.sample.ui.fragment.HappyFragment;
import com.github.ppamorim.prism.sample.ui.fragment.SadFragment;
import com.github.ppamorim.prism.sample.util.ViewUtil;

public class BaseActivity extends PrismActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.recycler_view) RecyclerView recyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    setPrismPosition(PrismPosition.RIGHT);
    setSpringType(SpringType.SPEEDBOUNCESS);
    setBouncenessSpeed(5, 5);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    ButterKnife.bind(this);
    ViewUtil.configRecyclerView(this, recyclerView);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(getResources().getString(R.string.app_name));
    }
    configPrism();
  }

  @Override public void onBackPressed() {
    if (isRevelead()) {
      hide();
      return;
    }
    super.onBackPressed();
  }

  private void configPrism() {
    FragmentViewItemAdapter fragmentViewItemAdapter =
        new FragmentViewItemAdapter(FragmentViewItems.with(this)
          .add("happy0", HappyFragment.class)
          .add("happy1", SadFragment.class)
          .add("happy2", HappyFragment.class)
          .add("happy3", SadFragment.class)
          .add("happy4", HappyFragment.class)
          .create());
    setAdapter(fragmentViewItemAdapter);
  }

  public void onVaporClick(int position) {
    show(position);
  }

}
