package com.nerdcoredevelopment.game2048champsfinal.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nerdcoredevelopment.game2048champsfinal.fragments.AnnouncementsFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.BlockDesignFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.LogoLottieFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.NavigationFragment;
import com.nerdcoredevelopment.game2048champsfinal.R;
import com.nerdcoredevelopment.game2048champsfinal.fragments.SettingsFragment;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.ArrivingFeatureDialog;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.GameExitDialog;
import com.nerdcoredevelopment.game2048champsfinal.fragments.PreGameFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.ShopFragment;
import com.nerdcoredevelopment.game2048champsfinal.fragments.ThemesFragment;

import java.util.ArrayList;
import java.util.List;

/* TODO -> Look into Animated Gradient Background (Something like the Instagram start screen)
           Resource Link -> https://www.youtube.com/watch?v=x_DXXGvyfh8
*/
/* TODO -> Add a 'Share' button for sharing app download link & 'Promote' button for sharing promo
           code of possibly a premium product/scheme together as one group having these 2 buttons
           in the SettingsFragment
*/
/* TODO -> Multiple quick dialog OR activity open clicks cause the dialog OR activity to open multiple times, we have to
           find a way to avoid that. Basically wherever a new page is opening there we need to stop this from happening
*/
// TODO -> Implement the In-app updates button in the SettingsFragment
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
// TODO -> 'Hammer of Thor' name for the highest level achievement of use of Smash Tile Tool
/* TODO -> 'Revive Game' tool only available in GameOverDialog. Cost of use - 1000 coins. Tool Effect - Eliminates all the
            game tiles except the tiles with the highest value, collects and starts placing them from the bottom left corner
            towards right, if the last row fills up then in the row above from left to right and so on, each row from top to
            bottom.
*/
/* TODO -> Change the preview images after Changing theme colors & colors for blocks after implementing new game progression
           system for -
           (1) Game Preview Image (Ensure color change of board, cells, block cell x drawable color,and game tiles etc.)
           (2) Block Preview Image (Ensure color change of board, only use board of existing game modes, all 20 block
           drawable designs)
*/
// TODO -> In the later stages of development, think over the colors for the different buttons in all the dialogs
public class MainActivity extends AppCompatActivity implements
        NavigationFragment.OnNavigationFragmentInteractionListener,
        PreGameFragment.OnPreGameFragmentInteractionListener,
        AnnouncementsFragment.OnAnnouncementsFragmentInteractionListener,
        SettingsFragment.OnSettingsFragmentInteractionListener,
        BlockDesignFragment.OnBlockDesignFragmentInteractionListener,
        ShopFragment.OnShopFragmentInteractionListener,
        ThemesFragment.OnThemesFragmentInteractionListener {
    private SharedPreferences sharedPreferences;

    private void initialise() {
        sharedPreferences = getSharedPreferences("com.nerdcoredevelopment.game2048champsfinal", Context.MODE_PRIVATE);
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
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Following lines of code hide the status bar at the very top of the screen which battery
        indicator, network status other icons etc. Note, this is done before setting the layout with
        the line -> setContentView(R.layout.activity_main);
        */
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // To Disable screen rotation and keep the device in Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // To set the app theme to 'LIGHT' (even if 'DARK' theme is selected, however if user in their
        // settings enables 'DARK' theme for our individual app, then it will override the following line
        // no matter what)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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
                .replace(R.id.logo_lottie_fragment_container, logoLottieFragment, "LOGO_LOTTIE_FRAGMENT")
                .replace(R.id.navigation_fragment_container, navigationFragment, "NAVIGATION_FRAGMENT")
                .commit();
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onResume() {
        super.onResume();

        /* Persisting the screen settings even if the user leaves the app mid use for when he/she
           returns to use the app again
        */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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

    @Override
    public void onNavigationFragmentAnnouncementsClicked() {
        // If AnnouncementsFragment was opened and is currently on top, then return
        int countOfFragments = getSupportFragmentManager().getFragments().size();
        if (countOfFragments > 0) {
            Fragment topMostFragment = getSupportFragmentManager().getFragments().get(countOfFragments-1);
            if (topMostFragment != null && topMostFragment.getTag() != null && !topMostFragment.getTag().isEmpty()
                    && topMostFragment.getTag().equals("ANNOUNCEMENTS_FRAGMENT")) {
                return;
            }
        }

        AnnouncementsFragment fragment = new AnnouncementsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.replace(R.id.main_activity_full_screen_fragment_container,
                fragment, "ANNOUNCEMENTS_FRAGMENT").commit();
    }

    @Override
    public void onNavigationFragmentLeaderboardsClicked() {
        Toast.makeText(MainActivity.this, "Leaderboards Clicked", Toast.LENGTH_SHORT).show();
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

        SettingsFragment fragment = new SettingsFragment();
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
    public void onAnnouncementsFragmentInteractionBackClicked() {
        onBackPressed();
    }

    @Override
    public void onSettingsFragmentInteractionBackClicked() {
        onBackPressed();
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
    public void onSettingsFragmentInteractionChangeThemeClicked() {
        // If ThemesFragment was opened and is currently on top, then return
        int countOfFragments = getSupportFragmentManager().getFragments().size();
        if (countOfFragments > 0) {
            Fragment topMostFragment = getSupportFragmentManager().getFragments().get(countOfFragments-1);
            if (topMostFragment != null && topMostFragment.getTag() != null && !topMostFragment.getTag().isEmpty()
                    && topMostFragment.getTag().equals("THEMES_FRAGMENT")) {
                return;
            }
        }

        ThemesFragment fragment = new ThemesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.main_activity_full_screen_fragment_container,
                fragment, "THEMES_FRAGMENT").commit();
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
        // TODO -> Remove toast and implement the 'How To Play' fragment
        Toast.makeText(MainActivity.this, "'How To Play' button clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSettingsFragmentInteractionHelpClicked() {
        // TODO -> Remove toast and implement the 'Help' fragment where we would answer FAQs
        Toast.makeText(MainActivity.this, "'Help' button clicked", Toast.LENGTH_SHORT).show();
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
    public void onShopFragmentInteractionRestorePurchaseClicked() {
        Toast.makeText(MainActivity.this, "Restore Purchases Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShopFragmentInteractionUpdateCoins(int currentCoins) {
        updateCoins(currentCoins);
    }

    @Override
    public void onThemesFragmentInteractionBackClicked() {
        onBackPressed();
    }
}