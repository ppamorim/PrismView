package com.github.ppamorim.prism.sample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.github.ppamorim.prism.sample.R;

public class HappyFragment extends Fragment {

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_happy, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

}
