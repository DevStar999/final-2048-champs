package com.nerdcoredevelopment.game2048champsfinal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.nerdcoredevelopment.game2048champsfinal.R;
import com.nerdcoredevelopment.game2048champsfinal.animations.ToolAnimationsUtility;

public class DestroyAreaFragment extends Fragment {
    private OnDestroyAreaFragmentInteractionListener mListener;
    private AppCompatImageView backButton;
    private LottieAnimationView rotatingLightLottie;
    private LottieAnimationView destroyAreaPreviewLottie;
    private AppCompatImageView toolUseCompletedImageView;
    private AppCompatTextView toolDescriptionTextView;
    private boolean isToolUseComplete;

    public DestroyAreaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void settingOnClickListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDestroyAreaFragmentInteractionBackClicked();
                }
            }
        });
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

        View view = inflater.inflate(R.layout.fragment_destroy_area, container, false);

        backButton = view.findViewById(R.id.back_destroy_area_fragment_button);
        rotatingLightLottie = view.findViewById(R.id.rotating_light_destroy_area_fragment_lottie);
        destroyAreaPreviewLottie = view.findViewById(R.id.destroy_area_preview_destroy_area_fragment_lottie);
        destroyAreaPreviewLottie.setMaxFrame(50);
        toolUseCompletedImageView = view.findViewById(R.id.tool_title_completed_destroy_area_fragment_image_view);
        toolDescriptionTextView = view.findViewById(R.id.tool_description_destroy_area_fragment_text_view);
        isToolUseComplete = true;

        // Making tool lottie view emerge so that it grabs attention during the tool fragment transition
        CountDownTimer postFragmentSetupTimer = new CountDownTimer(225, 10000) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                isToolUseComplete = false;
            }
        };
        new CountDownTimer(150, 10000) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                ToolAnimationsUtility.toolLottieEmergeAnimation(destroyAreaPreviewLottie, 200);
                postFragmentSetupTimer.start();
            }
        }.start();

        settingOnClickListeners();

        return view;
    }

    public boolean checkToolUseState() {
        return isToolUseComplete;
    }

    public interface OnDestroyAreaFragmentInteractionListener {
        void onDestroyAreaFragmentInteractionBackClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDestroyAreaFragmentInteractionListener) {
            mListener = (OnDestroyAreaFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnDestroyAreaFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
