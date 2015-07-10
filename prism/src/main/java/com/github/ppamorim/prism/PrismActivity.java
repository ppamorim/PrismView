package com.github.ppamorim.prism;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

public class PrismActivity extends AppCompatActivity {

  public static final int DEFAULT_TENSION = 40;
  public static final int DEFAULT_FRICTION = 5;
  public static final double DEFAULT_SMALL_RATIO = 0.8;

  private boolean hideEnabled = true;
  private int activityWidth;

  public int tension = DEFAULT_TENSION;
  public int friction = DEFAULT_FRICTION;
  public double smallRatio = DEFAULT_SMALL_RATIO;

  private FrameLayout root;
  private View mainView;
  private FrameLayout prismView;

  private Spring moveSpring;

  @Override public void setContentView(int layoutResID) {
    super.setContentView(initializeRootView());
    initialize(LayoutInflater.from(getApplicationContext()).inflate(layoutResID, null, false));
  }

  @Override public void setContentView(View view) {
    super.setContentView(initializeRootView());
    initialize(view);
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(initializeRootView(), params);
    initialize(view);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    if(mainView == null) {
      throw new IllegalStateException("mainView is null, did you set this view?");
    }
    root.addView(mainView);
    if(prismView == null) {
      prismView = new FrameLayout(getApplicationContext());
      prismView.setId(prismView.getClass().hashCode());
    }
    root.addView(prismView, FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT);
    initializePositions();
  }

  @Override protected void onResume() {
    super.onResume();
    moveSpring().addListener(simpleSpringListener);
  }

  @Override protected void onPause() {
    super.onPause();
    moveSpring().removeListener(simpleSpringListener);
  }

  private FrameLayout initializeRootView() {
    root = (FrameLayout) LayoutInflater.from(getApplicationContext())
        .inflate(R.layout.activity_prism, null, false);
    return root;
  }

  private void initialize(View mainView) {
    this.mainView = mainView;
  }

  private void initializePositions() {
    activityWidth = ActivityHelper.getWidth(getWindow());
    ViewCompat.setX(prismView, activityWidth);
  }

  public void load(Fragment fragment) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(prismView.getId(), fragment).commit();
    prismView.postDelayed(new Runnable() {
      @Override public void run() {
        reveal();
      }
    }, 200);
  }

  public void setHideEnabled(boolean enabled) {
    this.hideEnabled = enabled;
  }

  public boolean isHideEnabled() {
    return hideEnabled;
  }

  public int getTension() {
    return tension;
  }

  public void setTension(int tension) {
    this.tension = tension;
  }

  public int getFriction() {
    return friction;
  }

  public void setFriction(int friction) {
    this.friction = friction;
  }

  public double getSmallRatio() {
    return smallRatio;
  }

  public void setSmallRatio(double smallRatio) {
    this.smallRatio = smallRatio;
  }

  public void reveal() {
    reset();
    moveSpring().setCurrentValue(0);
    moveSpring().setEndValue(1);
  }

  public void hide() {
    reset();
    moveSpring().setCurrentValue(1);
    moveSpring().setEndValue(0);
  }

  public boolean isRevelead() {
    return moveSpring().getCurrentValue() > 0;
  }

  private void showMainView() {
    prismView.setVisibility(View.GONE);
    mainView.setVisibility(View.VISIBLE);
  }

  private void showPrismView() {
    prismView.setVisibility(View.VISIBLE);
    mainView.setVisibility(View.GONE);
  }

  private void reset() {
    prismView.setVisibility(View.VISIBLE);
    mainView.setVisibility(View.VISIBLE);
  }

  private SimpleSpringListener simpleSpringListener = new SimpleSpringListener() {
    @Override public void onSpringUpdate(Spring spring) {
      super.onSpringUpdate(spring);
      float ratio =  (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1,
          1, smallRatio);
      ViewCompat.setTranslationX(prismView, (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1,
          activityWidth, 0));
      ViewCompat.setScaleX(mainView, ratio);
      ViewCompat.setScaleY(mainView, ratio);
      ViewCompat.setAlpha(mainView, ratio);
    }

    @Override public void onSpringAtRest(Spring spring) {
      super.onSpringAtRest(spring);
      if(hideEnabled) {
        if (spring.getCurrentValue() == 1) {
          showPrismView();
        } else {
          showMainView();
        }
      }
    }
  };

  private Spring moveSpring() {
    if(moveSpring == null) {
      moveSpring = SpringSystem
          .create()
          .createSpring()
          .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
    }
    return moveSpring;
  }

}
