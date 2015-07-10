package com.github.ppamorim.prism.sample.ui.renderers.viewholder;

import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ppamorim.prism.sample.R;
import com.github.ppamorim.prism.sample.domain.model.Vapor;
import com.github.ppamorim.prism.sample.ui.activity.BaseActivity;
import com.github.ppamorim.prism.sample.util.ViewUtil;
import com.github.ppamorim.recyclerrenderers.viewholder.RenderViewHolder;

public class VaporViewHolder extends RenderViewHolder<Vapor> implements View.OnClickListener {

  @Bind(R.id.image) SimpleDraweeView image;

  public VaporViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    ((BaseActivity) getContext()).onVaporClick(getAdapterPosition());
  }

  @Override public void onBindView(Vapor vapor) {
    ViewUtil.bind(image, vapor.getUrl());
  }

}
