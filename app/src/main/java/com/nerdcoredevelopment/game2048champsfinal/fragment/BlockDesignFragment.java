package com.nerdcoredevelopment.game2048champsfinal.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.nerdcoredevelopment.game2048champsfinal.R;

import java.util.ArrayList;
import java.util.List;

public class BlockDesignFragment extends Fragment {
    private Context context;
    private OnBlockDesignFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private List<Pair<Integer, Integer>> blockDesignDrawableResourceIds;
    private AppCompatImageView blockDesignOptionInUseImageView;
    private AppCompatImageView blockDesignPreviewImageView;

    public BlockDesignFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @SuppressLint("DefaultLocale")
    private void initialiseBlockDesignOptions(View layoutView) {
        LinearLayout blockDesignOptionsScrollViewChild = layoutView
                .findViewById(R.id.block_design_options_scrollview_child_linear_layout);

        List<AppCompatImageView> blockDesignOptionImageViewList = new ArrayList<>();
        List<AppCompatImageView> blockDesignOptionSelectedBackgroundList = new ArrayList<>();
        List<AppCompatImageView> blockDesignOptionSelectedImageViewList = new ArrayList<>();

        // Programmatically filling up the block design options
        for (int i = 0; i < blockDesignDrawableResourceIds.size(); i += 3) {
            int rowNumber = (i / 3) + 1;
            RelativeLayout blockDesignOptionsRow = new RelativeLayout(context);
            blockDesignOptionsRow.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 3; j++) {
                int optionNumber = j + 1;
                if (i + j >= blockDesignDrawableResourceIds.size()) {
                    continue;
                }
                FrameLayout blockDesignOption = new FrameLayout(context);
                blockDesignOption.setBackgroundResource(R.drawable.rounded_corner_block_design_option);
                RelativeLayout.LayoutParams blockDesignOptionParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                if (j == 0) {
                    blockDesignOptionParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                } else if (j == 1) {
                    blockDesignOptionParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                } else if (j == 2) {
                    blockDesignOptionParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                }
                blockDesignOption.setLayoutParams(blockDesignOptionParams);

                AppCompatImageView blockDesignOptionImageView = new AppCompatImageView(context);
                blockDesignOptionImageView.setBackgroundResource(R.drawable.rounded_corner_block_design_option_image_view);
                blockDesignOptionImageView.setTag("blockOption_row" + String.format("%03d", rowNumber)
                        + "_option" + String.format("%03d", optionNumber));
                blockDesignOptionImageView.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
                blockDesignOptionImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                blockDesignOptionImageView.setImageResource(blockDesignDrawableResourceIds.get(i + j).first);
                FrameLayout.LayoutParams blockDesignOptionImageViewParams = new FrameLayout.LayoutParams(dpToPx(72), dpToPx(72));
                blockDesignOptionImageView.setLayoutParams(blockDesignOptionImageViewParams);

                AppCompatImageView blockDesignOptionSelectedBackground = new AppCompatImageView(context);
                blockDesignOptionSelectedBackground.setVisibility(View.GONE);
                blockDesignOptionSelectedBackground.setImageResource(R.drawable.rounded_corner_block_design_option_selected);
                FrameLayout.LayoutParams blockDesignOptionSelectedBackgroundParams = new FrameLayout.LayoutParams(dpToPx(72), dpToPx(72));
                blockDesignOptionSelectedBackground.setLayoutParams(blockDesignOptionSelectedBackgroundParams);

                AppCompatImageView blockDesignOptionSelectedImageView = new AppCompatImageView(context);
                blockDesignOptionSelectedImageView.setVisibility(View.GONE);
                blockDesignOptionSelectedImageView.setScaleX(0.75f);
                blockDesignOptionSelectedImageView.setScaleY(0.75f);
                blockDesignOptionSelectedImageView.setImageResource(R.drawable.block_design_fragment_selected);
                FrameLayout.LayoutParams blockDesignOptionSelectedImageViewParams = new FrameLayout.LayoutParams(dpToPx(72), dpToPx(72));
                blockDesignOptionSelectedImageView.setLayoutParams(blockDesignOptionSelectedImageViewParams);

                blockDesignOption.addView(blockDesignOptionImageView);
                blockDesignOption.addView(blockDesignOptionSelectedBackground);
                blockDesignOption.addView(blockDesignOptionSelectedImageView);

                blockDesignOptionsRow.addView(blockDesignOption);

                if ((sharedPreferences.getInt("blockDrawableResourceId", R.drawable.block_cell_x)
                        == blockDesignDrawableResourceIds.get(i + j).first)
                        && (sharedPreferences.getInt("blockDrawablePreviewResourceId", R.drawable.block_preview_cell_x)
                        == blockDesignDrawableResourceIds.get(i + j).second)) {
                    blockDesignOptionImageView.setClickable(false);
                    blockDesignOptionSelectedBackground.setVisibility(View.VISIBLE);
                    blockDesignOptionSelectedImageView.setVisibility(View.VISIBLE);
                    blockDesignOptionInUseImageView.setImageResource(blockDesignDrawableResourceIds.get(i + j).first);
                    blockDesignPreviewImageView.setImageResource(blockDesignDrawableResourceIds.get(i + j).second);
                }
                blockDesignOptionImageViewList.add(blockDesignOptionImageView);
                blockDesignOptionSelectedBackgroundList.add(blockDesignOptionSelectedBackground);
                blockDesignOptionSelectedImageViewList.add(blockDesignOptionSelectedImageView);
            }

            blockDesignOptionsScrollViewChild.addView(blockDesignOptionsRow);
        }

