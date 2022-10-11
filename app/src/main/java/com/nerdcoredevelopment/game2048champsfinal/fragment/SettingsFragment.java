package com.nerdcoredevelopment.game2048champsfinal.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.nerdcoredevelopment.game2048champsfinal.BuildConfig;
import com.nerdcoredevelopment.game2048champsfinal.R;
import com.nerdcoredevelopment.game2048champsfinal.dialogs.RateUsPromptDialog;

public class SettingsFragment extends Fragment {
    private final static String FACEBOOK_URL = "www.facebook.com/Nerdcore-Development-109351035183956";
    private final static String FACEBOOK_PAGE_ID = "Nerdcore-Development-109351035183956";
    private final static String INSTAGRAM_URL = "www.instagram.com/nerdcoredev";
    private final static String TWITTER_USERNAME = "NerdcoreDev";
    private final static String DEVELOPER_MAIL_ID = "nerdcoredevelopment@gmail.com";
    private final static String FEEDBACK_MAIL_SUBJECT = "Feedback - 2048 Champs";
    private final static String FEEDBACK_MAIL_BODY = "Hi Nerdcore Team,\n\n";
    private Context context;
    private OnSettingsFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private AppCompatImageView backButton;
    private LinearLayout getPremiumLinearLayout;
    private LinearLayout changeThemeLinearLayout;
    private LinearLayout toggleRotatingLightLinearLayout;
    private SwitchCompat toggleRotatingLightSwitch;
    private LinearLayout blockDesignLinearLayout;
    private LinearLayout howToPlayLinearLayout;
    private LinearLayout helpLinearLayout;
    private LinearLayout rateUsLinearLayout;
    private LinearLayout feedbackLinearLayout;
    private LinearLayout facebookLinearLayout;
    private LinearLayout instagramLinearLayout;
    private LinearLayout twitterLinearLayout;
    private LinearLayout privacyLinearLayout;
    private LinearLayout termsLinearLayout;
    private LinearLayout exitLinearLayout;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void settingOnClickListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionBackClicked();
                }
            }
        });
        getPremiumLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionGetPremiumClicked();
                }
            }
        });
        changeThemeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionChangeThemeClicked();
                }
            }
        });
        toggleRotatingLightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggleRotatingLightSwitch.isChecked()) {
                    toggleRotatingLightSwitch.setChecked(false);
                    sharedPreferences.edit().putBoolean("toggleRotatingLight", false).apply();
                } else {
                    toggleRotatingLightSwitch.setChecked(true);
                    sharedPreferences.edit().putBoolean("toggleRotatingLight", true).apply();
                }

                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionToggleRotatingLightClicked(
                            sharedPreferences.getBoolean("toggleRotatingLight", true));
                }
            }
        });
        toggleRotatingLightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    sharedPreferences.edit().putBoolean("toggleRotatingLight", true).apply();
                } else {
                    sharedPreferences.edit().putBoolean("toggleRotatingLight", false).apply();
                }

                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionToggleRotatingLightClicked(
                            sharedPreferences.getBoolean("toggleRotatingLight", true));
                }
            }
        });
        blockDesignLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionBlockDesignClicked();
                }
            }
        });
        howToPlayLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionHowToPlayClicked();
                }
            }
        });
        helpLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionHelpClicked();
                }
            }
        });
        rateUsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateUsPromptDialog rateUsPromptDialog = new RateUsPromptDialog(context);
                rateUsPromptDialog.show();
                rateUsPromptDialog.setRateUsPromptDialogListener(new RateUsPromptDialog.RateUsPromptDialogListener() {
                    @Override
                    public void getResponseOfRateUsDialog(boolean response) {
                        if (response) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                            String packageName = "com.nerdcoredevelopment.game2048champsfinal";
                            Uri uriForApp = Uri.parse("market://details?id=" + packageName);
                            Uri uriForBrowser = Uri.parse("http://play.google.com/store/apps/details?id="
                                    + packageName);

                            try {
                                browserIntent.setData(uriForApp);
                                startActivity(browserIntent);
                            } catch (ActivityNotFoundException exception) {
                                browserIntent.setData(uriForBrowser);
                                startActivity(browserIntent);
                            }
                        }
                    }
                });
            }
        });
        feedbackLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String versionName = BuildConfig.VERSION_NAME;

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{DEVELOPER_MAIL_ID});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, FEEDBACK_MAIL_SUBJECT + " v" + versionName);
                emailIntent.putExtra(Intent.EXTRA_TEXT, FEEDBACK_MAIL_BODY);
                emailIntent.setSelector(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")));

                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        facebookLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriFacebook = "";
                PackageManager packageManager = context.getPackageManager();
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0);

                    if (applicationInfo.enabled) {
                        int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                        if (versionCode >= 3002850) { // newer versions of fb app
                            uriFacebook = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                        } else { // older versions of fb app
                            uriFacebook = "fb://page/" + FACEBOOK_PAGE_ID;
                        }
                    }
                } catch (Exception e) {
                    uriFacebook = FACEBOOK_URL; // normal web url
                }
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uriFacebook)));
            }
        });
        instagramLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = SettingsFragment.INSTAGRAM_URL
                        .substring(SettingsFragment.INSTAGRAM_URL.lastIndexOf("/") + 1);
                Uri uri = Uri.parse("http://instagram.com/_u/" + username);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.instagram.android");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/" + username)));
                }
            }
        });
        twitterLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("twitter://user?screen_name=" + TWITTER_USERNAME);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.twitter.android");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/#!/" + TWITTER_USERNAME)));
                }
            }
        });
        privacyLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://nerdcoredevelopment.com/privacy-policy.html"));
                startActivity(browserIntent);
            }
        });
        termsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://nerdcoredevelopment.com/terms-of-service.html"));
                startActivity(browserIntent);
            }
        });
        exitLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onSettingsFragmentInteractionExitClicked();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        sharedPreferences = context.getSharedPreferences("com.nerdcoredevelopment.game2048champsfinal", Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        backButton = view.findViewById(R.id.title_back_settings_fragment_button);
        getPremiumLinearLayout = view.findViewById(R.id.get_premium_linear_layout);
        changeThemeLinearLayout = view.findViewById(R.id.change_theme_linear_layout);
        toggleRotatingLightLinearLayout = view.findViewById(R.id.toggle_rotating_light_linear_layout);
        toggleRotatingLightSwitch = view.findViewById(R.id.toggle_rotating_light_switch);
        if (sharedPreferences.getBoolean("toggleRotatingLight", true)) {
            toggleRotatingLightSwitch.setChecked(true);
        } else {
            toggleRotatingLightSwitch.setChecked(false);
        }
        blockDesignLinearLayout = view.findViewById(R.id.block_design_linear_layout);
        howToPlayLinearLayout = view.findViewById(R.id.how_to_play_linear_layout);
        helpLinearLayout = view.findViewById(R.id.help_linear_layout);
        rateUsLinearLayout = view.findViewById(R.id.rate_us_linear_layout);
        feedbackLinearLayout = view.findViewById(R.id.feedback_linear_layout);
        facebookLinearLayout = view.findViewById(R.id.facebook_linear_layout);
        instagramLinearLayout = view.findViewById(R.id.instagram_linear_layout);
        twitterLinearLayout = view.findViewById(R.id.twitter_linear_layout);
        privacyLinearLayout = view.findViewById(R.id.privacy_policy_linear_layout);
        termsLinearLayout = view.findViewById(R.id.terms_of_service_linear_layout);
        exitLinearLayout = view.findViewById(R.id.exit_game_linear_layout);

        settingOnClickListeners();

        return view;
    }

    public interface OnSettingsFragmentInteractionListener {
        void onSettingsFragmentInteractionBackClicked();
        void onSettingsFragmentInteractionGetPremiumClicked();
        void onSettingsFragmentInteractionChangeThemeClicked();
        void onSettingsFragmentInteractionToggleRotatingLightClicked(boolean isChecked);
        void onSettingsFragmentInteractionBlockDesignClicked();
        void onSettingsFragmentInteractionHowToPlayClicked();
        void onSettingsFragmentInteractionHelpClicked();
        void onSettingsFragmentInteractionExitClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingsFragmentInteractionListener) {
            mListener = (OnSettingsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSettingsFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}