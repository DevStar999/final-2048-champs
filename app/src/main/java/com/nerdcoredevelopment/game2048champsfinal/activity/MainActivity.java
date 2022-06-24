package com.nerdcoredevelopment.game2048champsfinal.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.nerdcoredevelopment.game2048champsfinal.R;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.GameExitDialog;
import com.nerdcoredevelopment.game2048champsfinal.enums.GameModes;
import com.nerdcoredevelopment.game2048champsfinal.manager.MainManager;

import java.util.List;

// TODO -> Implement functionality to ask user to rate app on Play Store
// TODO -> Do a limited time glowing animation for get-premium version icon so that the user can take that hint
// TODO -> If possible use Fragments where we can replace them with activities. For e.g. for passing GameModes object
/*
TODO -> Implement Rail Mode for this game. Later in Ads Version of app, this mode can also be unlocked by another
        way where for certain condition which signifies that the user has played the game a lot OR else this game
        mode can only be unlock via purchase of the premium version of the app
*/
/*
TODO -> A limited premium version offer for say only first 1000 premium users and link the countdown to your website
*/
// TODO -> In Settings, give user the option to customise the block cell with different images like rock, skull etc.
// TODO -> When we do release an ads version of the app, give user a free run without ads for the 1st 2-3 days
// TODO -> Send different notifications as the life of the user grows more and more
// TODO -> In Setting the user should be given the option to customise the backdrop of the game layout
// TODO -> Setup Google Play Services for this project (Status - Play Games Services configuration started)
// TODO -> In Settings, allow users to choose from more than 1 swipe speeds
public class MainActivity extends AppCompatActivity {
    // Utility class for MainActivity
    private MainManager mainManager;

    // Using the GameModes enum for storing the game mode selected by the user
    private GameModes currentGameMode;

    // Attributes for determining game mode
    private List<String> allGameModes;
    private AppCompatButton gameModeButton;

    // Attributes for determining game size
    private List<String> allCurrentGameSizes;
    private AppCompatButton gameSizeButton;

    // Game modes customisation UI elements
    private LottieAnimationView gamePreviewSpotLightLottie;
    private AppCompatImageView gamePreviewImageView;
    private LottieAnimationView startGameLottie;
    private AppCompatImageView modeLeft;
    private AppCompatImageView modeRight;
    private AppCompatImageView sizeLeft;
    private AppCompatImageView sizeRight;

    private void initialise() {
        // Initialising MainManager
        mainManager = new MainManager(MainActivity.this);

        // The default game mode
        currentGameMode = GameModes.getGameModeEnum(4, 4, "SQUARE");

        // Initialising allGameModes List and gameModeTextView TextView
        allGameModes = GameModes.getAllGameModes();
        gameModeButton = findViewById(R.id.game_mode_button);
        gameModeButton.setText(currentGameMode.getMode());
        mainManager.updateModeBrowseIcons(currentGameMode.getMode(), allGameModes);

        // Initialising allCurrentGameSizes List and gameSizeTextView TextView
        allCurrentGameSizes = GameModes.getAllGameVariantsOfMode(currentGameMode.getMode());
        gameSizeButton = findViewById(R.id.game_size_button);
        gameSizeButton.setText(currentGameMode.getDimensions());
        mainManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

        gamePreviewSpotLightLottie = findViewById(R.id.game_preview_spotlight_lottie);
        gamePreviewImageView = findViewById(R.id.game_preview_image_view);
        startGameLottie = findViewById(R.id.start_game_lottie);
        modeLeft = findViewById(R.id.game_mode_left_arrow_image_view);
        modeRight = findViewById(R.id.game_mode_right_arrow_image_view);
        sizeLeft = findViewById(R.id.game_size_left_arrow_image_view);
        sizeRight = findViewById(R.id.game_size_right_arrow_image_view);

        // Updating the preview accordingly
        mainManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Following lines of code hide the status bar at the very top of the screen which battery
        indicator, network status other icons etc. Note, this is done before setting the layout with
        the line -> setContentView(R.layout.activity_main);
        */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTheme(R.style.Theme_2048Champs);
        setContentView(R.layout.activity_main);

        // To Disable screen rotation and keep the device in Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // To set the app theme to 'LIGHT' (even if 'DARK' theme is selected, however if user in their
        // settings enables 'DARK' theme for our individual app, then it will override the following line
        // no matter what)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // To hide the navigation bar as default i.e. it will hide by itself if left unused or unattended
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        initialise();
    }

