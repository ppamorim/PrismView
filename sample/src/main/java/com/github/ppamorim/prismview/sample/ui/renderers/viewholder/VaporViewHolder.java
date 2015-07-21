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
package com.github.ppamorim.prismview.sample.ui.renderers.viewholder;

import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ppamorim.prismview.sample.R;
import com.github.ppamorim.prismview.sample.domain.model.Vapor;
import com.github.ppamorim.prismview.sample.ui.activity.BaseActivity;
import com.github.ppamorim.prismview.sample.util.ViewUtil;
import com.github.ppamorim.recyclerrenderers.viewholder.RenderViewHolder;

public class VaporViewHolder extends RenderViewHolder<Vapor> implements View.OnClickListener {

  @Bind(R.id.image) SimpleDraweeView image;
  @Bind(R.id.type) TextView type;

  public VaporViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    ((BaseActivity) getContext()).onVaporClick(getAdapterPosition());
  }

  @Override public void onBindView(Vapor vapor) {
    type.setText(vapor.getTitle());
    ViewUtil.bind(image, vapor.getUrl());
  }

}
