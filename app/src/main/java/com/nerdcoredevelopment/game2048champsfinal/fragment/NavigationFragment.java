package com.nerdcoredevelopment.game2048champsfinal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.nerdcoredevelopment.game2048champsfinal.R;

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

        FrameLayout pregameLinearLayout = view.findViewById(R.id.fragment_navigation_pregame_frame_layout);
        FrameLayout announcementsLinearLayout = view.findViewById(R.id.fragment_navigation_announcements_linear_layout);
        FrameLayout leaderboardsLinearLayout = view.findViewById(R.id.fragment_navigation_leaderboards_linear_layout);
        FrameLayout settingsLinearLayout = view.findViewById(R.id.fragment_navigation_settings_linear_layout);
        FrameLayout shopLinearLayout = view.findViewById(R.id.fragment_navigation_shop_linear_layout);

        pregameLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentPreGameClicked();
                }
            }
        });
        announcementsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentAnnouncementsClicked();
                }
            }
        });
        leaderboardsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentLeaderboardsClicked();
                }
            }
        });
        settingsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onNavigationFragmentSettingsClicked();
                }
            }
        });
        shopLinearLayout.setOnClickListener(new View.OnClickListener() {
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
        void onNavigationFragmentPreGameClicked();
        void onNavigationFragmentAnnouncementsClicked();
        void onNavigationFragmentLeaderboardsClicked();
        void onNavigationFragmentSettingsClicked();
        void onNavigationFragmentShopClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNavigationFragmentInteractionListener) {
            mListener = (OnNavigationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNavigationFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