    @Override
    public void onBackPressed() {
        GameExitDialog gameExitDialog = new GameExitDialog(this);
        gameExitDialog.show();
        gameExitDialog.setGameExitDialogListener(new GameExitDialog.GameExitDialogListener() {
            @Override
            public void getResponseOfExitDialog(boolean response) {
                if (response) {
                    MainActivity.super.onBackPressed();
                }
            }
        });
    }

    /**
     * onClick listeners for Icons on the Main Activity's layout are as follows
     */
    public void signInToGooglePlay(View view) {
        Toast.makeText(this, "Google Play Sign In Clicked", Toast.LENGTH_SHORT).show();
    }

    public void upgradeToPremium(View view) {
        Toast.makeText(this, "Upgrade to Premium Icon Clicked", Toast.LENGTH_SHORT).show();
    }

    /*
    TODO -> Show number of moves setting in settings menu to toggle on/off in game layout.
            Setting page reference from NordPass.
    */
    /*
    TODO -> Champs mode tutorial should be unlocked only after the player has at-least once reached the
            Champs mode.
    */
    public void settingsClicked(View view) {
        Toast.makeText(this, "Settings Icon Clicked", Toast.LENGTH_SHORT).show();
    }

    public void achievementsClicked(View view) {
        Toast.makeText(this, "Achievements Icon Clicked", Toast.LENGTH_SHORT).show();
    }

    public void menuClicked(View view) {
        Toast.makeText(this, "Menu Icon Clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * onClick listeners of Game Mode Options Linear Layout are as follows
     */
    // onClick listener for start game button is as follows
    public void startGameButtonPressed(View view) {
        ConstraintLayout rootLayout = findViewById(R.id.root_layout_main_activity);
        view.setEnabled(false);
        rootLayout.setEnabled(false);
        modeLeft.setEnabled(false); modeRight.setEnabled(false);
        sizeLeft.setEnabled(false); sizeRight.setEnabled(false);


        gamePreviewSpotLightLottie.setVisibility(View.INVISIBLE);
        gamePreviewImageView.setVisibility(View.INVISIBLE);
        startGameLottie.setVisibility(View.VISIBLE);

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

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("gameMode", currentGameMode.getMode());
                intent.putExtra("gameMatrixRows", currentGameMode.getRows());
                intent.putExtra("gameMatrixColumns", currentGameMode.getColumns());
                startActivity(intent);
                finish();
            }
        }.start();
    }

    // onClick listener to change game mode option is as follows
    public void gameModeBrowse(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            int indexOfCurrentMode = allGameModes.indexOf(currentGameMode.getMode());
            if (view.getId() == R.id.game_mode_left_arrow_image_view) {
                indexOfCurrentMode--;
            } else {
                indexOfCurrentMode++;
            }

            // Make changes to the game mode and size both
            String newGameMode = allGameModes.get(indexOfCurrentMode);
            allCurrentGameSizes = GameModes.getAllGameVariantsOfMode(newGameMode);
            currentGameMode = GameModes.getGameModeEnum(
                    Character.getNumericValue(allCurrentGameSizes.get(0)
                            .charAt(allCurrentGameSizes.get(0).length() - 1)),
                    Character.getNumericValue(allCurrentGameSizes.get(0).charAt(0)),
                    newGameMode);

            // Updating the text views for both mode and size
            gameModeButton.setText(currentGameMode.getMode());
            gameSizeButton.setText(currentGameMode.getDimensions());

            // Updating Game Mode Browse Icons
            mainManager.updateModeBrowseIcons(currentGameMode.getMode(), allGameModes);

            // Updating Game Size Browse Icons
            mainManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

            // Updating Preview
            mainManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());
        }
    }

    // onClick listener to change game size option is as follows
    public void gameSizeBrowse(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            int indexOfCurrentSize = allCurrentGameSizes.indexOf(currentGameMode.getDimensions());
            if (view.getId() == R.id.game_size_left_arrow_image_view) {
                indexOfCurrentSize--;
            } else {
                indexOfCurrentSize++;
            }

            // Make changes to the game size
            String newGameSize = allCurrentGameSizes.get(indexOfCurrentSize);
            currentGameMode = GameModes.getGameModeEnum(
                    Character.getNumericValue(newGameSize.charAt(newGameSize.length() - 1)),
                    Character.getNumericValue(newGameSize.charAt(0)),
                    currentGameMode.getMode());
            gameSizeButton.setText(currentGameMode.getDimensions());

            // Updating Game Size Browse Icons
            mainManager.updateSizeBrowseIcons(currentGameMode.getDimensions(), allCurrentGameSizes);

            // Updating Preview
            mainManager.updatePreview(currentGameMode.getGamePreviewAssetFileName());
        }
    }
}