package com.github.ppamorim.prism.sample.domain.model;

import com.github.ppamorim.prism.sample.R;
import com.github.ppamorim.recyclerrenderers.interfaces.Renderable;

public class Vapor implements Renderable {

  private String url;

  public Vapor(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  @Override public int getRenderableResourceId(int i) {
    return R.layout.adapter_vapor;
  }

}
