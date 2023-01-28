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
import com.nerdcoredevelopment.game2048champsfinal.enums.GameStates;
import com.nerdcoredevelopment.game2048champsfinal.manager.PreGameManager;

import java.util.List;

public class PreGameFragment extends Fragment {
    // Back button in the title text of the fragment
    private AppCompatImageView backButton;

    // Root layout of the fragment
    private ConstraintLayout rootLayout;

    // Interaction listener of this fragment
    private OnPreGameFragmentInteractionListener mListener;

    // Parent Activity of the current fragment
    private Activity parentActivity;

    // Utility class for MainActivity
    private PreGameManager preGameManager;

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
    private LottieAnimationView startGameLottie;
    private AppCompatTextView highScoreTextView;
    private AppCompatImageView modeLeft;
    private AppCompatImageView modeRight;
    private AppCompatImageView sizeLeft;
    private AppCompatImageView sizeRight;
    private AppCompatButton startGameButton;

    public PreGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialise(View layoutView) {
        // Initialising and setting onClick listener for the back button in title
        backButton = layoutView.findViewById(R.id.title_back_pregame_fragment_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onPreGameFragmentInteractionBackClicked();
                }
            }
        });

        // Initialising the root layout
        rootLayout = layoutView.findViewById(R.id.root_layout_pregame_fragment);

        // Initialising preGameManager
        preGameManager = new PreGameManager(this.parentActivity, layoutView);

        // Initialising sharedPreferences
        sharedPreferences = this.parentActivity.getSharedPreferences("com.nerdcoredevelopment.game2048champsfinal", Context.MODE_PRIVATE);

        // The default game mode
        currentGameMode = GameModes.getGameModeEnum(
                sharedPreferences.getInt("gameMatrixColumns", 4),
                sharedPreferences.getInt("gameMatrixRows", 4),
                sharedPreferences.getString("gameMode", "SQUARE"));

        // Initialising allGameModes List and gameModeTextView TextView
        allGameModes = GameModes.getAllGameModes();
        gameModeTextView = layoutView.findViewById(R.id.game_mode_pregame_fragment_text_view);
        gameModeTextView.setText(currentGameMode.getMode());
        preGameManager.updateModeBrowseIcons(currentGameMode.getMode(), allGameModes);

        // Initialising allCurrentGameSizes List and gameSizeTextView TextView
        allCurrentGameSizes = GameModes.getAllGameVariantsOfMode(currentGameMode.getMode());
        gameSizeTextView = layoutView.findViewById(R.id.game_size_pregame_fragment_text_view);
        gameSizeTextView.setText(currentGameMode.getDimensions());
        preGameManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

        gamePreviewSpotLightLottie = layoutView.findViewById(R.id.game_preview_pregame_fragment_spotlight_lottie);
        if (sharedPreferences.getBoolean("toggleRotatingLight", true)) {
            gamePreviewSpotLightLottie.playAnimation();
        } else {
            gamePreviewSpotLightLottie.pauseAnimation();
        }
        gamePreviewImageView = layoutView.findViewById(R.id.game_preview_pregame_fragment_image_view);
        startGameLottie = layoutView.findViewById(R.id.start_game_pregame_fragment_lottie);
        highScoreTextView = layoutView.findViewById(R.id.high_score_pregame_fragment_text_view);
        highScoreTextView.setText(String.valueOf(sharedPreferences.getLong("bestScore" + " " + currentGameMode.getMode()
                + " " + currentGameMode.getDimensions(), 0L)));
        modeLeft = layoutView.findViewById(R.id.game_mode_left_arrow_pregame_fragment_image_view);
        modeRight = layoutView.findViewById(R.id.game_mode_right_arrow_pregame_fragment_image_view);
        sizeLeft = layoutView.findViewById(R.id.game_size_left_arrow_pregame_fragment_image_view);
        sizeRight = layoutView.findViewById(R.id.game_size_right_arrow_pregame_fragment_image_view);
        startGameButton = layoutView.findViewById(R.id.start_game_pregame_fragment_button);
        if (sharedPreferences.getInt("gameStateEnumIndex" + " " + currentGameMode.getMode()
                + " " + currentGameMode.getDimensions(), 0) == GameStates.GAME_ONGOING.ordinal()) {
            startGameButton.setText("RESUME GAME");
        } else {
            startGameButton.setText("START GAME");
        }

        // Updating the preview accordingly
        preGameManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());
    }

    private void settingsOnClickListenerForStartButton() {
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentGameMode.isCanAccess()) {
                    view.setEnabled(false);
                    rootLayout.setEnabled(false);
                    modeLeft.setEnabled(false); modeRight.setEnabled(false);
                    sizeLeft.setEnabled(false); sizeRight.setEnabled(false);

                    gamePreviewSpotLightLottie.setVisibility(View.INVISIBLE);
                    gamePreviewImageView.setVisibility(View.INVISIBLE);
                    startGameLottie.setVisibility(View.VISIBLE);

                    sharedPreferences.edit().putString("gameMode", currentGameMode.getMode()).apply();
                    sharedPreferences.edit().putInt("gameMatrixColumns", currentGameMode.getColumns()).apply();
                    sharedPreferences.edit().putInt("gameMatrixRows", currentGameMode.getRows()).apply();

                    startGameLottie.playAnimation();

                    new CountDownTimer(1500, 1500) {
                        @Override
                        public void onTick(long millisUntilFinished) {}
                        @Override
                        public void onFinish() {
                            view.setEnabled(true);
                            rootLayout.setEnabled(true);
                            modeLeft.setEnabled(true); modeRight.setEnabled(true);
                            sizeLeft.setEnabled(true); sizeRight.setEnabled(true);
                            startGameLottie.pauseAnimation();

                            if (mListener != null) {
                                mListener.onPreGameFragmentInteractionStartGame(currentGameMode.getMode(),
                                        currentGameMode.getColumns(),
                                        currentGameMode.getRows());
                            }
                        }
                    }.start();
                } else {
                    if (mListener != null) {
                        mListener.onPreGameFragmentInteractionShowArrivingFeatureDialog();
                    }
                }
            }
        });
    }

    private void handleGameModeBrowse(int indexOfCurrentMode) {
        // Make changes to the game mode and size both
        String newGameMode = allGameModes.get(indexOfCurrentMode);
        allCurrentGameSizes = GameModes.getAllGameVariantsOfMode(newGameMode);
        currentGameMode = GameModes.getGameModeEnum(
                Character.getNumericValue(allCurrentGameSizes.get(0).charAt(0)),
                Character.getNumericValue(allCurrentGameSizes.get(0)
                        .charAt(allCurrentGameSizes.get(0).length() - 1)), newGameMode);

        // Updating the text views for both mode and size
        gameModeTextView.setText(currentGameMode.getMode());
        gameSizeTextView.setText(currentGameMode.getDimensions());

        // Updating the text view for the high score of the new current game mode
        highScoreTextView.setText(String.valueOf(sharedPreferences.getLong("bestScore" + " " + currentGameMode.getMode()
                + " " + currentGameMode.getDimensions(), 0L)));

        // Updating Game Mode Browse Icons
        preGameManager.updateModeBrowseIcons(currentGameMode.getMode(), allGameModes);

        // Updating Game Size Browse Icons
        preGameManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

        // Updating Preview
        preGameManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());

        // Update the text of the start game button
        if (sharedPreferences.getInt("gameStateEnumIndex" + " " + currentGameMode.getMode()
                + " " + currentGameMode.getDimensions(), 0) == GameStates.GAME_ONGOING.ordinal()) {
            startGameButton.setText("RESUME GAME");
        } else {
            startGameButton.setText("START GAME");
        }
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

        // Updating the text view for the high score of the new current game mode
        highScoreTextView.setText(String.valueOf(sharedPreferences.getLong("bestScore" + " " + currentGameMode.getMode()
                + " " + currentGameMode.getDimensions(), 0L)));

        // Updating Game Size Browse Icons
        preGameManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

        // Updating Preview
        preGameManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());

        // Update the text of the start game button
        if (sharedPreferences.getInt("gameStateEnumIndex" + " " + currentGameMode.getMode()
                + " " + currentGameMode.getDimensions(), 0) == GameStates.GAME_ONGOING.ordinal()) {
            startGameButton.setText("RESUME GAME");
        } else {
            startGameButton.setText("START GAME");
        }
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
        View view = inflater.inflate(R.layout.fragment_pre_game, container, false);

        initialise(view);

        settingsOnClickListenerForStartButton();

        settingOnClickListenersForModeButtons();

        settingOnClickListenersForSizeButtons();

        return view;
    }

    public interface OnPreGameFragmentInteractionListener {
        void onPreGameFragmentInteractionBackClicked();
        void onPreGameFragmentInteractionStartGame(String gameMode, int gameMatrixColumns, int gameMatrixRows);
        void onPreGameFragmentInteractionShowArrivingFeatureDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnPreGameFragmentInteractionListener) {
            mListener = (OnPreGameFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnPreGameFragmentInteractionListener");
        }
        this.parentActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}