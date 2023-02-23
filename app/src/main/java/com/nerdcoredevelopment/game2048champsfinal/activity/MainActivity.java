package com.nerdcoredevelopment.game2048champsfinal.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.AuthenticationResult;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.nerdcoredevelopment.game2048champsfinal.BuildConfig;
import com.nerdcoredevelopment.game2048champsfinal.R;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.ArrivingFeatureDialog;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.ErrorOccurredDialog;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.GameExitDialog;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.UpdateAppStaticAvailableDialog;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.UpdateAppStaticUnavailableDialog;
import com.nerdcoredevelopment.game2048champsfinal.enums.ChangeValueToolAchievements;
import com.nerdcoredevelopment.game2048champsfinal.enums.DestroyAreaToolAchievements;
import com.nerdcoredevelopment.game2048champsfinal.enums.EliminateValueToolAchievements;
import com.nerdcoredevelopment.game2048champsfinal.enums.GameModes;
import com.nerdcoredevelopment.game2048champsfinal.enums.ReviveGameToolAchievements;
import com.nerdcoredevelopment.game2048champsfinal.enums.ScoringAchievements;
import com.nerdcoredevelopment.game2048champsfinal.enums.SmashTileToolAchievements;
import com.nerdcoredevelopment.game2048champsfinal.enums.SwapTilesToolAchievements;
import com.nerdcoredevelopment.game2048champsfinal.enums.TileUnlockAchievements;
import com.nerdcoredevelopment.game2048champsfinal.enums.UndoToolAchievements;
import com.nerdcoredevelopment.game2048champsfinal.fragments.BlockDesignFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.LeaderboardsFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.LogoLottieFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.NavigationFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.PreGameFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.SettingsFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.ShopFragment;

import java.util.ArrayList;
import java.util.List;

