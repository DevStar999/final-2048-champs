package com.nerdcoredevelopment.game2048champsfinal;

import static com.google.android.gms.ads.RequestConfiguration.MAX_AD_CONTENT_RATING_G;
import static com.google.android.gms.ads.RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionLaunchCallback;
import com.qonversion.android.sdk.dto.QLaunchResult;

/** Application class that initializes, loads and show ads when activities change states. */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private void initialiseQonversion(int retryAttemptCount) {
        if (retryAttemptCount >= 10) {
            return;
        }

        // Qonversion.setDebugMode(); /* Always have this line commented out, un-comment only while testing */
        Qonversion.launch(this, "5KV0fBWmAdudW3_m6DuKt0F00M2FFDKo",
                false, new QonversionLaunchCallback() {
                    @Override
                    public void onSuccess(@NonNull QLaunchResult qLaunchResult) {}
                    @Override
                    public void onError(@NonNull QonversionError qonversionError) {
                        initialiseQonversion(retryAttemptCount + 1);
                    }
                }
        );
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);

        initialiseQonversion(0);

        // Initialising AdMob
        RequestConfiguration requestConfiguration = MobileAds.getRequestConfiguration().toBuilder()
                .setTagForChildDirectedTreatment(TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
                .setMaxAdContentRating(MAX_AD_CONTENT_RATING_G)
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {}
        });
    }

    /**
     * ActivityLifecycleCallback methods.
     */
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }
}