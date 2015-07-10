package com.github.ppamorim.prism.sample.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.ppamorim.prism.PrismActivity;
import com.github.ppamorim.prism.sample.R;
import com.github.ppamorim.prism.sample.ui.fragment.HappyFragment;
import com.github.ppamorim.prism.sample.util.ViewUtil;

public class BaseActivity extends PrismActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.recycler_view) RecyclerView recyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    ButterKnife.bind(this);
    ViewUtil.configRecyclerView(this, recyclerView);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null) {
      actionBar.setTitle(getResources().getString(R.string.app_name));
    }
  }

  @Override public void onBackPressed() {
    if(isRevelead()) {
      hide();
      return;
    }
    super.onBackPressed();
  }

  public void onVaporClick(int position) {
    load(new HappyFragment());
  }

}
