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

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * Provides a Facebook like
 * transition for your activity, this uses Rebound to provide the animations.
 */
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

  /**
   * Initializes the RootView loading the activity_prism layout
   * and add the view of the #layoutResID in the root view.
   *
   * @param layoutResID Main view layout.
   */
  @Override public void setContentView(int layoutResID) {
    super.setContentView(initializeRootView());
    initialize(LayoutInflater.from(getApplicationContext()).inflate(layoutResID, null, false));
  }

  /**
   * This initialize the RootView loading the activity_prism layout
   * and add the view of the #view in the root view.
   *
   * @param view Main view.
   */
  @Override public void setContentView(View view) {
    super.setContentView(initializeRootView());
    initialize(view);
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(initializeRootView(), params);
    initialize(view);
  }

  /**
   * This check the consistency of the #mainView object and add the #mainView view
   * at the root view, after create a new instance of prismView and add this at the
   * root too.
   * Then, move the #prismView to the right position.
   * @param savedInstanceState
   */
  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    if (mainView == null) {
      throw new IllegalStateException("mainView is null, did you set this view?");
    }
    root.addView(mainView);
    if (prismView == null) {
      prismView = new FrameLayout(getApplicationContext());
      prismView.setId(prismView.getClass().hashCode());
    }
    root.addView(prismView, FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT);
    initializePositions();
  }

  /**
   * Add listener for moveSpring when the activity is resumed.
   */
  @Override protected void onResume() {
    super.onResume();
    moveSpring().addListener(simpleSpringListener);
  }

  /**
   * Remove the listener for moveSpring when the activity is paused.
   */
  @Override protected void onPause() {
    super.onPause();
    moveSpring().removeListener(simpleSpringListener);
  }

  /**
   * Inflates the base layout if this library.
   * @return return the root view.
   */
  private FrameLayout initializeRootView() {
    return (FrameLayout) LayoutInflater.from(getApplicationContext())
        .inflate(R.layout.activity_prism, null, false);
  }

  /**
   * Instantiates the #mainView.
   * @param mainView Return a instantiated #mainView.
   */
  private void initialize(View mainView) {
    this.mainView = mainView;
  }

  /**
   * Sets X position of the main view.
   */
  private void initializePositions() {
    ViewCompat.setX(prismView, ActivityHelper.getWidth(getWindow()));
  }

  /**
   * Load and replace the #prismView with a fragment.
   * Then, lazily performs a animation (reveal).
   * @param fragment Instance of fragment.
   */
  public void load(Fragment fragment) {
    (getSupportFragmentManager().beginTransaction())
      .replace(prismView.getId(), fragment)
      .commit();
    prismView.postDelayed(new Runnable() {
      @Override public void run() {
        reveal();
      }
    }, 200);
  }

  /**
   * Active this method when you don't want to hide the mainView
   * when the animation is complete.
   * @param enabled set animation enabled.
   */
  public void setHideEnabled(boolean enabled) {
    this.hideEnabled = enabled;
  }

  /**
   * @return True if animation is enabled.
   */
  public boolean isHideEnabled() {
    return hideEnabled;
  }

  /**
   * @return Tension of the animation.
   */
  public int getTension() {
    return tension;
  }

  /**
   * Set the tension of the animation.
   * @param tension This value need to be bigger than zero.
   */
  public void setTension(int tension) {
    this.tension = tension;
  }

  /**
   * @return Friction of the animation.
   */
  public int getFriction() {
    return friction;
  }

  /**
   * Set the friction of the animation.
   * @param friction This value need to be bigger than zero.
   */
  public void setFriction(int friction) {
    this.friction = friction;
  }

  /**
   * @return Min size of the #mainView when the PrismView is reveled.
   */
  public double getSmallRatio() {
    return smallRatio;
  }

  /**
   * Set the smallest ratio of the #mainView.
   * @param smallRatio Need to be smaller than or equals 1.
   */
  public void setSmallRatio(double smallRatio) {
    if (smallRatio <= 1) {
      this.smallRatio = smallRatio;
    }
  }

  /**
   * Perform the reveal animation.
   */
  public void reveal() {
    reset();
    moveSpring().setCurrentValue(0);
    moveSpring().setEndValue(1);
  }

  /**
   * Perform the hide animation.
   */
  public void hide() {
    reset();
    moveSpring().setCurrentValue(1);
    moveSpring().setEndValue(0);
  }

  /**
   * @return True if getCurrentValue() is bigger than 0.
   */
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

  /**
   * Reset the view to perform the reveal or hide animation.
   */
  private void reset() {
    prismView.setVisibility(View.VISIBLE);
    mainView.setVisibility(View.VISIBLE);
  }

  /**
   * Create a new instance of SimpleSpringListener that performs
   * a translationX, ScaleX, ScaleY and alpha value change.
   *
   * At the finish of the animation, verify if the getCurrentValue()
   * is equals 1, if true, show the PrismView, else, show the MainView.
   */
  private SimpleSpringListener simpleSpringListener = new SimpleSpringListener() {
    @Override public void onSpringUpdate(Spring spring) {
      super.onSpringUpdate(spring);
      float ratio =  (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1,
          1, smallRatio);
      ViewCompat.setTranslationX(prismView, (float) SpringUtil.mapValueFromRangeToRange(
          spring.getCurrentValue(), 0, 1, activityWidth, 0));
      ViewCompat.setScaleX(mainView, ratio);
      ViewCompat.setScaleY(mainView, ratio);
      ViewCompat.setAlpha(mainView, ratio);
    }

    @Override public void onSpringAtRest(Spring spring) {
      super.onSpringAtRest(spring);
      if (hideEnabled) {
        if (spring.getCurrentValue() == 1) {
          showPrismView();
        } else {
          showMainView();
        }
      }
    }
  };

  /**
   * Create a new Instance of moveSpring if it's needed.
   * @return Instance of moveSpring.
   */
  private Spring moveSpring() {
    if (moveSpring == null) {
      moveSpring = SpringSystem
          .create()
          .createSpring()
          .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
    }
    return moveSpring;
  }

}
