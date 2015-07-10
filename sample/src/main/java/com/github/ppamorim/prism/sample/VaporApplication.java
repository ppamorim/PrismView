package com.github.ppamorim.prism.sample;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Pedro Paulo on 7/9/2015.
 */
public class VaporApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(getApplicationContext());
  }

  @Override public void onTerminate() {
    super.onTerminate();
    Fresco.shutDown();
  }

}
