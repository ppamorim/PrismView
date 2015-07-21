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
package com.github.ppamorim.prismview.sample.ui.renderers.renderer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.github.ppamorim.prismview.sample.ui.renderers.viewholder.VaporViewHolder;
import com.github.ppamorim.recyclerrenderers.renderer.Renderer;
import com.github.ppamorim.recyclerrenderers.viewholder.RenderViewHolder;

public class VaporRenderer extends Renderer {
  @Override public RenderViewHolder onCreateViewHolder(ViewGroup viewGroup,
      LayoutInflater layoutInflater, int resourceId) {
    return new VaporViewHolder(layoutInflater.inflate(resourceId, viewGroup, false));
  }
}
