package com.nerdcoredevelopment.game2048champsfinal.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.nerdcoredevelopment.game2048champsfinal.R;

public class SettingsFragment extends Fragment {
    private final static String FACEBOOK_URL = "https://www.facebook.com/Nerdcore-Development-109351035183956";
    private final static String FACEBOOK_PAGE_ID = "Nerdcore-Development-109351035183956";
    private final static String INSTAGRAM_URL = "https://www.instagram.com/nerdcoredev";
    private final static String TWITTER_USERNAME = "NerdcoreDev";
    private Context context;
    private OnSettingsFragmentInteractionListener mListener;
    private AppCompatImageView backButton;
    private LinearLayout rateUsLinearLayout;
    private LinearLayout feedbackLinearLayout;
    private LinearLayout facebookLinearLayout;
    private LinearLayout instagramLinearLayout;
    private LinearLayout twitterLinearLayout;
    private LinearLayout privacyLinearLayout;
    private LinearLayout termsLinearLayout;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    private Intent instagramProfileIntent(PackageManager packageManager) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (packageManager.getPackageInfo("com.instagram.android", 0) != null) {
                String username = SettingsFragment.INSTAGRAM_URL
                        .substring(SettingsFragment.INSTAGRAM_URL.lastIndexOf("/") + 1);
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(SettingsFragment.INSTAGRAM_URL));
        return intent;
    }

    private Intent twitterProfileIntent(PackageManager packageManager) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (packageManager.getPackageInfo("com.twitter.android", 0) != null) {
                intent.setData(Uri.parse("twitter://user?screen_name=" + TWITTER_USERNAME));
                intent.setPackage("com.twitter.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse("https://twitter.com/#!/" + TWITTER_USERNAME));
        return intent;
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
        rateUsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO -> A dialog should open suggesting user to give 5 star rating

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                Uri uriForApp = Uri.parse("market://details?id=" + context.getPackageName());
                Uri uriForBrowser = Uri.parse("http://play.google.com/store/apps/details?id="
                        + context.getPackageName());

                try {
                    browserIntent.setData(uriForApp);
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException exception) {
                    browserIntent.setData(uriForBrowser);
                    startActivity(browserIntent);
                }
            }
        });
        feedbackLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        facebookLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(context);
                browserIntent.setData(Uri.parse(facebookUrl));
                startActivity(browserIntent);
            }
        });
        instagramLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = instagramProfileIntent(context.getPackageManager());
                startActivity(browserIntent);
            }
        });
        twitterLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = twitterProfileIntent(context.getPackageManager());
                startActivity(browserIntent);
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        backButton = view.findViewById(R.id.title_back_settings_fragment_button);
        rateUsLinearLayout = view.findViewById(R.id.rate_us_linear_layout);
        feedbackLinearLayout = view.findViewById(R.id.feedback_linear_layout);
        facebookLinearLayout = view.findViewById(R.id.facebook_linear_layout);
        instagramLinearLayout = view.findViewById(R.id.instagram_linear_layout);
        twitterLinearLayout = view.findViewById(R.id.twitter_linear_layout);
        privacyLinearLayout = view.findViewById(R.id.privacy_policy_linear_layout);
        termsLinearLayout = view.findViewById(R.id.terms_of_service_linear_layout);

        settingOnClickListeners();

        return view;
    }

    public interface OnSettingsFragmentInteractionListener {
        void onSettingsFragmentInteractionBackClicked();
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