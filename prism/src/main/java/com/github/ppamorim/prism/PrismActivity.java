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
import com.github.ppamorim.PrismPosition;
import com.github.ppamorim.SpringType;
import com.github.ppamorim.creator.FragmentViewItemAdapter;

/**
 * Provides a Facebook like
 * transition for your activity, this uses Rebound to provide the animations.
 */
public class PrismActivity extends AppCompatActivity {

  public static final int DEFAULT_TENSION = 40;
  public static final int DEFAULT_FRICTION = 5;
  public static final int DEFAULT_BOUNCENESS = 0;
  public static final int DEFAULT_SPEED = 20;
  public static final double DEFAULT_SMALL_RATIO = 0.8;

  private boolean hideEnabled = true;

  public int firstValue;
  public int secondValue;
  public double smallRatio = DEFAULT_SMALL_RATIO;

  public PrismPosition prismPosition = PrismPosition.RIGHT;
  public SpringType springType = SpringType.ORIGAMI;

  private FrameLayout root;
  private View mainView;
  private FrameLayout prismView;

  private FragmentViewItemAdapter fragmentViewItemAdapter;

  private ActivityHelper activityHelper;
  private Spring moveSpring;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityHelper = new ActivityHelper();
    activityHelper.config(getWindow());
  }

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
    initializeListeners();
  }

  /**
   * Remove the listener for moveSpring when the activity is paused.
   */
  @Override protected void onPause() {
    super.onPause();
    moveSpring().removeAllListeners();
  }

  /**
   * Inflates the base layout if this library.
   * @return return the root view.
   */
  private FrameLayout initializeRootView() {
    root = (FrameLayout) LayoutInflater.from(getApplicationContext())
        .inflate(R.layout.activity_prism, null, false);
    return root;
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
    if (prismView != null && activityHelper != null) {
      if (prismPosition == PrismPosition.RIGHT) {
        ViewCompat.setTranslationX(prismView, activityHelper.getWidth());
      } else if (prismPosition == PrismPosition.LEFT) {
        ViewCompat.setTranslationX(prismView, -activityHelper.getWidth());
      } else if (prismPosition == PrismPosition.TOP) {
        ViewCompat.setTranslationY(prismView, -activityHelper.getHeight());
      } else if (prismPosition == PrismPosition.BOTTOM) {
        ViewCompat.setTranslationY(prismView, activityHelper.getHeight());
      }
    }
  }

  private void initializeListeners() {
    SimpleSpringListener simpleSpringListener = null;
    if (prismPosition == PrismPosition.RIGHT) {
      simpleSpringListener = translationRight;
    } else if (prismPosition == PrismPosition.LEFT) {
      simpleSpringListener = translationLeft;
    } else if (prismPosition == PrismPosition.TOP) {
      simpleSpringListener = translationTop;
    } else if (prismPosition == PrismPosition.BOTTOM) {
      simpleSpringListener = translationBottom;
    }
    if (simpleSpringListener != null) {
      moveSpring().removeAllListeners().addListener(simpleSpringListener);
    }
  }

  /**
   * Load and replace the #prismView with a fragment.
   * Then, lazily performs a animation (reveal).
   * @param fragmentViewItemAdapter Instance of FragmentViewItemAdapter.
   */
  public void setAdapter(FragmentViewItemAdapter fragmentViewItemAdapter) {
    this.fragmentViewItemAdapter = fragmentViewItemAdapter;
  }

  public void show(int position) {
    if (fragmentViewItemAdapter == null) {
      throw new IllegalStateException("adapter is null");
    }
    if (fragmentViewItemAdapter.getLoadedPosition() != position) {
      fragmentViewItemAdapter.setLoadedPosition(position);
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(prismView.getId(), fragmentViewItemAdapter.getItem(position));
      transaction.commit();
    }
    reveal();
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


  public void setOrigami(int tension, int friction) {
    if (springType == SpringType.ORIGAMI) {
      this.firstValue = tension;
      this.secondValue = friction;
    } else {
      throw  new IllegalStateException("You must use SpringType.ORIGAMI"
          + " if you want to use setOrigami");
    }
  }

  public void setBouncenessSpeed(int bounceness, int speed) {
    if (springType == SpringType.SPEEDBOUNCINESS) {
      this.firstValue = bounceness;
      this.secondValue = speed;
    } else {
      throw  new IllegalStateException("You must use SpringType.SPEEDBOUNCINESS"
          + " if you want to use setSpeedBounceness");
    }
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
   * @return Type of the spring mode
   */
  public SpringType getSpringType() {
    return springType;
  }

  /**
   * Set the type of spring mode
   * @param springType Enum type
   */
  public void setSpringType(SpringType springType) {
    this.springType = springType;
  }

  /**
   * @return Position of the PrismView
   */
  public PrismPosition getPrismPosition() {
    return prismPosition;
  }

  /**
   * Set the side of the PrismView
   * @param prismPosition Enum position
   */
  public void setPrismPosition(PrismPosition prismPosition) {
    this.prismPosition = prismPosition;
    initializePositions();
    initializeListeners();
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
    initializePositions();
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
  private SimpleSpringListener translationRight = new SimpleSpringListener() {
    @Override public void onSpringUpdate(Spring spring) {
      super.onSpringUpdate(spring);
      double currentValue = spring.getCurrentValue();
      ViewCompat.setTranslationX(prismView, (float) SpringUtil.mapValueFromRangeToRange(
          currentValue, 0, 1, activityHelper.getWidth(), 0));
      mainViewUpdate(currentValue);
    }

    @Override public void onSpringAtRest(Spring spring) {
      super.onSpringAtRest(spring);
      onSpringAtRestOut(spring);
    }
  };

  /**
   * Create a new instance of SimpleSpringListener that performs
   * a translationX, ScaleX, ScaleY and alpha value change.
   *
   * At the finish of the animation, verify if the getCurrentValue()
   * is equals 1, if true, show the PrismView, else, show the MainView.
   */
  private SimpleSpringListener translationLeft = new SimpleSpringListener() {
    @Override public void onSpringUpdate(Spring spring) {
      super.onSpringUpdate(spring);
      double currentValue = spring.getCurrentValue();
      ViewCompat.setTranslationX(prismView, (float) SpringUtil.mapValueFromRangeToRange(
          currentValue, 0, 1, -activityHelper.getWidth(), 0));
      mainViewUpdate(currentValue);
    }

    @Override public void onSpringAtRest(Spring spring) {
      super.onSpringAtRest(spring);
      onSpringAtRestOut(spring);
    }
  };

  /**
   * Create a new instance of SimpleSpringListener that performs
   * a translationX, ScaleX, ScaleY and alpha value change.
   *
   * At the finish of the animation, verify if the getCurrentValue()
   * is equals 1, if true, show the PrismView, else, show the MainView.
   */
  private SimpleSpringListener translationTop = new SimpleSpringListener() {
        @Override public void onSpringUpdate(Spring spring) {
          super.onSpringUpdate(spring);
          double currentValue = spring.getCurrentValue();
          ViewCompat.setTranslationY(prismView,
              (float) SpringUtil.mapValueFromRangeToRange(currentValue, 0, 1,
                  -activityHelper.getHeight(), 0));
          mainViewUpdate(currentValue);
        }

        @Override public void onSpringAtRest(Spring spring) {
          super.onSpringAtRest(spring);
          onSpringAtRestOut(spring);
        }
      };

  /**
   * Create a new instance of SimpleSpringListener that performs
   * a translationX, ScaleX, ScaleY and alpha value change.
   *
   * At the finish of the animation, verify if the getCurrentValue()
   * is equals 1, if true, show the PrismView, else, show the MainView.
   */
  private SimpleSpringListener translationBottom = new SimpleSpringListener() {
    @Override public void onSpringUpdate(Spring spring) {
      super.onSpringUpdate(spring);
      double currentValue = spring.getCurrentValue();
      ViewCompat.setTranslationY(prismView,
          (float) SpringUtil.mapValueFromRangeToRange(currentValue, 0, 1,
              activityHelper.getHeight(), 0));
      mainViewUpdate(currentValue);
    }

    @Override public void onSpringAtRest(Spring spring) {
      super.onSpringAtRest(spring);
      onSpringAtRestOut(spring);
    }
  };

  private void mainViewUpdate(double currentValue) {
    float ratio =  (float) SpringUtil.mapValueFromRangeToRange(currentValue, 0, 1,
        1, smallRatio);
    ViewCompat.setScaleX(mainView, ratio);
    ViewCompat.setScaleY(mainView, ratio);
    ViewCompat.setAlpha(mainView, ratio);
  }

  private void onSpringAtRestOut(Spring spring) {
    if (hideEnabled) {
      if (spring.getCurrentValue() == 1) {
        showPrismView();
      } else {
        showMainView();
      }
    }
  }

  /**
   * Create a new Instance of moveSpring if it's needed.
   * @return Instance of moveSpring.
   */
  private Spring moveSpring() {

    SpringConfig springConfig = null;
    if (springType == SpringType.ORIGAMI) {
      if (isValuesNotSet()) {
        firstValue = DEFAULT_TENSION;
        secondValue = DEFAULT_FRICTION;
      }
      springConfig = SpringConfig.fromOrigamiTensionAndFriction(firstValue, secondValue);
    } else if (springType == SpringType.SPEEDBOUNCINESS) {
      if (isValuesNotSet()) {
        firstValue = DEFAULT_BOUNCENESS;
        secondValue = DEFAULT_SPEED;
      }
      springConfig = SpringConfig.fromBouncinessAndSpeed(firstValue, secondValue);
    }

    if (moveSpring == null) {
      if(springConfig != null) {
        moveSpring = SpringSystem.create().createSpring().setSpringConfig(springConfig);
      }
    } else {
      moveSpring.setSpringConfig(springConfig);
    }
    return moveSpring;
  }

  private boolean isValuesNotSet() {
    return firstValue == -1 || secondValue == -1;
  }

}
