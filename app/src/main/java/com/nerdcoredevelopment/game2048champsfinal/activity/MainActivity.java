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
// TODO -> Remove smaller sized game modes as we get close to the publicity launch
// TODO -> Implement the In-app updates button in the SettingsFragment
// TODO -> Implement the In-app review feature working properly with quotas
/* TODO -> When it will be time to implement App Open Ad, change logo transition time to 3 seconds and splash screen time
           to 4 seconds
*/
/* TODO -> !! Bug !! -> If we do undo exactly after the move in which we completed the goal, then goal completion is done.
           But now if the user makes any move even a move which does not lead to goal completion the code block for goal
           completion is being played. This happens also when Change Value tool reverses the goal completion status. Also
           check if, after this bug happens in the game once, it somehow happens again and again. Additionally, look out for
           this bug after undo is done for game over and the first move is made after this undo (Check after bug has happened
           once before this)
*/
// TODO -> The width of the buttons in ShopFragment should be able to fit the text of the prices comfortably
/* TODO -> Implement a combo bar which fills up in 20 or 30 or 50 etc. steps, where each step is added on completion of a
           move in which merge occurs at 2 or more places i.e. a multi-merge move. When the bar fills up we reward the user
           with coins. As the progress bar fills up change it's color from red/orange to green or something like that, it
           will look cool and engaging
*/
/* TODO -> If In-App Review causes any Google Play Policy violation in 'Feed the Animal' project then we will not implement
           it in this app. But if it does not cause any violation then we can implement it in this app.
*/
/* TODO -> A list of tasks that need to be done w.r.t. the Review feature in an involuntary Pop-up manner, is as follows:
           (1) Decide if we want to implement In-App Review feature or keep the normal review feature
           (2) Decide the beginning DND (Do Not Disturb) period before which it should not be shown
           (3) Decide the freq. and place (for e.g. the homepage of the app etc.) where it should be shown
*/
/* TODO -> A list of tasks that need to be done w.r.t. the Update App feature in an involuntary Pop-up manner, is as follows:
           (1) Decide the place (for e.g. the homepage of the app etc.) and freq. with which it should be shown
           (2) Condition check to allow the Update App Pop Up Dialog to be triggered
           (3) Code for functionality of 'Remind Later' and 'Skip Version' buttons is pending
           (4) Handle the error condition with a dialog or something
           (5) The code is incomplete when it comes to checking internet connectivity. Write and test out a good working code
           which correctly checks internet connectivity
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
           (i) Friends (ii) Saved Games
           (iii) Events (iv) Player Stats
           (3) Fill up the details of the OAuth consent screen in GCP project like App logo, privacy policy link etc.
           (4) We need to instruct the user to share their gameplay activity to everyone in the Google Play Games app settings
           so that the score of the user can be submitted in the public version of a leaderboard & same data can be fetched
           and shown to the user in the app
*/
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