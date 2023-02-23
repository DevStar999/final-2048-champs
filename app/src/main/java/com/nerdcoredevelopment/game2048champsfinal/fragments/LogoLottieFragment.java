package com.nerdcoredevelopment.game2048champsfinal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.nerdcoredevelopment.game2048champsfinal.R;

public class LogoLottieFragment extends Fragment {
    private LottieAnimationView gamePreviewSpotlightLottie;

    public LogoLottieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        View view = inflater.inflate(R.layout.fragment_logo_lottie, container, false);

        gamePreviewSpotlightLottie = view.findViewById(R.id.game_preview_spotlight_logo_lottie_fragment_lottie);
        gamePreviewSpotlightLottie.playAnimation();

        return view;
    }

    public void updateRotatingLightState(boolean isChecked) {
        if (isChecked) {
            gamePreviewSpotlightLottie.playAnimation();
        } else {
            gamePreviewSpotlightLottie.pauseAnimation();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}