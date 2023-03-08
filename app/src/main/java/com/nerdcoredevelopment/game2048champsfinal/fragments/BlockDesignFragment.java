package com.nerdcoredevelopment.game2048champsfinal.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.nerdcoredevelopment.game2048champsfinal.enums.BlockDesigns;

import java.util.ArrayList;
import java.util.List;

public class BlockDesignFragment extends Fragment {
    private Context context;
    private OnBlockDesignFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private List<BlockDesigns> blockDesignsList;
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
                .findViewById(R.id.block_design_options_scrollview_child_block_design_fragment_linear_layout);

        List<AppCompatImageView> blockDesignOptionImageViewList = new ArrayList<>();
        List<AppCompatImageView> blockDesignOptionSelectedBackgroundList = new ArrayList<>();
        List<AppCompatImageView> blockDesignOptionSelectedImageViewList = new ArrayList<>();

        // Programmatically filling up the block design options
        boolean isSelectionDone = false;
        for (int i = 0; i < blockDesignsList.size(); i += 3) {
            int rowNumber = (i / 3) + 1;
            RelativeLayout blockDesignOptionsRow = new RelativeLayout(context);
            blockDesignOptionsRow.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 3; j++) {
                int optionNumber = j + 1;
                if (i + j >= blockDesignsList.size()) {
                    continue;
                }
                FrameLayout blockDesignOption = new FrameLayout(context);
                blockDesignOption.setBackgroundResource(R.drawable.rounded_corners_block_design_option);
                RelativeLayout.LayoutParams blockDesignOptionParams =
                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                if (j == 0) {
                    blockDesignOptionParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                } else if (j == 1) {
                    blockDesignOptionParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                } else {
                    blockDesignOptionParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                }
                blockDesignOption.setLayoutParams(blockDesignOptionParams);

                AppCompatImageView blockDesignOptionImageView = new AppCompatImageView(context);
                blockDesignOptionImageView.setBackgroundResource(R.drawable.rounded_corners_block_design_option_image_view);
                blockDesignOptionImageView.setTag("blockOption_row" + String.format("%03d", rowNumber)
                        + "_option" + String.format("%03d", optionNumber));
                blockDesignOptionImageView.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
                blockDesignOptionImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                blockDesignOptionImageView.setImageResource(blockDesignsList.get(i + j).getBlockDrawableResourceId());
                blockDesignOptionImageView.setRotationX(blockDesignsList.get(i + j).getBlockDrawableRotationX());
                blockDesignOptionImageView.setRotationY(blockDesignsList.get(i + j).getBlockDrawableRotationY());
                FrameLayout.LayoutParams blockDesignOptionImageViewParams = new FrameLayout.LayoutParams(dpToPx(72), dpToPx(72));
                blockDesignOptionImageView.setLayoutParams(blockDesignOptionImageViewParams);

                AppCompatImageView blockDesignOptionSelectedBackground = new AppCompatImageView(context);
                blockDesignOptionSelectedBackground.setVisibility(View.GONE);
                blockDesignOptionSelectedBackground.setImageResource(R.drawable.rounded_corners_block_design_option_selected);
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

                if (sharedPreferences.getString("selectedBlockDrawableEnumName", BlockDesigns.BLOCK_PIRATE.name())
                        .equals(blockDesignsList.get(i + j).name())) {
                    isSelectionDone = true;
                    blockDesignOptionImageView.setClickable(false);
                    blockDesignOptionSelectedBackground.setVisibility(View.VISIBLE);
                    blockDesignOptionSelectedImageView.setVisibility(View.VISIBLE);
                    blockDesignOptionInUseImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    blockDesignOptionInUseImageView.setImageResource(blockDesignsList.get(i + j).getBlockDrawableResourceId());
                    blockDesignOptionInUseImageView.setRotationX(blockDesignsList.get(i + j).getBlockDrawableRotationX());
                    blockDesignOptionInUseImageView.setRotationY(blockDesignsList.get(i + j).getBlockDrawableRotationY());
                    blockDesignPreviewImageView.setImageResource(blockDesignsList.get(i + j).getBlockPreviewResourceId());
                }
                blockDesignOptionImageViewList.add(blockDesignOptionImageView);
                blockDesignOptionSelectedBackgroundList.add(blockDesignOptionSelectedBackground);
                blockDesignOptionSelectedImageViewList.add(blockDesignOptionSelectedImageView);
            }

            blockDesignOptionsScrollViewChild.addView(blockDesignOptionsRow);
        }

        if (!isSelectionDone) {
            blockDesignOptionImageViewList.get(0).setClickable(false);
            blockDesignOptionSelectedBackgroundList.get(0).setVisibility(View.VISIBLE);
            blockDesignOptionSelectedImageViewList.get(0).setVisibility(View.VISIBLE);
            blockDesignOptionInUseImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            blockDesignOptionInUseImageView.setImageResource(blockDesignsList.get(0).getBlockDrawableResourceId());
            blockDesignOptionInUseImageView.setRotationX(blockDesignsList.get(0).getBlockDrawableRotationX());
            blockDesignOptionInUseImageView.setRotationY(blockDesignsList.get(0).getBlockDrawableRotationY());
            blockDesignPreviewImageView.setImageResource(blockDesignsList.get(0).getBlockPreviewResourceId());
        }

        // Setting onClick listeners to the block design options
        for (int i = 0; i < blockDesignsList.size(); i++) {
            int finalI = i;
            blockDesignOptionImageViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    blockDesignOptionImageViewList.get(finalI).setClickable(false);
                    blockDesignOptionSelectedBackgroundList.get(finalI).setVisibility(View.VISIBLE);
                    blockDesignOptionSelectedImageViewList.get(finalI).setVisibility(View.VISIBLE);
                    blockDesignOptionInUseImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    blockDesignOptionInUseImageView.setImageResource(blockDesignsList.get(finalI).getBlockDrawableResourceId());
                    blockDesignOptionInUseImageView.setRotationX(blockDesignsList.get(finalI).getBlockDrawableRotationX());
                    blockDesignOptionInUseImageView.setRotationY(blockDesignsList.get(finalI).getBlockDrawableRotationY());
                    blockDesignPreviewImageView.setImageResource(blockDesignsList.get(finalI).getBlockPreviewResourceId());

                    for (int j = 0; j < blockDesignsList.size(); j++) {
                        if (finalI != j) {
                            blockDesignOptionImageViewList.get(j).setClickable(true);
                            blockDesignOptionSelectedBackgroundList.get(j).setVisibility(View.GONE);
                            blockDesignOptionSelectedImageViewList.get(j).setVisibility(View.GONE);
                        }
                    }

                    sharedPreferences.edit().putString("selectedBlockDrawableEnumName",
                            blockDesignsList.get(finalI).name()).apply();
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
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

        blockDesignOptionInUseImageView = view.findViewById(R.id.block_design_option_in_use_block_design_fragment_image_view);
        blockDesignPreviewImageView = view.findViewById(R.id.block_design_preview_block_design_fragment_image_view);

        blockDesignsList = new ArrayList<>(BlockDesigns.getAllBlockDesigns());

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
            throw new RuntimeException(context + " must implement OnBlockDesignFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}