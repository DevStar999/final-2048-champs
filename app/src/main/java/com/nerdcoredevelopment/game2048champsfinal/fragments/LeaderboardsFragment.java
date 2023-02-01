package com.nerdcoredevelopment.game2048champsfinal.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.nerdcoredevelopment.game2048champsfinal.R;
import com.nerdcoredevelopment.game2048champsfinal.enums.GameModes;
import com.nerdcoredevelopment.game2048champsfinal.manager.LeaderboardsManager;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardsFragment extends Fragment {
    // Back button in the title text of the fragment
    private AppCompatImageView backButton;

    // Root layout of the fragment
    private ConstraintLayout rootLayout;

    // Interaction listener of this fragment
    private OnLeaderboardsFragmentInteractionListener mListener;

    // Parent Activity of the current fragment
    private Activity parentActivity;

    // Utility class for MainActivity
    private LeaderboardsManager leaderboardsManager;

    // Attribute for determining game state
    private SharedPreferences sharedPreferences;

    // Using the GameModes enum for storing the game mode selected by the user
    private GameModes currentGameMode;

    // Attributes for determining game mode
    private List<String> allGameModes;
    private AppCompatTextView gameModeTextView;

    // Attributes for determining game size
    private List<String> allCurrentGameSizes;
    private AppCompatTextView gameSizeTextView;

    // Game modes customisation UI elements
    private LottieAnimationView gamePreviewSpotLightLottie;
    private AppCompatImageView gamePreviewImageView;
    private AppCompatImageView modeLeft;
    private AppCompatImageView modeRight;
    private AppCompatImageView sizeLeft;
    private AppCompatImageView sizeRight;
    private AppCompatButton showLeaderboardsButton;

    public LeaderboardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialise(View layoutView) {
        // Initialising and setting onClick listener for the back button in title
        backButton = layoutView.findViewById(R.id.title_back_leaderboards_fragment_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onLeaderboardsFragmentInteractionBackClicked();
                }
            }
        });

        // Initialising the root layout
        rootLayout = layoutView.findViewById(R.id.root_layout_leaderboards_fragment);

        // Initialising preGameManager
        leaderboardsManager = new LeaderboardsManager(this.parentActivity, layoutView);

        // Initialising sharedPreferences
        sharedPreferences = this.parentActivity.getSharedPreferences("com.nerdcoredevelopment.game2048champsfinal",
                Context.MODE_PRIVATE);

        // The default game mode
        currentGameMode = GameModes.getGameModeEnum(
                sharedPreferences.getInt("gameMatrixColumns", 4),
                sharedPreferences.getInt("gameMatrixRows", 4),
                sharedPreferences.getString("gameMode", "SQUARE"));

        // Initialising allGameModes List and gameModeTextView TextView
        allGameModes = GameModes.getAllGameModes();
        gameModeTextView = layoutView.findViewById(R.id.game_mode_leaderboards_fragment_text_view);
        gameModeTextView.setText(currentGameMode.getMode());
        leaderboardsManager.updateModeBrowseIcons(currentGameMode.getMode(), allGameModes);

        // Initialising allCurrentGameSizes List and gameSizeTextView TextView
        allCurrentGameSizes = GameModes.getAllGameVariantsOfMode(currentGameMode.getMode());
        allCurrentGameSizes.removeAll(new ArrayList<String>() {{ add("? X ?"); }});
        gameSizeTextView = layoutView.findViewById(R.id.game_size_leaderboards_fragment_text_view);
        gameSizeTextView.setText(currentGameMode.getDimensions());
        leaderboardsManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

        gamePreviewSpotLightLottie = layoutView.findViewById(R.id.game_preview_leaderboards_fragment_spotlight_lottie);
        if (sharedPreferences.getBoolean("toggleRotatingLight", true)) {
            gamePreviewSpotLightLottie.playAnimation();
        } else {
            gamePreviewSpotLightLottie.pauseAnimation();
        }
        gamePreviewImageView = layoutView.findViewById(R.id.game_preview_leaderboards_fragment_image_view);
        modeLeft = layoutView.findViewById(R.id.game_mode_left_arrow_leaderboards_fragment_image_view);
        modeRight = layoutView.findViewById(R.id.game_mode_right_arrow_leaderboards_fragment_image_view);
        sizeLeft = layoutView.findViewById(R.id.game_size_left_arrow_leaderboards_fragment_image_view);
        sizeRight = layoutView.findViewById(R.id.game_size_right_arrow_leaderboards_fragment_image_view);
        showLeaderboardsButton = layoutView.findViewById(R.id.show_leaderboards_fragment_button);

        // Updating the preview accordingly
        leaderboardsManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());
    }

    private void settingsOnClickListenerForShowLeaderboardsButton() {
        showLeaderboardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                rootLayout.setEnabled(false);
                modeLeft.setEnabled(false); modeRight.setEnabled(false);
                sizeLeft.setEnabled(false); sizeRight.setEnabled(false);

                new CountDownTimer(1000, 10000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    @Override
                    public void onFinish() {
                        view.setEnabled(true);
                        rootLayout.setEnabled(true);
                        modeLeft.setEnabled(true); modeRight.setEnabled(true);
                        sizeLeft.setEnabled(true); sizeRight.setEnabled(true);
                    }
                }.start();

                if (mListener != null) {
                    mListener.onLeaderboardsFragmentInteractionShowLeaderboard(currentGameMode.getLeaderboardStringResourceId());
                }
            }
        });
    }

    private void handleGameModeBrowse(int indexOfCurrentMode) {
        // Make changes to the game mode and size both
        String newGameMode = allGameModes.get(indexOfCurrentMode);
        allCurrentGameSizes = GameModes.getAllGameVariantsOfMode(newGameMode);
        allCurrentGameSizes.removeAll(new ArrayList<String>() {{ add("? X ?"); }});
        currentGameMode = GameModes.getGameModeEnum(
                Character.getNumericValue(allCurrentGameSizes.get(0).charAt(0)),
                Character.getNumericValue(allCurrentGameSizes.get(0)
                        .charAt(allCurrentGameSizes.get(0).length() - 1)), newGameMode);

        // Updating the text views for both mode and size
        gameModeTextView.setText(currentGameMode.getMode());
        gameSizeTextView.setText(currentGameMode.getDimensions());

        // Updating Game Mode Browse Icons
        leaderboardsManager.updateModeBrowseIcons(currentGameMode.getMode(), allGameModes);

        // Updating Game Size Browse Icons
        leaderboardsManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

        // Updating Preview
        leaderboardsManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());
    }

    private void settingOnClickListenersForModeButtons() {
        modeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getVisibility() == View.VISIBLE) {
                    int indexOfCurrentMode = allGameModes.indexOf(currentGameMode.getMode());
                    indexOfCurrentMode--;
                    handleGameModeBrowse(indexOfCurrentMode);
                }
            }
        });
        modeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getVisibility() == View.VISIBLE) {
                    int indexOfCurrentMode = allGameModes.indexOf(currentGameMode.getMode());
                    indexOfCurrentMode++;
                    handleGameModeBrowse(indexOfCurrentMode);
                }
            }

        });
    }

    private void handleGameSizeBrowse(int indexOfCurrentSize) {
        // Make changes to the game size
        String newGameSize = allCurrentGameSizes.get(indexOfCurrentSize);
        currentGameMode = GameModes.getGameModeEnum(
                Character.getNumericValue(newGameSize.charAt(0)),
                Character.getNumericValue(newGameSize.charAt(newGameSize.length() - 1)),
                currentGameMode.getMode());
        gameSizeTextView.setText(currentGameMode.getDimensions());

        // Updating Game Size Browse Icons
        leaderboardsManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

        // Updating Preview
        leaderboardsManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());
    }

    private void settingOnClickListenersForSizeButtons() {
        sizeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getVisibility() == View.VISIBLE) {
                    int indexOfCurrentSize = allCurrentGameSizes.indexOf(currentGameMode.getDimensions());
                    indexOfCurrentSize--;
                    handleGameSizeBrowse(indexOfCurrentSize);
                }
            }
        });
        sizeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getVisibility() == View.VISIBLE) {
                    int indexOfCurrentSize = allCurrentGameSizes.indexOf(currentGameMode.getDimensions());
                    indexOfCurrentSize++;
                    handleGameSizeBrowse(indexOfCurrentSize);
                }
            }
        });
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboards, container, false);

        initialise(view);

        settingsOnClickListenerForShowLeaderboardsButton();

        settingOnClickListenersForModeButtons();

        settingOnClickListenersForSizeButtons();

        return view;
    }

    public interface OnLeaderboardsFragmentInteractionListener {
        void onLeaderboardsFragmentInteractionBackClicked();
        void onLeaderboardsFragmentInteractionShowLeaderboard(int leaderboardStringResourceId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnLeaderboardsFragmentInteractionListener) {
            mListener = (OnLeaderboardsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnLeaderboardsFragmentInteractionListener");
        }
        this.parentActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
