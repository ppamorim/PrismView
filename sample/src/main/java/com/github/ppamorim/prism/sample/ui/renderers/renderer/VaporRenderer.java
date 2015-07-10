package com.github.ppamorim.prism.sample.ui.renderers.renderer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.github.ppamorim.prism.sample.ui.renderers.viewholder.VaporViewHolder;
import com.github.ppamorim.recyclerrenderers.renderer.Renderer;
import com.github.ppamorim.recyclerrenderers.viewholder.RenderViewHolder;

public class VaporRenderer extends Renderer {
  @Override public RenderViewHolder onCreateViewHolder(ViewGroup viewGroup,
      LayoutInflater layoutInflater, int resourceId) {
    return new VaporViewHolder(layoutInflater.inflate(resourceId, viewGroup, false));
  }
}
