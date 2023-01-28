package com.nerdcoredevelopment.game2048champsfinal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nerdcoredevelopment.game2048champsfinal.R;

/* TODO -> (1) Implement the GPGS Sign In feature. Whenever the user is -
           [a] Signed In = Change visibility of the GPGS Sign In Button to "invisible". Hide the text "PLAY" in the play
           button below and remove the layout margin bottom of 10dp in it's icon ImageView
           [b] NOT Signed In = Show the GPGS Sign In Button. Show the text "PLAY" in the play button below and add the
           layout margin of 10dp in it's icon ImageView
           (2) Implement the GPGS Achievements feature and assign the visibility of the respective button to "visible"
           (3) Implement the GPGS Leaderboards feature and assign the visibility of the respective button to "visible"
*/
public class NavigationFragment extends Fragment {
    private OnNavigationFragmentInteractionListener mListener;

    public NavigationFragment() {
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

        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        FrameLayout gpgsSignInFrameLayout = view.findViewById(R.id.fragment_navigation_gpgs_sign_in_frame_layout);
        FrameLayout pregameFrameLayout = view.findViewById(R.id.fragment_navigation_pregame_frame_layout);
        FrameLayout achievementsFrameLayout = view.findViewById(R.id.fragment_navigation_achievements_frame_layout);
        FrameLayout leaderboardsFrameLayout = view.findViewById(R.id.fragment_navigation_leaderboards_frame_layout);
        FrameLayout settingsFrameLayout = view.findViewById(R.id.fragment_navigation_settings_frame_layout);
        FrameLayout shopFrameLayout = view.findViewById(R.id.fragment_navigation_shop_frame_layout);

        gpgsSignInFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentGpgsSignInClicked();
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

        return view;
    }

    public interface OnNavigationFragmentInteractionListener {
        void onNavigationFragmentGpgsSignInClicked();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

