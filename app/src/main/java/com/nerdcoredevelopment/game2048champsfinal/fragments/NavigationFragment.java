package com.nerdcoredevelopment.game2048champsfinal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.nerdcoredevelopment.game2048champsfinal.R;

public class NavigationFragment extends Fragment {
    private Context context;
    private OnNavigationFragmentInteractionListener mListener;
    private FrameLayout gpgsSignInFrameLayout;
    private FrameLayout pregameFrameLayout;
    private AppCompatImageView pregameImageView;
    private AppCompatTextView pregameTextView;
    private FrameLayout achievementsFrameLayout;
    private FrameLayout leaderboardsFrameLayout;
    private FrameLayout settingsFrameLayout;
    private FrameLayout shopFrameLayout;

    public NavigationFragment() {
        // Required empty public constructor
    }

    private void initialise(View layoutView) {
        gpgsSignInFrameLayout = layoutView.findViewById(R.id.fragment_navigation_gpgs_sign_in_frame_layout);
        pregameFrameLayout = layoutView.findViewById(R.id.fragment_navigation_pregame_frame_layout);
        pregameImageView = layoutView.findViewById(R.id.fragment_navigation_pregame_image_view);
        pregameTextView = layoutView.findViewById(R.id.fragment_navigation_pregame_text_view);
        achievementsFrameLayout = layoutView.findViewById(R.id.fragment_navigation_achievements_frame_layout);
        leaderboardsFrameLayout = layoutView.findViewById(R.id.fragment_navigation_leaderboards_frame_layout);
        settingsFrameLayout = layoutView.findViewById(R.id.fragment_navigation_settings_frame_layout);
        shopFrameLayout = layoutView.findViewById(R.id.fragment_navigation_shop_frame_layout);
    }

    private void settingOnClickListeners() {
        gpgsSignInFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentGPGSSignInClicked();
                }
            }
        });
        pregameFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentPreGameClicked();
                }
            }
        });
        achievementsFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentAchievementsClicked();
                }
            }
        });
        leaderboardsFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentLeaderboardsClicked();
                }
            }
        });
        settingsFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentSettingsClicked();
                }
            }
        });
        shopFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentShopClicked();
                }
            }
        });
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

        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        initialise(view);

        settingOnClickListeners();

        return view;
    }

    private int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void hideSignInButton() {
        if (mListener != null) {
            gpgsSignInFrameLayout.setVisibility(View.INVISIBLE);
            pregameTextView.setVisibility(View.INVISIBLE);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) pregameImageView.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            pregameImageView.setLayoutParams(params);
        }
    }

    public void revealSignInButton() {
        if (mListener != null) {
            gpgsSignInFrameLayout.setVisibility(View.VISIBLE);
            pregameTextView.setVisibility(View.VISIBLE);
            int layoutMarginBottom = dpToPx(10, context);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) pregameImageView.getLayoutParams();
            params.setMargins(0, 0, 0, layoutMarginBottom);
            pregameImageView.setLayoutParams(params);
        }
    }

    public interface OnNavigationFragmentInteractionListener {
        void onNavigationFragmentGPGSSignInClicked();
        void onNavigationFragmentPreGameClicked();
        void onNavigationFragmentAchievementsClicked();
        void onNavigationFragmentLeaderboardsClicked();
        void onNavigationFragmentSettingsClicked();
        void onNavigationFragmentShopClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNavigationFragmentInteractionListener) {
            mListener = (OnNavigationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnNavigationFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