        // Setting onClick listeners to the block design options
        for (int i = 0; i < blockDesignDrawableResourceIds.size(); i++) {
            int finalI = i;
            blockDesignOptionImageViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    blockDesignOptionImageViewList.get(finalI).setClickable(false);
                    blockDesignOptionSelectedBackgroundList.get(finalI).setVisibility(View.VISIBLE);
                    blockDesignOptionSelectedImageViewList.get(finalI).setVisibility(View.VISIBLE);
                    blockDesignOptionInUseImageView.setImageResource(blockDesignDrawableResourceIds.get(finalI).first);
                    blockDesignPreviewImageView.setImageResource(blockDesignDrawableResourceIds.get(finalI).second);

                    for (int j = 0; j < blockDesignDrawableResourceIds.size(); j++) {
                        if (finalI != j) {
                            blockDesignOptionImageViewList.get(j).setClickable(true);
                            blockDesignOptionSelectedBackgroundList.get(j).setVisibility(View.GONE);
                            blockDesignOptionSelectedImageViewList.get(j).setVisibility(View.GONE);
                        }
                    }

                    sharedPreferences.edit().putInt("blockDrawableResourceId",
                            blockDesignDrawableResourceIds.get(finalI).first).apply();
                    sharedPreferences.edit().putInt("blockDrawablePreviewResourceId",
                            blockDesignDrawableResourceIds.get(finalI).second).apply();
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        sharedPreferences = context.getSharedPreferences("com.nerdcoredevelopment.game2048champsfinal", Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_block_design, container, false);

        // Back button in the title text of the fragment
        AppCompatImageView backButton = view.findViewById(R.id.title_back_block_design_fragment_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onBlockDesignFragmentInteractionBackClicked();
                }
            }
        });

        blockDesignOptionInUseImageView = view.findViewById(R.id.block_design_option_in_use_image_view);
        blockDesignPreviewImageView = view.findViewById(R.id.block_design_preview_image_view);

        blockDesignDrawableResourceIds = new ArrayList<>() {{
            // Keeping the first entry as the default block entry
            add(new Pair<>(R.drawable.block_cell_x, R.drawable.block_preview_cell_x));
            add(new Pair<>(R.drawable.block_alien, R.drawable.block_preview_alien));
            add(new Pair<>(R.drawable.block_heart, R.drawable.block_preview_heart));
            add(new Pair<>(R.drawable.block_poop, R.drawable.block_preview_poop));
            add(new Pair<>(R.drawable.block_rock, R.drawable.block_preview_rock));
            add(new Pair<>(R.drawable.block_cactus, R.drawable.block_preview_cactus));
            add(new Pair<>(R.drawable.block_hurdle, R.drawable.block_preview_hurdle));
            add(new Pair<>(R.drawable.block_ninja, R.drawable.block_preview_ninja));
            add(new Pair<>(R.drawable.block_stormtrooper, R.drawable.block_preview_stormtrooper));
            add(new Pair<>(R.drawable.block_vault, R.drawable.block_preview_vault));
        }};

        initialiseBlockDesignOptions(view);

        return view;
    }


    public interface OnBlockDesignFragmentInteractionListener {
        void onBlockDesignFragmentInteractionBackClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnBlockDesignFragmentInteractionListener) {
            mListener = (OnBlockDesignFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBlockDesignFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
