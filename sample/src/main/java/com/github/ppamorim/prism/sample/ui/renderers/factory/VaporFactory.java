package com.github.ppamorim.prism.sample.ui.renderers.factory;

import com.github.ppamorim.prism.sample.R;
import com.github.ppamorim.prism.sample.ui.renderers.renderer.VaporRenderer;
import com.github.ppamorim.recyclerrenderers.interfaces.RendererFactory;
import com.github.ppamorim.recyclerrenderers.renderer.Renderer;

public class VaporFactory implements RendererFactory {
  @Override public Renderer getRenderer(int id) {
    switch (id) {
      case R.layout.adapter_vapor:
        return new VaporRenderer();
      default:
        return null;
    }
  }
}
