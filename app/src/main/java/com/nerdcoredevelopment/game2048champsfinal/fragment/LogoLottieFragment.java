package com.nerdcoredevelopment.game2048champsfinal.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.nerdcoredevelopment.game2048champsfinal.R;

public class LogoLottieFragment extends Fragment {
    private Context context;
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
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.nerdcoredevelopment.game2048champsfinal", Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_logo_lottie, container, false);

        gamePreviewSpotlightLottie = view.findViewById(R.id.game_preview_spotlight_lottie);
        if (sharedPreferences.getBoolean("toggleRotatingLight", true)) {
            gamePreviewSpotlightLottie.playAnimation();
        } else {
            gamePreviewSpotlightLottie.pauseAnimation();
        }

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
        this.context = context;
    }
}