/* TODO -> Add a 'Share' button for sharing app download link & 'Promote' button for sharing promo
           code of possibly a premium product/scheme together as one group having these 2 buttons
           in the SettingsFragment
*/
/* TODO -> Multiple quick dialog OR activity open clicks cause the dialog OR activity to open multiple times, we have to
           find a way to avoid that. Basically wherever a new page is opening there we need to stop this from happening
*/
/* TODO -> When it will be time to implement App Open Ad, change logo transition time to 3 seconds and splash screen time
           to 4 seconds
*/
// TODO -> The width of the buttons in ShopFragment should be able to fit the text of the prices comfortably
/* TODO -> Implement a combo bar which fills up in 20 or 30 or 50 etc. steps, where each step is added on completion of a
           move in which merge occurs at 2 or more places i.e. a multi-merge move. When the bar fills up we reward the user
           with coins. As the progress bar fills up change it's color from red/orange to green or something like that, it
           will look cool and engaging
*/
/* TODO -> A list of tasks that need to be done w.r.t. the Review feature in an involuntary Pop-up manner, is as follows:
           (1) Decide if we want to implement In-App Review feature or keep the normal review feature
           (2) Decide the beginning DND (Do Not Disturb) period before which it should not be shown
           (3) Decide the freq. and place (for e.g. the homepage of the app etc.) where it should be shown
           [Note - As of now, every thing for implementing this feature with a static button in the SettingsFragment has
           been completed (as per the life-cycle of Review feature photo in Mobile photos in the OnePlus phone)]
*/
/* TODO -> A list of tasks that need to be done w.r.t. the Update App feature in an involuntary Pop-up manner, is as follows:
           (1) Decide the place (for e.g. the homepage of the app etc.) and freq. with which it should be shown
           (2) Condition check to allow the Update App Pop Up Dialog to be triggered
           (3) Code for functionality of 'Remind Later' and 'Skip Version' buttons is pending
           (4) Handle the error condition with a dialog or something
           [Note - As of now, every thing for implementing this feature with a static button in the SettingsFragment has
           been completed (as per the life-cycle of Review feature photo in Mobile photos in the OnePlus phone)]
*/
/* TODO -> Shift all the tools transition & layout related code into a separate fragment for two of the places which are as
           follows:
           (1) GameActivity
           (2) GameOverDialog
*/
/* TODO -> Replace the LottieAnimationView for rotating light everywhere with AppCompatImageView and rotate it at places it
           is required to be rotated. This should come in handy for when we implement the 'Change Theme' feature. The places
           this can be done are as follows:
           (1) Homepage of the app
           (2) ShopFragment
           (3) PregameFragment
*/
/* TODO -> All the TODOs related to GPGS are as follows:
           (1) Keep adding tasks from the link -> https://developer.android.com/games/pgs/quality, to this to do list for
           GPGS features along with the progress of implementation of GPGS features
           (2) The features which are as follows are to be implemented in the 2nd round of implementation are as follows:
           [i] Friends [ii] Saved Games
           [iii] Events [iv] Player Stats
           (3) We need to instruct the user to share their gameplay activity to everyone in the Google Play Games app settings
           so that the score of the user can be submitted in the public version of a leaderboard & same data can be fetched
           and shown to the user in the app. Also instruct them how to make friends on Google Play Games, so that social
           version of a leaderboard can also be shown to the player
           (4) Take a look at the notes in 'Squares Addition' app over the MainActivity.java file for tasks that need to be
           done while implementing GPGS
           (5) After all the features of GPGS are done, do a round of error handling for all GPGS features. For this we can
           verify all the GPGS features are working correctly and proper error handling is done for the following -
           [i] When user is not signed-in
           [ii] No internet connection or any combination of the above two
           [iii] Any other faulty scenario
           (6) Do take a look at the Firebase consumption for the user of GPGS features in the project
           (7) Check if the implementation of the following document is required (Enable Server-Side Access) ->
           https://developers.google.com/games/services/android/offline-access
           If not required, then it would save us time. So, do check if it is required to be implemented or not
           (8) Once game is published for Play Console and for Play Games services with all the implemented GPGS features,
           then remember to turn on things like Anti-piracy, Tamper-protection etc.
           (9) Check out this article to limit API usage and quota as follows ->
           https://developers.google.com/games/services/console/configuring#adjusting_api_usage_quotas
           (10) We should create like a 'Fresh Data' button wherever it we may feel that it is required like before e.g.
                showing the leaderboards, achievements, loading some data etc. After this button is clicked, for the
                immediate next clicks for showing leaderboards, achievements etc. we should call the methods which fetch the
                fresh data from the servers instead of the methods which we usually call which may show us cached/stale data
                sometimes.
           (11) Check out the TODOs of 'Squares Addition' app, specifically the one which talks about the reason to explore
           the 'Saved Games' feature of GPGS
           (12) Be sure to make the use of Player ID in the code, this may be required for saving data particular to them
           [Remember, here we are talking about Player ID (ID that developers can use) not Player Username on GPGS (This is
           shown to the user on Google Play Games and it can be edited by the user as well)]
           (14) Whenever we are waiting for GPGS features to load or take effect, we can show something like a loading screen
           etc. in the meanwhile
           (15) Create an achievement on purchase of (a) 25K coins package (b) 50K coins package (c) 100K coins package
           (d) Premium Version (e) Collecting 1 million coins (Incremental Achievement)
*/
/* TODO -> 'Revive Game' tool only available in GameOverDialog. Cost of use - 1000 coins. Tool Effect - Eliminates all the
            game tiles except the tiles with the highest value, collects and starts placing them from the bottom left corner
            towards right, if the last row fills up then in the row above from left to right and so on, each row from top to
            bottom.
*/
// TODO -> In the later stages of development, think over the colors for the different buttons in all the dialogs
public class MainActivity extends AppCompatActivity implements
        NavigationFragment.OnNavigationFragmentInteractionListener,
        PreGameFragment.OnPreGameFragmentInteractionListener,
        LeaderboardsFragment.OnLeaderboardsFragmentInteractionListener,
        ShopFragment.OnShopFragmentInteractionListener,
        SettingsFragment.OnSettingsFragmentInteractionListener,
        BlockDesignFragment.OnBlockDesignFragmentInteractionListener {
    private SharedPreferences sharedPreferences;

    // Attributes for Google Play Games Services (GPGS) features
    private boolean isUserSignedIn;
    private GamesSignInClient gamesSignInClient;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private AchievementsClient achievementsClient;
    public static final int RC_LEADERBOARD_UI = 9004;
    private LeaderboardsClient leaderboardsClient;

    // Attributes required for In app updates feature
    public static final int UPDATE_REQUEST_CODE = 100;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;

    private void initialise() {
        isUserSignedIn = true;
        sharedPreferences = getSharedPreferences("com.nerdcoredevelopment.game2048champsfinal", Context.MODE_PRIVATE);
        gamesSignInClient = PlayGames.getGamesSignInClient(MainActivity.this);
        achievementsClient = PlayGames.getAchievementsClient(MainActivity.this);
        leaderboardsClient = PlayGames.getLeaderboardsClient(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Following lines of code hide the status bar at the very top of the screen which battery
        indicator, network status other icons etc. Note, this is done before setting the layout with
        the line -> setContentView(R.layout.activity_main);
        */
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // To hide the navigation bar as default i.e. it will hide by itself if left unused or unattended
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        initialise();

        LogoLottieFragment logoLottieFragment = new LogoLottieFragment();
        NavigationFragment navigationFragment = new NavigationFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.logo_lottie_main_activity_fragment_container, logoLottieFragment, "LOGO_LOTTIE_FRAGMENT")
                .replace(R.id.navigation_main_activity_fragment_container, navigationFragment, "NAVIGATION_FRAGMENT")
                .commit();

        PlayGamesSdk.initialize(this);

        new CountDownTimer(500, 10000) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                verifyPlayGamesSignIn(false);
            }
        }.start();

        setupInAppUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Persisting the screen settings even if the user leaves the app mid use for when he/she
           returns to use the app again
        */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            // Back button was pressed from activity, do nothing as we want to eliminate this option
            // to exit from the homepage
        } else {
            // Back button was pressed from fragment
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                // If the update is cancelled or fails, we can ignore this if we are implementing a 'Flexible Update'
            }
        }
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) { return false; }
        /* NetworkInfo is deprecated in API 29 so we have to check separately for higher API Levels */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Network network = cm.getActiveNetwork();
            if (network == null) { return false; }
            NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(network);
            if (networkCapabilities == null) { return false; }
            boolean isInternetSuspended = !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED);
            return (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    && !isInternetSuspended);
        } else {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }
    }

    private void setupInAppUpdate() {
        installStateUpdatedListener = installState -> {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            }
        };
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(installStateUpdatedListener);
    }

    private void popupSnackbarForCompleteUpdate() { // Displays the snackbar notification and call to action.
        Snackbar snackbar = Snackbar.make(findViewById(R.id.root_layout_main_activity),
                "An update has been downloaded", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("INSTALL", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getColor(R.color.white));
        snackbar.show();
    }

    private void launchInAppUpdateFlowForStaticButton() {
        if (!isInternetConnected()) {
            /* Using a feel right as of now. Since, the user may repeatedly press this button it isn't wise to show a dialog
               for prompting the user to check their internet connection
             */
            Toast.makeText(MainActivity.this, "Network connection failed. Please check " +
                    "Internet connectivity", Toast.LENGTH_LONG).show();
            return;
        }

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                int oldVersion = BuildConfig.VERSION_CODE;
                int newVersion = appUpdateInfo.availableVersionCode();
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        UpdateAppStaticAvailableDialog updateAppStaticAvailableDialog =
                                new UpdateAppStaticAvailableDialog(MainActivity.this);
                        updateAppStaticAvailableDialog.setUpdateAppStaticAvailableDialogListener(response -> {
                            if (response) {
                                try {
                                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE,
                                            MainActivity.this, UPDATE_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        updateAppStaticAvailableDialog.show();
                    }
                } else if (oldVersion == newVersion) {
                    // Note: Even in the case when there was no internet we get UpdateAvailability.UPDATE_NOT_AVAILABLE.
                    // So we made that check earlier
                    new UpdateAppStaticUnavailableDialog(MainActivity.this).show();
                } else {
                    // This is the final error code branch
                    new ErrorOccurredDialog(MainActivity.this, "Oops! Something went wrong").show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new ErrorOccurredDialog(MainActivity.this, "Oops! Something went wrong").show();
            }
        });
    }

    private void checkForNewSignedInPlayer() {
        PlayGames.getPlayersClient(MainActivity.this).getCurrentPlayer()
                .addOnCompleteListener(new OnCompleteListener<Player>() {
                    @Override
                    public void onComplete(@NonNull Task<Player> task) {
                        String playerId = task.getResult().getPlayerId();
                        String previousSignedInPlayerId = sharedPreferences.getString("previousSignedInPlayerId",
                                "<Default Signed In Player>");
                        String currentSignedInPlayerId = sharedPreferences.getString("currentSignedInPlayerId",
                                previousSignedInPlayerId); // Expected currently signed in player
                        if (currentSignedInPlayerId.equals(playerId)) { // The previous player has
                            // All is good here
                        } else {
                            sharedPreferences.edit().putString("previousSignedInPlayerId",
                                    currentSignedInPlayerId).apply();
                            sharedPreferences.edit().putString("currentSignedInPlayerId", playerId).apply();
                            updateAchievementsProgress(0);
                            updateLeaderboardsProgress(0);
                        }
                    }
                });
    }

    private void verifyPlayGamesSignIn(boolean isSignInAttemptManual) {
        gamesSignInClient.isAuthenticated().addOnCompleteListener(new OnCompleteListener<AuthenticationResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthenticationResult> isAuthenticatedTask) {
                boolean isAuthenticated = (isAuthenticatedTask.isSuccessful()
                        && isAuthenticatedTask.getResult().isAuthenticated());
                if (isAuthenticated) {
                    isUserSignedIn = true;
                    hideSignInButtonThroughoutApp();
                    checkForNewSignedInPlayer();
                } else {
                    isUserSignedIn = false;
                    revealSignInButtonThroughoutApp();
                    if (isSignInAttemptManual) {
                        if (isInternetConnected()) {
                            /* TODO -> Replace this toast with something better like a dialog etc. and more descriptive
                                       (Sign In feature)
                            */
                            Toast.makeText(MainActivity.this, "Download the 'Google Play Games' app and " +
                                    "select an account to play this game", Toast.LENGTH_LONG).show();
                        } else { // Internet is NOT connected
                            Toast.makeText(MainActivity.this, "Network connection failed. Please check " +
                                    "Internet connectivity", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    private void verifyPlayGamesSignInPreAchievementsDisplay() {
        gamesSignInClient.isAuthenticated().addOnCompleteListener(new OnCompleteListener<AuthenticationResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthenticationResult> isAuthenticatedTask) {
                boolean isAuthenticated = (isAuthenticatedTask.isSuccessful()
                        && isAuthenticatedTask.getResult().isAuthenticated());
                if (isAuthenticated) {
                    isUserSignedIn = true;
                    hideSignInButtonThroughoutApp();
                    checkForNewSignedInPlayer();
                } else {
                    isUserSignedIn = false;
                    revealSignInButtonThroughoutApp();
                    if (isInternetConnected()) {
                        /* TODO -> Replace this toast with something better like a dialog etc. and more descriptive
                                   (Achievements feature)
                        */
                        Toast.makeText(MainActivity.this, "Cannot access this feature without being Signed In",
                                Toast.LENGTH_LONG).show();
                    } else { // Internet is NOT connected
                        Toast.makeText(MainActivity.this, "Network connection failed. Please check " +
                                "Internet connectivity", Toast.LENGTH_LONG).show();
                    }
                }

                if (isUserSignedIn) {
                    showAchievementsDisplayPostVerification();
                }
            }
        });
    }

    private void verifyPlayGamesSignInPreLeaderboardsDisplay() {
        gamesSignInClient.isAuthenticated().addOnCompleteListener(new OnCompleteListener<AuthenticationResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthenticationResult> isAuthenticatedTask) {
                boolean isAuthenticated = (isAuthenticatedTask.isSuccessful()
                        && isAuthenticatedTask.getResult().isAuthenticated());
                if (isAuthenticated) {
                    isUserSignedIn = true;
                    hideSignInButtonThroughoutApp();
                    checkForNewSignedInPlayer();
                } else {
                    isUserSignedIn = false;
                    revealSignInButtonThroughoutApp();
                    if (isInternetConnected()) {
                        /* TODO -> Replace this toast with something better like a dialog etc. and more descriptive
                                   (Leaderboards feature)
                        */
                        Toast.makeText(MainActivity.this, "Ensure you are Signed In and 'Everyone can see your " +
                                "game activity' in 'Google Play Games' app settings", Toast.LENGTH_LONG).show();
                    } else { // Internet is NOT connected
                        Toast.makeText(MainActivity.this, "Network connection failed. Please check " +
                                "Internet connectivity", Toast.LENGTH_LONG).show();
                    }
                }

                if (isUserSignedIn) {
                    showLeaderboardsDisplayPostVerification();
                }
            }
        });
    }

    private void hideSignInButtonThroughoutApp() {
        List<Fragment> fragments = new ArrayList<>(getSupportFragmentManager().getFragments());
        for (int index = 0; index < fragments.size(); index++) {
            Fragment currentFragment = fragments.get(index);
            if (currentFragment != null && currentFragment.getTag() != null
                    && !currentFragment.getTag().isEmpty()) {
                if (currentFragment.getTag().equals("NAVIGATION_FRAGMENT")) {
                    ((NavigationFragment) currentFragment).hideSignInButton();
                } else if (currentFragment.getTag().equals("SETTINGS_FRAGMENT")) {
                    ((SettingsFragment) currentFragment).hideSignInButton();
                }
            }
        }
    }

    private void revealSignInButtonThroughoutApp() {
        List<Fragment> fragments = new ArrayList<>(getSupportFragmentManager().getFragments());
        for (int index = 0; index < fragments.size(); index++) {
            Fragment currentFragment = fragments.get(index);
            if (currentFragment != null && currentFragment.getTag() != null
                    && !currentFragment.getTag().isEmpty()) {
                if (currentFragment.getTag().equals("NAVIGATION_FRAGMENT")) {
                    ((NavigationFragment) currentFragment).revealSignInButton();
                } else if (currentFragment.getTag().equals("SETTINGS_FRAGMENT")) {
                    ((SettingsFragment) currentFragment).revealSignInButton();
                }
            }
        }
    }

    private void updateAchievementsProgress(int retryAttempt) {
        if (retryAttempt >= 3) {
            return;
        }

        achievementsClient.load(true).addOnSuccessListener(new OnSuccessListener<AnnotatedData<AchievementBuffer>>() {
            @Override
            public void onSuccess(AnnotatedData<AchievementBuffer> achievementBufferAnnotatedData) {
                AchievementBuffer achievementBuffer = achievementBufferAnnotatedData.get();
                if (achievementBuffer != null) {
                    int count = achievementBuffer.getCount();
                    for (int index = 0; index < count; index++) {
                        Achievement achievement = achievementBuffer.get(index);
                        String achievementId = achievement.getAchievementId();
                        // Update the progress related to ScoringAchievements
                        for (int scoringAchievementIndex = 0; scoringAchievementIndex < ScoringAchievements.values().length;
                             scoringAchievementIndex++) {
                            ScoringAchievements currentScoringAchievement =
                                    ScoringAchievements.values()[scoringAchievementIndex];
                            if (achievementId.equals(getString(currentScoringAchievement.getAchievementStringResourceId()))) {
                                if (achievement.getState() == Achievement.STATE_UNLOCKED) {
                                    sharedPreferences.edit().putBoolean("scoringAchievement" + "_" +
                                            getString(currentScoringAchievement.getAchievementStringResourceId()), true).apply();
                                } else {
                                    sharedPreferences.edit().putBoolean("scoringAchievement" + "_" +
                                            getString(currentScoringAchievement.getAchievementStringResourceId()), false).apply();
                                }
                                break;
                            }
                        }

                        // Update the progress related to TileUnlockAchievements
                        for (int tileUnlockAchievementIndex = 0; tileUnlockAchievementIndex < TileUnlockAchievements.values().length;
                             tileUnlockAchievementIndex++) {
                            TileUnlockAchievements currentTileUnlockAchievement =
                                    TileUnlockAchievements.values()[tileUnlockAchievementIndex];
                            if (achievementId.equals(getString(currentTileUnlockAchievement.getAchievementStringResourceId()))) {
                                if (achievement.getState() == Achievement.STATE_UNLOCKED) {
                                    sharedPreferences.edit().putBoolean("tileUnlockAchievement" + "_" +
                                            getString(currentTileUnlockAchievement.getAchievementStringResourceId()), true).apply();
                                } else {
                                    sharedPreferences.edit().putBoolean("tileUnlockAchievement" + "_" +
                                            getString(currentTileUnlockAchievement.getAchievementStringResourceId()), false).apply();
                                }
                                break;
                            }
                        }

                        // Update the progress related to UndoToolAchievements
                        for (int undoToolsAchievementIndex = 0; undoToolsAchievementIndex < UndoToolAchievements.values().length;
                             undoToolsAchievementIndex++) {
                            UndoToolAchievements currentUndoToolAchievement =
                                    UndoToolAchievements.values()[undoToolsAchievementIndex];
                            if (achievementId.equals(getString(currentUndoToolAchievement.getAchievementStringResourceId()))) {
                                sharedPreferences.edit().putInt("undoToolAchievement" + "_" +
                                                getString(currentUndoToolAchievement.getAchievementStringResourceId()),
                                        achievement.getState()).apply();
                                break;
                            }
                        }

                        // Update the progress related to SmashTileToolAchievements
                        for (int smashTileToolAchievementIndex = 0; smashTileToolAchievementIndex < SmashTileToolAchievements
                                .values().length; smashTileToolAchievementIndex++) {
                            SmashTileToolAchievements currentSmashTileToolAchievement =
                                    SmashTileToolAchievements.values()[smashTileToolAchievementIndex];
                            if (achievementId.equals(getString(currentSmashTileToolAchievement.getAchievementStringResourceId()))) {
                                sharedPreferences.edit().putInt("smashTileToolAchievement" + "_" +
                                                getString(currentSmashTileToolAchievement.getAchievementStringResourceId()),
                                        achievement.getState()).apply();
                                break;
                            }
                        }

                        // Update the progress related to SwapTilesToolAchievements
                        for (int swapTilesToolAchievementIndex = 0; swapTilesToolAchievementIndex < SwapTilesToolAchievements
                                .values().length; swapTilesToolAchievementIndex++) {
                            SwapTilesToolAchievements currentSwapTilesToolAchievement =
                                    SwapTilesToolAchievements.values()[swapTilesToolAchievementIndex];
                            if (achievementId.equals(getString(currentSwapTilesToolAchievement.getAchievementStringResourceId()))) {
                                sharedPreferences.edit().putInt("swapTilesToolAchievement" + "_" +
                                                getString(currentSwapTilesToolAchievement.getAchievementStringResourceId()),
                                        achievement.getState()).apply();
                                break;
                            }
                        }

                        // Update the progress related to ChangeValueToolAchievements
                        for (int changeValueToolAchievementIndex = 0; changeValueToolAchievementIndex < ChangeValueToolAchievements
                                .values().length; changeValueToolAchievementIndex++) {
                            ChangeValueToolAchievements currentChangeValueToolAchievement =
                                    ChangeValueToolAchievements.values()[changeValueToolAchievementIndex];
                            if (achievementId.equals(getString(currentChangeValueToolAchievement.getAchievementStringResourceId()))) {
                                sharedPreferences.edit().putInt("changeValueToolAchievement" + "_" +
                                                getString(currentChangeValueToolAchievement.getAchievementStringResourceId()),
                                        achievement.getState()).apply();
                                break;
                            }
                        }

                        // Update the progress related to EliminateValueToolAchievements
                        for (int eliminateValueToolAchievementIndex = 0; eliminateValueToolAchievementIndex <
                                EliminateValueToolAchievements.values().length; eliminateValueToolAchievementIndex++) {
                            EliminateValueToolAchievements currentEliminateValueToolAchievement =
                                    EliminateValueToolAchievements.values()[eliminateValueToolAchievementIndex];
                            if (achievementId.equals(getString(currentEliminateValueToolAchievement.getAchievementStringResourceId()))) {
                                sharedPreferences.edit().putInt("eliminateValueToolAchievement" + "_" +
                                                getString(currentEliminateValueToolAchievement.getAchievementStringResourceId()),
                                        achievement.getState()).apply();
                                break;
                            }
                        }

                        // Update the progress related to DestroyAreaToolAchievements
                        for (int destroyAreaToolAchievementIndex = 0; destroyAreaToolAchievementIndex <
                                DestroyAreaToolAchievements.values().length; destroyAreaToolAchievementIndex++) {
                            DestroyAreaToolAchievements currentDestroyAreaToolAchievement =
                                    DestroyAreaToolAchievements.values()[destroyAreaToolAchievementIndex];
                            if (achievementId.equals(getString(currentDestroyAreaToolAchievement.getAchievementStringResourceId()))) {
                                sharedPreferences.edit().putInt("destroyAreaToolAchievement" + "_" +
                                                getString(currentDestroyAreaToolAchievement.getAchievementStringResourceId()),
                                        achievement.getState()).apply();
                                break;
                            }
                        }

                        // Update the progress related to ReviveGameToolAchievements
                        for (int reviveGameToolAchievementIndex = 0; reviveGameToolAchievementIndex <
                                ReviveGameToolAchievements.values().length; reviveGameToolAchievementIndex++) {
                            ReviveGameToolAchievements currentReviveGameToolAchievement =
                                    ReviveGameToolAchievements.values()[reviveGameToolAchievementIndex];
                            if (achievementId.equals(getString(currentReviveGameToolAchievement.getAchievementStringResourceId()))) {
                                sharedPreferences.edit().putInt("reviveGameToolAchievement" + "_" +
                                                getString(currentReviveGameToolAchievement.getAchievementStringResourceId()),
                                        achievement.getState()).apply();
                                break;
                            }
                        }
                    }
                }

                if (achievementBuffer != null) {
                    achievementBuffer.release();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                updateAchievementsProgress(retryAttempt + 1);
            }
        });
    }

    private void updateLeaderboardsProgress(int retryAttempt) {
        if (retryAttempt >= 3) {
            return;
        }

        leaderboardsClient.loadLeaderboardMetadata(true)
                .addOnSuccessListener(new OnSuccessListener<AnnotatedData<LeaderboardBuffer>>() {
                    @Override
                    public void onSuccess(AnnotatedData<LeaderboardBuffer> leaderboardBufferAnnotatedData) {
                        LeaderboardBuffer leaderboardBuffer = leaderboardBufferAnnotatedData.get();
                        if (leaderboardBuffer != null) {
                            int count = leaderboardBuffer.getCount();
                            for (int index = 0; index < count; index++) {
                                Leaderboard leaderboard = leaderboardBuffer.get(index);
                                String leaderboardId = leaderboard.getLeaderboardId();
                                // Update progress related to the best scores in various game modes
                                for (int currentGameModeIndex = 0; currentGameModeIndex < GameModes.values().length;
                                     currentGameModeIndex++) {
                                    GameModes currentGameMode = GameModes.values()[currentGameModeIndex];
                                    if (leaderboardId.equals(getString(currentGameMode.getLeaderboardStringResourceId()))) {
                                        List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                        long leaderboardBestScore = Long.MIN_VALUE;
                                        long gameModeSavedBestScore = sharedPreferences.getLong("bestScore" + " " +
                                                currentGameMode.getMode() + " " + currentGameMode.getDimensions(), 0L);
                                        for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                            LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                            long currentLeaderboardBestScore = currentVariant.getRawPlayerScore();
                                            leaderboardBestScore = Math.max(leaderboardBestScore, currentLeaderboardBestScore);
                                        }
                                        if (leaderboardBestScore < gameModeSavedBestScore) {
                                            leaderboardsClient.submitScore(leaderboardId, gameModeSavedBestScore);
                                        } else {
                                            sharedPreferences.edit().putLong("bestScore" + " " + currentGameMode.getMode()
                                                    + " " + currentGameMode.getDimensions(), leaderboardBestScore).apply();
                                        }
                                    }
                                }

                                // Updating the progress related to the most number of coins saved
                                if (leaderboardId.equals(getString(R.string.leaderboard_coins_leaderboard))) {
                                    List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                    int savedMostCoins = sharedPreferences.getInt("mostCoins", 3000);
                                    int leaderboardMostCoins = Integer.MIN_VALUE;
                                    for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                        LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                        int currentLeaderboardMostCoins = (int) currentVariant.getRawPlayerScore();
                                        leaderboardMostCoins = Math.max(leaderboardMostCoins, currentLeaderboardMostCoins);
                                    }
                                    if (leaderboardMostCoins < savedMostCoins) {
                                        leaderboardsClient.submitScore(leaderboardId, savedMostCoins);
                                    } else {
                                        sharedPreferences.edit().putInt("mostCoins", leaderboardMostCoins).apply();
                                    }
                                }

                                // Updating the progress related to the use count of 'Undo' tool
                                if (leaderboardId.equals(getString(R.string.leaderboard_undo_tool_masters))) {
                                    List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                    int savedUndoToolUseCountSubmitted =
                                            sharedPreferences.getInt("undoToolUseCountSubmitted", 0);
                                    int savedUndoToolCurrentUseCount =
                                            sharedPreferences.getInt("undoToolCurrentUseCount", savedUndoToolUseCountSubmitted);
                                    int leaderboardUndoToolUseCount = Integer.MIN_VALUE;
                                    for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                        LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                        int currentLeaderboardUndoToolUseCount = (int) currentVariant.getRawPlayerScore();
                                        leaderboardUndoToolUseCount = Math.max(leaderboardUndoToolUseCount, currentLeaderboardUndoToolUseCount);
                                    }
                                    // (1) If the score was never submitted, then value of 'leaderboardUndoToolUseCount' will be -1.
                                    // So we first submit the score to the leaderboard
                                    if (leaderboardUndoToolUseCount < 0) {
                                        leaderboardUndoToolUseCount = 0;
                                        leaderboardsClient.submitScore(leaderboardId, leaderboardUndoToolUseCount);
                                    }

                                    // (2) Always update 'undoToolUseCountSubmitted' in saved data
                                    savedUndoToolUseCountSubmitted = leaderboardUndoToolUseCount;
                                    sharedPreferences.edit().putInt("undoToolUseCountSubmitted", savedUndoToolUseCountSubmitted).apply();

                                    // (3) Based on value of 'undoToolUseCountSubmitted' save the value of 'undoToolCurrentUseCount'
                                    if (savedUndoToolCurrentUseCount >= savedUndoToolUseCountSubmitted
                                            && savedUndoToolCurrentUseCount < savedUndoToolUseCountSubmitted + 10) {
                                    } else {
                                        savedUndoToolCurrentUseCount = savedUndoToolUseCountSubmitted;
                                    }
                                    sharedPreferences.edit().putInt("undoToolCurrentUseCount", savedUndoToolCurrentUseCount).apply();

                                    // (4) Verify if the state of the achievements is in accordance with the above 2 values
                                    List<Integer> levelWiseExpectedState = new ArrayList<>() {{
                                        add(Achievement.STATE_REVEALED); add(Achievement.STATE_HIDDEN); add(Achievement.STATE_HIDDEN);
                                    }};
                                    if (savedUndoToolUseCountSubmitted >= 100 && savedUndoToolUseCountSubmitted < 250) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_REVEALED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_HIDDEN);
                                    } else if (savedUndoToolUseCountSubmitted >= 250 && savedUndoToolUseCountSubmitted < 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_REVEALED);
                                    } else if (savedUndoToolUseCountSubmitted >= 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_UNLOCKED);
                                    }

                                    for (int undoToolAchievementsIndex = 0; undoToolAchievementsIndex <
                                            UndoToolAchievements.values().length; undoToolAchievementsIndex++) {
                                        UndoToolAchievements currentUndoToolAchievement =
                                                UndoToolAchievements.values()[undoToolAchievementsIndex];
                                        int currentStateOfAchievement = sharedPreferences.getInt("undoToolAchievement" + "_" +
                                                        getString(currentUndoToolAchievement.getAchievementStringResourceId()),
                                                currentUndoToolAchievement.getInitialAchievementState());

                                        if (levelWiseExpectedState.get(undoToolAchievementsIndex) == currentStateOfAchievement) {
                                            // All good here
                                        } else {
                                            if (levelWiseExpectedState.get(undoToolAchievementsIndex) == Achievement.STATE_HIDDEN) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, nothing we can do
                                            } else if (levelWiseExpectedState.get(undoToolAchievementsIndex) == Achievement.STATE_REVEALED) {
                                                // Then, currentStateOfAchievement is either 'STATE_HIDDEN' or 'STATE_UNLOCKED'.
                                                // if currentStateOfAchievement is 'STATE_HIDDEN' we can do the following
                                                if (currentStateOfAchievement == Achievement.STATE_HIDDEN) {
                                                    currentStateOfAchievement = Achievement.STATE_REVEALED;
                                                    achievementsClient.reveal(getString(currentUndoToolAchievement.getAchievementStringResourceId()));
                                                }
                                            } else if (levelWiseExpectedState.get(undoToolAchievementsIndex) == Achievement.STATE_UNLOCKED) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, we can unlock the achievement
                                                currentStateOfAchievement = Achievement.STATE_UNLOCKED;
                                                achievementsClient.unlock(getString(currentUndoToolAchievement.getAchievementStringResourceId()));
                                            }
                                        }

                                        sharedPreferences.edit().putInt("undoToolAchievement" + "_" +
                                                        getString(currentUndoToolAchievement.getAchievementStringResourceId()),
                                                currentStateOfAchievement).apply();
                                    }
                                }

                                // Updating the progress related to the use count of 'Smash Tile' tool
                                if (leaderboardId.equals(getString(R.string.leaderboard_smash_tile_tool_masters))) {
                                    List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                    int savedSmashTileToolUseCountSubmitted =
                                            sharedPreferences.getInt("smashTileToolUseCountSubmitted", 0);
                                    int savedSmashTileToolCurrentUseCount =
                                            sharedPreferences.getInt("smashTileToolCurrentUseCount", savedSmashTileToolUseCountSubmitted);
                                    int leaderboardSmashTileToolUseCount = Integer.MIN_VALUE;
                                    for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                        LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                        int currentLeaderboardSmashTileToolUseCount = (int) currentVariant.getRawPlayerScore();
                                        leaderboardSmashTileToolUseCount = Math.max(leaderboardSmashTileToolUseCount, currentLeaderboardSmashTileToolUseCount);
                                    }
                                    // (1) If the score was never submitted, then value of 'leaderboardSmashTileToolUseCount' will be -1.
                                    // So we first submit the score to the leaderboard
                                    if (leaderboardSmashTileToolUseCount < 0) {
                                        leaderboardSmashTileToolUseCount = 0;
                                        leaderboardsClient.submitScore(leaderboardId, leaderboardSmashTileToolUseCount);
                                    }

                                    // (2) Always update 'smashTileToolUseCountSubmitted' in saved data
                                    savedSmashTileToolUseCountSubmitted = leaderboardSmashTileToolUseCount;
                                    sharedPreferences.edit().putInt("smashTileToolUseCountSubmitted", savedSmashTileToolUseCountSubmitted).apply();

                                    // (3) Based on value of 'smashTileToolUseCountSubmitted' save the value of 'smashTileToolCurrentUseCount'
                                    if (savedSmashTileToolCurrentUseCount >= savedSmashTileToolUseCountSubmitted
                                            && savedSmashTileToolCurrentUseCount < savedSmashTileToolUseCountSubmitted + 10) {
                                    } else {
                                        savedSmashTileToolCurrentUseCount = savedSmashTileToolUseCountSubmitted;
                                    }
                                    sharedPreferences.edit().putInt("smashTileToolCurrentUseCount", savedSmashTileToolCurrentUseCount).apply();

                                    // (4) Verify if the state of the achievements is in accordance with the above 2 values
                                    List<Integer> levelWiseExpectedState = new ArrayList<>() {{
                                        add(Achievement.STATE_REVEALED); add(Achievement.STATE_HIDDEN); add(Achievement.STATE_HIDDEN);
                                    }};
                                    if (savedSmashTileToolUseCountSubmitted >= 100 && savedSmashTileToolUseCountSubmitted < 250) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_REVEALED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_HIDDEN);
                                    } else if (savedSmashTileToolUseCountSubmitted >= 250 && savedSmashTileToolUseCountSubmitted < 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_REVEALED);
                                    } else if (savedSmashTileToolUseCountSubmitted >= 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_UNLOCKED);
                                    }

                                    for (int smashTileToolAchievementsIndex = 0; smashTileToolAchievementsIndex <
                                            SmashTileToolAchievements.values().length; smashTileToolAchievementsIndex++) {
                                        SmashTileToolAchievements currentSmashTileToolAchievement =
                                                SmashTileToolAchievements.values()[smashTileToolAchievementsIndex];
                                        int currentStateOfAchievement = sharedPreferences.getInt("smashTileToolAchievement" + "_" +
                                                        getString(currentSmashTileToolAchievement.getAchievementStringResourceId()),
                                                currentSmashTileToolAchievement.getInitialAchievementState());

                                        if (levelWiseExpectedState.get(smashTileToolAchievementsIndex) == currentStateOfAchievement) {
                                            // All good here
                                        } else {
                                            if (levelWiseExpectedState.get(smashTileToolAchievementsIndex) == Achievement.STATE_HIDDEN) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, nothing we can do
                                            } else if (levelWiseExpectedState.get(smashTileToolAchievementsIndex) == Achievement.STATE_REVEALED) {
                                                // Then, currentStateOfAchievement is either 'STATE_HIDDEN' or 'STATE_UNLOCKED'.
                                                // if currentStateOfAchievement is 'STATE_HIDDEN' we can do the following
                                                if (currentStateOfAchievement == Achievement.STATE_HIDDEN) {
                                                    currentStateOfAchievement = Achievement.STATE_REVEALED;
                                                    achievementsClient.reveal(getString(currentSmashTileToolAchievement.getAchievementStringResourceId()));
                                                }
                                            } else if (levelWiseExpectedState.get(smashTileToolAchievementsIndex) == Achievement.STATE_UNLOCKED) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, we can unlock the achievement
                                                currentStateOfAchievement = Achievement.STATE_UNLOCKED;
                                                achievementsClient.unlock(getString(currentSmashTileToolAchievement.getAchievementStringResourceId()));
                                            }
                                        }

                                        sharedPreferences.edit().putInt("smashTileToolAchievement" + "_" +
                                                        getString(currentSmashTileToolAchievement.getAchievementStringResourceId()),
                                                currentStateOfAchievement).apply();
                                    }
                                }

                                // Updating the progress related to the use count of 'Swap Tiles' tool
                                if (leaderboardId.equals(getString(R.string.leaderboard_swap_tiles_tool_masters))) {
                                    List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                    int savedSwapTilesToolUseCountSubmitted =
                                            sharedPreferences.getInt("swapTilesToolUseCountSubmitted", 0);
                                    int savedSwapTilesToolCurrentUseCount =
                                            sharedPreferences.getInt("swapTilesToolCurrentUseCount", savedSwapTilesToolUseCountSubmitted);
                                    int leaderboardSwapTilesToolUseCount = Integer.MIN_VALUE;
                                    for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                        LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                        int currentLeaderboardSwapTilesToolUseCount = (int) currentVariant.getRawPlayerScore();
                                        leaderboardSwapTilesToolUseCount = Math.max(leaderboardSwapTilesToolUseCount, currentLeaderboardSwapTilesToolUseCount);
                                    }
                                    // (1) If the score was never submitted, then value of 'leaderboardSwapTilesToolUseCount' will be -1.
                                    // So we first submit the score to the leaderboard
                                    if (leaderboardSwapTilesToolUseCount < 0) {
                                        leaderboardSwapTilesToolUseCount = 0;
                                        leaderboardsClient.submitScore(leaderboardId, leaderboardSwapTilesToolUseCount);
                                    }

                                    // (2) Always update 'swapTilesToolUseCountSubmitted' in saved data
                                    savedSwapTilesToolUseCountSubmitted = leaderboardSwapTilesToolUseCount;
                                    sharedPreferences.edit().putInt("swapTilesToolUseCountSubmitted", savedSwapTilesToolUseCountSubmitted).apply();

                                    // (3) Based on value of 'swapTilesToolUseCountSubmitted' save the value of 'swapTilesToolCurrentUseCount'
                                    if (savedSwapTilesToolCurrentUseCount >= savedSwapTilesToolUseCountSubmitted
                                            && savedSwapTilesToolCurrentUseCount < savedSwapTilesToolUseCountSubmitted + 10) {
                                    } else {
                                        savedSwapTilesToolCurrentUseCount = savedSwapTilesToolUseCountSubmitted;
                                    }
                                    sharedPreferences.edit().putInt("swapTilesToolCurrentUseCount", savedSwapTilesToolCurrentUseCount).apply();

                                    // (4) Verify if the state of the achievements is in accordance with the above 2 values
                                    List<Integer> levelWiseExpectedState = new ArrayList<>() {{
                                        add(Achievement.STATE_REVEALED); add(Achievement.STATE_HIDDEN); add(Achievement.STATE_HIDDEN);
                                    }};
                                    if (savedSwapTilesToolUseCountSubmitted >= 100 && savedSwapTilesToolUseCountSubmitted < 250) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_REVEALED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_HIDDEN);
                                    } else if (savedSwapTilesToolUseCountSubmitted >= 250 && savedSwapTilesToolUseCountSubmitted < 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_REVEALED);
                                    } else if (savedSwapTilesToolUseCountSubmitted >= 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_UNLOCKED);
                                    }

                                    for (int swapTilesToolAchievementsIndex = 0; swapTilesToolAchievementsIndex <
                                            SwapTilesToolAchievements.values().length; swapTilesToolAchievementsIndex++) {
                                        SwapTilesToolAchievements currentSwapTilesToolAchievement =
                                                SwapTilesToolAchievements.values()[swapTilesToolAchievementsIndex];
                                        int currentStateOfAchievement = sharedPreferences.getInt("swapTilesToolAchievement" + "_" +
                                                        getString(currentSwapTilesToolAchievement.getAchievementStringResourceId()),
                                                currentSwapTilesToolAchievement.getInitialAchievementState());

                                        if (levelWiseExpectedState.get(swapTilesToolAchievementsIndex) == currentStateOfAchievement) {
                                            // All good here
                                        } else {
                                            if (levelWiseExpectedState.get(swapTilesToolAchievementsIndex) == Achievement.STATE_HIDDEN) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, nothing we can do
                                            } else if (levelWiseExpectedState.get(swapTilesToolAchievementsIndex) == Achievement.STATE_REVEALED) {
                                                // Then, currentStateOfAchievement is either 'STATE_HIDDEN' or 'STATE_UNLOCKED'.
                                                // if currentStateOfAchievement is 'STATE_HIDDEN' we can do the following
                                                if (currentStateOfAchievement == Achievement.STATE_HIDDEN) {
                                                    currentStateOfAchievement = Achievement.STATE_REVEALED;
                                                    achievementsClient.reveal(getString(currentSwapTilesToolAchievement.getAchievementStringResourceId()));
                                                }
                                            } else if (levelWiseExpectedState.get(swapTilesToolAchievementsIndex) == Achievement.STATE_UNLOCKED) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, we can unlock the achievement
                                                currentStateOfAchievement = Achievement.STATE_UNLOCKED;
                                                achievementsClient.unlock(getString(currentSwapTilesToolAchievement.getAchievementStringResourceId()));
                                            }
                                        }

                                        sharedPreferences.edit().putInt("swapTilesToolAchievement" + "_" +
                                                        getString(currentSwapTilesToolAchievement.getAchievementStringResourceId()),
                                                currentStateOfAchievement).apply();
                                    }
                                }

                                // Updating the progress related to the use count of 'Change Value' tool
                                if (leaderboardId.equals(getString(R.string.leaderboard_change_value_tool_masters))) {
                                    List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                    int savedChangeValueToolUseCountSubmitted =
                                            sharedPreferences.getInt("changeValueToolUseCountSubmitted", 0);
                                    int savedChangeValueToolCurrentUseCount =
                                            sharedPreferences.getInt("changeValueToolCurrentUseCount", savedChangeValueToolUseCountSubmitted);
                                    int leaderboardChangeValueToolUseCount = Integer.MIN_VALUE;
                                    for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                        LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                        int currentLeaderboardChangeValueToolUseCount = (int) currentVariant.getRawPlayerScore();
                                        leaderboardChangeValueToolUseCount = Math.max(leaderboardChangeValueToolUseCount, currentLeaderboardChangeValueToolUseCount);
                                    }
                                    // (1) If the score was never submitted, then value of 'leaderboardChangeValueToolUseCount' will be -1.
                                    // So we first submit the score to the leaderboard
                                    if (leaderboardChangeValueToolUseCount < 0) {
                                        leaderboardChangeValueToolUseCount = 0;
                                        leaderboardsClient.submitScore(leaderboardId, leaderboardChangeValueToolUseCount);
                                    }

                                    // (2) Always update 'changeValueToolUseCountSubmitted' in saved data
                                    savedChangeValueToolUseCountSubmitted = leaderboardChangeValueToolUseCount;
                                    sharedPreferences.edit().putInt("changeValueToolUseCountSubmitted", savedChangeValueToolUseCountSubmitted).apply();

                                    // (3) Based on value of 'changeValueToolUseCountSubmitted' save the value of 'changeValueToolCurrentUseCount'
                                    if (savedChangeValueToolCurrentUseCount >= savedChangeValueToolUseCountSubmitted
                                            && savedChangeValueToolCurrentUseCount < savedChangeValueToolUseCountSubmitted + 10) {
                                    } else {
                                        savedChangeValueToolCurrentUseCount = savedChangeValueToolUseCountSubmitted;
                                    }
                                    sharedPreferences.edit().putInt("changeValueToolCurrentUseCount", savedChangeValueToolCurrentUseCount).apply();

                                    // (4) Verify if the state of the achievements is in accordance with the above 2 values
                                    List<Integer> levelWiseExpectedState = new ArrayList<>() {{
                                        add(Achievement.STATE_REVEALED); add(Achievement.STATE_HIDDEN); add(Achievement.STATE_HIDDEN);
                                    }};
                                    if (savedChangeValueToolUseCountSubmitted >= 100 && savedChangeValueToolUseCountSubmitted < 250) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_REVEALED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_HIDDEN);
                                    } else if (savedChangeValueToolUseCountSubmitted >= 250 && savedChangeValueToolUseCountSubmitted < 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_REVEALED);
                                    } else if (savedChangeValueToolUseCountSubmitted >= 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_UNLOCKED);
                                    }

                                    for (int changeValueToolAchievementsIndex = 0; changeValueToolAchievementsIndex <
                                            ChangeValueToolAchievements.values().length; changeValueToolAchievementsIndex++) {
                                        ChangeValueToolAchievements currentChangeValueToolAchievement =
                                                ChangeValueToolAchievements.values()[changeValueToolAchievementsIndex];
                                        int currentStateOfAchievement = sharedPreferences.getInt("changeValueToolAchievement" + "_" +
                                                        getString(currentChangeValueToolAchievement.getAchievementStringResourceId()),
                                                currentChangeValueToolAchievement.getInitialAchievementState());

                                        if (levelWiseExpectedState.get(changeValueToolAchievementsIndex) == currentStateOfAchievement) {
                                            // All good here
                                        } else {
                                            if (levelWiseExpectedState.get(changeValueToolAchievementsIndex) == Achievement.STATE_HIDDEN) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, nothing we can do
                                            } else if (levelWiseExpectedState.get(changeValueToolAchievementsIndex) == Achievement.STATE_REVEALED) {
                                                // Then, currentStateOfAchievement is either 'STATE_HIDDEN' or 'STATE_UNLOCKED'.
                                                // if currentStateOfAchievement is 'STATE_HIDDEN' we can do the following
                                                if (currentStateOfAchievement == Achievement.STATE_HIDDEN) {
                                                    currentStateOfAchievement = Achievement.STATE_REVEALED;
                                                    achievementsClient.reveal(getString(currentChangeValueToolAchievement.getAchievementStringResourceId()));
                                                }
                                            } else if (levelWiseExpectedState.get(changeValueToolAchievementsIndex) == Achievement.STATE_UNLOCKED) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, we can unlock the achievement
                                                currentStateOfAchievement = Achievement.STATE_UNLOCKED;
                                                achievementsClient.unlock(getString(currentChangeValueToolAchievement.getAchievementStringResourceId()));
                                            }
                                        }

                                        sharedPreferences.edit().putInt("changeValueToolAchievement" + "_" +
                                                        getString(currentChangeValueToolAchievement.getAchievementStringResourceId()),
                                                currentStateOfAchievement).apply();
                                    }
                                }

                                // Updating the progress related to the use count of 'Eliminate Value' tool
                                if (leaderboardId.equals(getString(R.string.leaderboard_eliminate_value_tool_masters))) {
                                    List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                    int savedEliminateValueToolUseCountSubmitted =
                                            sharedPreferences.getInt("eliminateValueToolUseCountSubmitted", 0);
                                    int savedEliminateValueToolCurrentUseCount =
                                            sharedPreferences.getInt("eliminateValueToolCurrentUseCount", savedEliminateValueToolUseCountSubmitted);
                                    int leaderboardEliminateValueToolUseCount = Integer.MIN_VALUE;
                                    for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                        LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                        int currentLeaderboardEliminateValueToolUseCount = (int) currentVariant.getRawPlayerScore();
                                        leaderboardEliminateValueToolUseCount = Math.max(leaderboardEliminateValueToolUseCount, currentLeaderboardEliminateValueToolUseCount);
                                    }
                                    // (1) If the score was never submitted, then value of 'leaderboardEliminateValueToolUseCount' will be -1.
                                    // So we first submit the score to the leaderboard
                                    if (leaderboardEliminateValueToolUseCount < 0) {
                                        leaderboardEliminateValueToolUseCount = 0;
                                        leaderboardsClient.submitScore(leaderboardId, leaderboardEliminateValueToolUseCount);
                                    }

                                    // (2) Always update 'eliminateValueToolUseCountSubmitted' in saved data
                                    savedEliminateValueToolUseCountSubmitted = leaderboardEliminateValueToolUseCount;
                                    sharedPreferences.edit().putInt("eliminateValueToolUseCountSubmitted", savedEliminateValueToolUseCountSubmitted).apply();

                                    // (3) Based on value of 'eliminateValueToolUseCountSubmitted' save the value of 'eliminateValueToolCurrentUseCount'
                                    if (savedEliminateValueToolCurrentUseCount >= savedEliminateValueToolUseCountSubmitted
                                            && savedEliminateValueToolCurrentUseCount < savedEliminateValueToolUseCountSubmitted + 10) {
                                    } else {
                                        savedEliminateValueToolCurrentUseCount = savedEliminateValueToolUseCountSubmitted;
                                    }
                                    sharedPreferences.edit().putInt("eliminateValueToolCurrentUseCount", savedEliminateValueToolCurrentUseCount).apply();

                                    // (4) Verify if the state of the achievements is in accordance with the above 2 values
                                    List<Integer> levelWiseExpectedState = new ArrayList<>() {{
                                        add(Achievement.STATE_REVEALED); add(Achievement.STATE_HIDDEN); add(Achievement.STATE_HIDDEN);
                                    }};
                                    if (savedEliminateValueToolUseCountSubmitted >= 100 && savedEliminateValueToolUseCountSubmitted < 250) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_REVEALED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_HIDDEN);
                                    } else if (savedEliminateValueToolUseCountSubmitted >= 250 && savedEliminateValueToolUseCountSubmitted < 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_REVEALED);
                                    } else if (savedEliminateValueToolUseCountSubmitted >= 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_UNLOCKED);
                                    }

                                    for (int eliminateValueToolAchievementsIndex = 0; eliminateValueToolAchievementsIndex <
                                            EliminateValueToolAchievements.values().length; eliminateValueToolAchievementsIndex++) {
                                        EliminateValueToolAchievements currentEliminateValueToolAchievement =
                                                EliminateValueToolAchievements.values()[eliminateValueToolAchievementsIndex];
                                        int currentStateOfAchievement = sharedPreferences.getInt("eliminateValueToolAchievement" + "_" +
                                                        getString(currentEliminateValueToolAchievement.getAchievementStringResourceId()),
                                                currentEliminateValueToolAchievement.getInitialAchievementState());

                                        if (levelWiseExpectedState.get(eliminateValueToolAchievementsIndex) == currentStateOfAchievement) {
                                            // All good here
                                        } else {
                                            if (levelWiseExpectedState.get(eliminateValueToolAchievementsIndex) == Achievement.STATE_HIDDEN) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, nothing we can do
                                            } else if (levelWiseExpectedState.get(eliminateValueToolAchievementsIndex) == Achievement.STATE_REVEALED) {
                                                // Then, currentStateOfAchievement is either 'STATE_HIDDEN' or 'STATE_UNLOCKED'.
                                                // if currentStateOfAchievement is 'STATE_HIDDEN' we can do the following
                                                if (currentStateOfAchievement == Achievement.STATE_HIDDEN) {
                                                    currentStateOfAchievement = Achievement.STATE_REVEALED;
                                                    achievementsClient.reveal(getString(currentEliminateValueToolAchievement.getAchievementStringResourceId()));
                                                }
                                            } else if (levelWiseExpectedState.get(eliminateValueToolAchievementsIndex) == Achievement.STATE_UNLOCKED) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, we can unlock the achievement
                                                currentStateOfAchievement = Achievement.STATE_UNLOCKED;
                                                achievementsClient.unlock(getString(currentEliminateValueToolAchievement.getAchievementStringResourceId()));
                                            }
                                        }

                                        sharedPreferences.edit().putInt("eliminateValueToolAchievement" + "_" +
                                                        getString(currentEliminateValueToolAchievement.getAchievementStringResourceId()),
                                                currentStateOfAchievement).apply();
                                    }
                                }

                                // Updating the progress related to the use count of 'Destroy Area' tool
                                if (leaderboardId.equals(getString(R.string.leaderboard_destroy_area_tool_masters))) {
                                    List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                    int savedDestroyAreaToolUseCountSubmitted =
                                            sharedPreferences.getInt("destroyAreaToolUseCountSubmitted", 0);
                                    int savedDestroyAreaToolCurrentUseCount =
                                            sharedPreferences.getInt("destroyAreaToolCurrentUseCount", savedDestroyAreaToolUseCountSubmitted);
                                    int leaderboardDestroyAreaToolUseCount = Integer.MIN_VALUE;
                                    for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                        LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                        int currentLeaderboardDestroyAreaToolUseCount = (int) currentVariant.getRawPlayerScore();
                                        leaderboardDestroyAreaToolUseCount = Math.max(leaderboardDestroyAreaToolUseCount, currentLeaderboardDestroyAreaToolUseCount);
                                    }
                                    // (1) If the score was never submitted, then value of 'leaderboardDestroyAreaToolUseCount' will be -1.
                                    // So we first submit the score to the leaderboard
                                    if (leaderboardDestroyAreaToolUseCount < 0) {
                                        leaderboardDestroyAreaToolUseCount = 0;
                                        leaderboardsClient.submitScore(leaderboardId, leaderboardDestroyAreaToolUseCount);
                                    }

                                    // (2) Always update 'destroyAreaToolUseCountSubmitted' in saved data
                                    savedDestroyAreaToolUseCountSubmitted = leaderboardDestroyAreaToolUseCount;
                                    sharedPreferences.edit().putInt("destroyAreaToolUseCountSubmitted", savedDestroyAreaToolUseCountSubmitted).apply();

                                    // (3) Based on value of 'destroyAreaToolUseCountSubmitted' save the value of 'destroyAreaToolCurrentUseCount'
                                    if (savedDestroyAreaToolCurrentUseCount >= savedDestroyAreaToolUseCountSubmitted
                                            && savedDestroyAreaToolCurrentUseCount < savedDestroyAreaToolUseCountSubmitted + 10) {
                                    } else {
                                        savedDestroyAreaToolCurrentUseCount = savedDestroyAreaToolUseCountSubmitted;
                                    }
                                    sharedPreferences.edit().putInt("destroyAreaToolCurrentUseCount", savedDestroyAreaToolCurrentUseCount).apply();

                                    // (4) Verify if the state of the achievements is in accordance with the above 2 values
                                    List<Integer> levelWiseExpectedState = new ArrayList<>() {{
                                        add(Achievement.STATE_REVEALED); add(Achievement.STATE_HIDDEN); add(Achievement.STATE_HIDDEN);
                                    }};
                                    if (savedDestroyAreaToolUseCountSubmitted >= 100 && savedDestroyAreaToolUseCountSubmitted < 250) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_REVEALED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_HIDDEN);
                                    } else if (savedDestroyAreaToolUseCountSubmitted >= 250 && savedDestroyAreaToolUseCountSubmitted < 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_REVEALED);
                                    } else if (savedDestroyAreaToolUseCountSubmitted >= 500) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_UNLOCKED);
                                    }

                                    for (int destroyAreaToolAchievementsIndex = 0; destroyAreaToolAchievementsIndex <
                                            DestroyAreaToolAchievements.values().length; destroyAreaToolAchievementsIndex++) {
                                        DestroyAreaToolAchievements currentDestroyAreaToolAchievement =
                                                DestroyAreaToolAchievements.values()[destroyAreaToolAchievementsIndex];
                                        int currentStateOfAchievement = sharedPreferences.getInt("destroyAreaToolAchievement" + "_" +
                                                        getString(currentDestroyAreaToolAchievement.getAchievementStringResourceId()),
                                                currentDestroyAreaToolAchievement.getInitialAchievementState());

                                        if (levelWiseExpectedState.get(destroyAreaToolAchievementsIndex) == currentStateOfAchievement) {
                                            // All good here
                                        } else {
                                            if (levelWiseExpectedState.get(destroyAreaToolAchievementsIndex) == Achievement.STATE_HIDDEN) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, nothing we can do
                                            } else if (levelWiseExpectedState.get(destroyAreaToolAchievementsIndex) == Achievement.STATE_REVEALED) {
                                                // Then, currentStateOfAchievement is either 'STATE_HIDDEN' or 'STATE_UNLOCKED'.
                                                // if currentStateOfAchievement is 'STATE_HIDDEN' we can do the following
                                                if (currentStateOfAchievement == Achievement.STATE_HIDDEN) {
                                                    currentStateOfAchievement = Achievement.STATE_REVEALED;
                                                    achievementsClient.reveal(getString(currentDestroyAreaToolAchievement.getAchievementStringResourceId()));
                                                }
                                            } else if (levelWiseExpectedState.get(destroyAreaToolAchievementsIndex) == Achievement.STATE_UNLOCKED) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, we can unlock the achievement
                                                currentStateOfAchievement = Achievement.STATE_UNLOCKED;
                                                achievementsClient.unlock(getString(currentDestroyAreaToolAchievement.getAchievementStringResourceId()));
                                            }
                                        }

                                        sharedPreferences.edit().putInt("destroyAreaToolAchievement" + "_" +
                                                        getString(currentDestroyAreaToolAchievement.getAchievementStringResourceId()),
                                                currentStateOfAchievement).apply();
                                    }
                                }

                                // Updating the progress related to the use count of 'Revive Game' tool
                                if (leaderboardId.equals(getString(R.string.leaderboard_revive_game_tool_masters))) {
                                    List<LeaderboardVariant> leaderboardVariants = leaderboard.getVariants();
                                    int savedReviveGameToolUseCountSubmitted =
                                            sharedPreferences.getInt("reviveGameToolUseCountSubmitted", 0);
                                    int savedReviveGameToolCurrentUseCount =
                                            sharedPreferences.getInt("reviveGameToolCurrentUseCount", savedReviveGameToolUseCountSubmitted);
                                    int leaderboardReviveGameToolUseCount = Integer.MIN_VALUE;
                                    for (int variantIndex = 0; variantIndex < leaderboardVariants.size(); variantIndex++) {
                                        LeaderboardVariant currentVariant = leaderboardVariants.get(variantIndex);
                                        int currentLeaderboardReviveGameToolUseCount = (int) currentVariant.getRawPlayerScore();
                                        leaderboardReviveGameToolUseCount = Math.max(leaderboardReviveGameToolUseCount, currentLeaderboardReviveGameToolUseCount);
                                    }
                                    // (1) If the score was never submitted, then value of 'leaderboardReviveGameToolUseCount' will be -1.
                                    // So we first submit the score to the leaderboard
                                    if (leaderboardReviveGameToolUseCount < 0) {
                                        leaderboardReviveGameToolUseCount = 0;
                                        leaderboardsClient.submitScore(leaderboardId, leaderboardReviveGameToolUseCount);
                                    }

                                    // (2) Always update 'reviveGameToolUseCountSubmitted' in saved data
                                    savedReviveGameToolUseCountSubmitted = leaderboardReviveGameToolUseCount;
                                    sharedPreferences.edit().putInt("reviveGameToolUseCountSubmitted", savedReviveGameToolUseCountSubmitted).apply();

                                    // (3) Based on value of 'reviveGameToolUseCountSubmitted' save the value of 'reviveGameToolCurrentUseCount'
                                    if (savedReviveGameToolCurrentUseCount >= savedReviveGameToolUseCountSubmitted
                                            && savedReviveGameToolCurrentUseCount < savedReviveGameToolUseCountSubmitted + 10) {
                                    } else {
                                        savedReviveGameToolCurrentUseCount = savedReviveGameToolUseCountSubmitted;
                                    }
                                    sharedPreferences.edit().putInt("reviveGameToolCurrentUseCount", savedReviveGameToolCurrentUseCount).apply();

                                    // (4) Verify if the state of the achievements is in accordance with the above 2 values
                                    List<Integer> levelWiseExpectedState = new ArrayList<>() {{
                                        add(Achievement.STATE_REVEALED); add(Achievement.STATE_HIDDEN); add(Achievement.STATE_HIDDEN);
                                    }};
                                    if (savedReviveGameToolUseCountSubmitted >= 50 && savedReviveGameToolUseCountSubmitted < 100) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_REVEALED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_HIDDEN);
                                    } else if (savedReviveGameToolUseCountSubmitted >= 100 && savedReviveGameToolUseCountSubmitted < 200) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_REVEALED);
                                    } else if (savedReviveGameToolUseCountSubmitted >= 200) {
                                        levelWiseExpectedState.set(0, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(1, Achievement.STATE_UNLOCKED);
                                        levelWiseExpectedState.set(2, Achievement.STATE_UNLOCKED);
                                    }

                                    for (int reviveGameToolAchievementsIndex = 0; reviveGameToolAchievementsIndex <
                                            ReviveGameToolAchievements.values().length; reviveGameToolAchievementsIndex++) {
                                        ReviveGameToolAchievements currentReviveGameToolAchievement =
                                                ReviveGameToolAchievements.values()[reviveGameToolAchievementsIndex];
                                        int currentStateOfAchievement = sharedPreferences.getInt("reviveGameToolAchievement" + "_" +
                                                        getString(currentReviveGameToolAchievement.getAchievementStringResourceId()),
                                                currentReviveGameToolAchievement.getInitialAchievementState());

                                        if (levelWiseExpectedState.get(reviveGameToolAchievementsIndex) == currentStateOfAchievement) {
                                            // All good here
                                        } else {
                                            if (levelWiseExpectedState.get(reviveGameToolAchievementsIndex) == Achievement.STATE_HIDDEN) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, nothing we can do
                                            } else if (levelWiseExpectedState.get(reviveGameToolAchievementsIndex) == Achievement.STATE_REVEALED) {
                                                // Then, currentStateOfAchievement is either 'STATE_HIDDEN' or 'STATE_UNLOCKED'.
                                                // if currentStateOfAchievement is 'STATE_HIDDEN' we can do the following
                                                if (currentStateOfAchievement == Achievement.STATE_HIDDEN) {
                                                    currentStateOfAchievement = Achievement.STATE_REVEALED;
                                                    achievementsClient.reveal(getString(currentReviveGameToolAchievement.getAchievementStringResourceId()));
                                                }
                                            } else if (levelWiseExpectedState.get(reviveGameToolAchievementsIndex) == Achievement.STATE_UNLOCKED) {
                                                // Then, currentStateOfAchievement is either 'STATE_REVEALED' or 'STATE_UNLOCKED'.
                                                // Either way, we can unlock the achievement
                                                currentStateOfAchievement = Achievement.STATE_UNLOCKED;
                                                achievementsClient.unlock(getString(currentReviveGameToolAchievement.getAchievementStringResourceId()));
                                            }
                                        }

                                        sharedPreferences.edit().putInt("reviveGameToolAchievement" + "_" +
                                                        getString(currentReviveGameToolAchievement.getAchievementStringResourceId()),
                                                currentStateOfAchievement).apply();
                                    }
                                }
                            }
                        }

                        if (leaderboardBuffer != null) {
                            leaderboardBuffer.release();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        updateLeaderboardsProgress(retryAttempt + 1);
                    }
                });
    }

    private void updateCoins(int currentCoins) {
        sharedPreferences.edit().putInt("currentCoins", currentCoins).apply();
        List<Fragment> fragments = new ArrayList<>(getSupportFragmentManager().getFragments());
        for (int index = 0; index < fragments.size(); index++) {
            Fragment currentFragment = fragments.get(index);
            if (currentFragment != null && currentFragment.getTag() != null
                    && !currentFragment.getTag().isEmpty()) {
                if (currentFragment.getTag().equals("SHOP_FRAGMENT")) {
                    ((ShopFragment) currentFragment).updateCoinsShopFragment(currentCoins);
                }
            }
        }

        // Check if current coins count is greater than the highest most coins count
        int mostCoins = sharedPreferences.getInt("mostCoins", 3000);
        if (currentCoins >= mostCoins + 1000) {
            sharedPreferences.edit().putInt("mostCoins", currentCoins).apply();
            leaderboardsClient.submitScore(getString(R.string.leaderboard_coins_leaderboard), currentCoins);
        }
    }

    @Override
    public void onNavigationFragmentGPGSSignInClicked() {
        gamesSignInClient.signIn();
        new CountDownTimer(1000, 10000) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() { verifyPlayGamesSignIn(true); }
        }.start();
    }

    @Override
    public void onNavigationFragmentPreGameClicked() {
        // If PreGameFragment was opened and is currently on top, then return
        int countOfFragments = getSupportFragmentManager().getFragments().size();
        if (countOfFragments > 0) {
            Fragment topMostFragment = getSupportFragmentManager().getFragments().get(countOfFragments-1);
            if (topMostFragment != null && topMostFragment.getTag() != null && !topMostFragment.getTag().isEmpty()
                    && topMostFragment.getTag().equals("PREGAME_FRAGMENT")) {
                return;
            }
        }

        PreGameFragment fragment = new PreGameFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.main_activity_full_screen_fragment_container,
                fragment, "PREGAME_FRAGMENT").commit();
    }

    private void showAchievementsDisplayPostVerification() {
        achievementsClient.getAchievementsIntent().addOnSuccessListener(new OnSuccessListener<Intent>() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent, RC_ACHIEVEMENT_UI);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (!isInternetConnected()) { // Internet is not connected which can be the cause of this failure
                    Toast.makeText(MainActivity.this, "Network connection failed. Please check " +
                            "Internet connectivity", Toast.LENGTH_LONG).show();
                } else { // Some unknown error has occurred
                    new ErrorOccurredDialog(MainActivity.this, "Oops! Something went wrong").show();
                }
            }
        });
    }

    @Override
    public void onNavigationFragmentAchievementsClicked() {
        if (!isUserSignedIn) {
            gamesSignInClient.signIn();
            new CountDownTimer(1000, 10000) {
                @Override
                public void onTick(long l) {}
                @Override
                public void onFinish() {
                    verifyPlayGamesSignInPreAchievementsDisplay();
                }
            }.start();
        } else {
            showAchievementsDisplayPostVerification();
        }
    }

    private void showLeaderboardsDisplayPostVerification() {
        // If LeaderboardsFragment was opened and is currently on top, then return
        int countOfFragments = getSupportFragmentManager().getFragments().size();
        if (countOfFragments > 0) {
            Fragment topMostFragment = getSupportFragmentManager().getFragments().get(countOfFragments-1);
            if (topMostFragment != null && topMostFragment.getTag() != null && !topMostFragment.getTag().isEmpty()
                    && topMostFragment.getTag().equals("LEADERBOARDS_FRAGMENT")) {
                return;
            }
        }

        LeaderboardsFragment fragment = new LeaderboardsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.main_activity_full_screen_fragment_container,
                fragment, "LEADERBOARDS_FRAGMENT").commit();
    }

    @Override
    public void onNavigationFragmentLeaderboardsClicked() {
        if (!isUserSignedIn) {
            gamesSignInClient.signIn();
            new CountDownTimer(1000, 10000) {
                @Override
                public void onTick(long l) {}
                @Override
                public void onFinish() {
                    verifyPlayGamesSignInPreLeaderboardsDisplay();
                }
            }.start();
        } else {
            showLeaderboardsDisplayPostVerification();
        }
    }

    @Override
    public void onNavigationFragmentSettingsClicked() {
        // If SettingsFragment was opened and is currently on top, then return
        int countOfFragments = getSupportFragmentManager().getFragments().size();
        if (countOfFragments > 0) {
            Fragment topMostFragment = getSupportFragmentManager().getFragments().get(countOfFragments-1);
            if (topMostFragment != null && topMostFragment.getTag() != null && !topMostFragment.getTag().isEmpty()
                    && topMostFragment.getTag().equals("SETTINGS_FRAGMENT")) {
                return;
            }
        }

        SettingsFragment fragment = SettingsFragment.newInstance(isUserSignedIn);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.replace(R.id.main_activity_full_screen_fragment_container,
                fragment, "SETTINGS_FRAGMENT").commit();
    }

    @Override
    public void onNavigationFragmentShopClicked() {
        // If ShopFragment was opened and is currently on top, then return
        int countOfFragments = getSupportFragmentManager().getFragments().size();
        if (countOfFragments > 0) {
            Fragment topMostFragment = getSupportFragmentManager().getFragments().get(countOfFragments-1);
            if (topMostFragment != null && topMostFragment.getTag() != null && !topMostFragment.getTag().isEmpty()
                    && topMostFragment.getTag().equals("SHOP_FRAGMENT")) {
                return;
            }
        }

        ShopFragment fragment = new ShopFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.main_activity_full_screen_fragment_container,
                fragment, "SHOP_FRAGMENT").commit();
    }

    @Override
    public void onPreGameFragmentInteractionBackClicked() {
        onBackPressed();
    }

    @Override
    public void onPreGameFragmentInteractionStartGame(String gameMode, int gameMatrixColumns, int gameMatrixRows) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("gameMode", gameMode);
        intent.putExtra("gameMatrixColumns", gameMatrixColumns);
        intent.putExtra("gameMatrixRows", gameMatrixRows);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPreGameFragmentInteractionShowArrivingFeatureDialog() {
        new ArrivingFeatureDialog(this).show();
    }

    @Override
    public void onLeaderboardsFragmentInteractionBackClicked() {
        onBackPressed();
    }

    @Override
    public void onLeaderboardsFragmentInteractionShowLeaderboard(int leaderboardStringResourceId) {
        leaderboardsClient.getLeaderboardIntent(getString(leaderboardStringResourceId))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (!isInternetConnected()) { // Internet is not connected which can be the cause of this failure
                            Toast.makeText(MainActivity.this, "Network connection failed. Please check " +
                                    "Internet connectivity", Toast.LENGTH_LONG).show();
                        } else { // Some unknown error has occurred
                            new ErrorOccurredDialog(MainActivity.this, "Oops! Something went wrong").show();
                        }
                    }
                });
    }

    @Override
    public void onSettingsFragmentInteractionBackClicked() {
        onBackPressed();
    }

    @Override
    public void onSettingsFragmentInteractionGPGSSignInClicked() {
        gamesSignInClient.signIn();
        new CountDownTimer(1000, 10000) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() { verifyPlayGamesSignIn(true); }
        }.start();
    }

    @Override
    public void onSettingsFragmentInteractionGetPremiumClicked() {
        // If ShopFragment was opened and is currently on top, then return
        int countOfFragments = getSupportFragmentManager().getFragments().size();
        if (countOfFragments > 0) {
            Fragment topMostFragment = getSupportFragmentManager().getFragments().get(countOfFragments-1);
            if (topMostFragment != null && topMostFragment.getTag() != null && !topMostFragment.getTag().isEmpty()
                    && topMostFragment.getTag().equals("SHOP_FRAGMENT")) {
                return;
            }
        }

        ShopFragment fragment = new ShopFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.main_activity_full_screen_fragment_container,
                fragment, "SHOP_FRAGMENT").commit();
    }

    @Override
    public void onSettingsFragmentInteractionToggleRotatingLightClicked(boolean isChecked) {
        List<Fragment> fragments = new ArrayList<>(getSupportFragmentManager().getFragments());
        for (int index = 0; index < fragments.size(); index++) {
            Fragment currentFragment = fragments.get(index);
            if (currentFragment != null && currentFragment.getTag() != null
                    && !currentFragment.getTag().isEmpty()) {
                if (currentFragment.getTag().equals("LOGO_LOTTIE_FRAGMENT")) {
                    ((LogoLottieFragment) currentFragment).updateRotatingLightState(isChecked);
                }
            }
        }
    }

    @Override
    public void onSettingsFragmentInteractionBlockDesignClicked() {
        // If BlockDesignFragment was opened and is currently on top, then return
        int countOfFragments = getSupportFragmentManager().getFragments().size();
        if (countOfFragments > 0) {
            Fragment topMostFragment = getSupportFragmentManager().getFragments().get(countOfFragments-1);
            if (topMostFragment != null && topMostFragment.getTag() != null && !topMostFragment.getTag().isEmpty()
                    && topMostFragment.getTag().equals("BLOCK_DESIGN_FRAGMENT")) {
                return;
            }
        }

        BlockDesignFragment fragment = new BlockDesignFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.main_activity_full_screen_fragment_container,
                fragment, "BLOCK_DESIGN_FRAGMENT").commit();
    }

    @Override
    public void onSettingsFragmentInteractionHowToPlayClicked() {
        Toast.makeText(MainActivity.this, "'How To Play' button clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSettingsFragmentInteractionHelpClicked() {
        Toast.makeText(MainActivity.this, "'Help' button clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSettingsFragmentInteractionCheckUpdatesClicked() {
        launchInAppUpdateFlowForStaticButton();
    }

    @Override
    public void onSettingsFragmentInteractionExitClicked() {
        GameExitDialog gameExitDialog = new GameExitDialog(this);
        gameExitDialog.show();
        gameExitDialog.setGameExitDialogListener(new GameExitDialog.GameExitDialogListener() {
            @Override
            public void getResponseOfExitDialog(boolean response) {
                if (response) {
                    MainActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void onBlockDesignFragmentInteractionBackClicked() {
        onBackPressed();
    }

    @Override
    public void onShopFragmentInteractionBackClicked() {
        onBackPressed();
    }

    @Override
    public void onShopFragmentInteractionUpdateCoins(int currentCoins) {
        updateCoins(currentCoins);
    }
}