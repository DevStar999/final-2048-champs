package com.nerdcoredevelopment.game2048champsfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {
    private final Context context;
    private final List<Integer> layoutResourceIds;

    public SliderAdapter(Context context, List<Integer> layoutResourceIds) {
        this.context = context;
        this.layoutResourceIds = layoutResourceIds;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout, null);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        viewHolder.sliderLayoutContainer.removeAllViews();
        View nestedLayout = inflater.inflate(layoutResourceIds.get(position), null);
        viewHolder.sliderLayoutContainer.addView(nestedLayout);
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return layoutResourceIds.size();
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        FrameLayout sliderLayoutContainer;
        public SliderViewHolder(View itemView) {
            super(itemView);
            sliderLayoutContainer = itemView.findViewById(R.id.slider_layout_container);
        }
    }
}
