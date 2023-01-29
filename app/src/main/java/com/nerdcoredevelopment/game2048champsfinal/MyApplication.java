package com.nerdcoredevelopment.game2048champsfinal;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;

import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionLaunchCallback;
import com.qonversion.android.sdk.dto.QLaunchResult;

/** Application class that initializes, loads and show ads when activities change states. */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private void initialiseQonversion() {
        // Qonversion.setDebugMode(); /* Always have this line commented out, un-comment only while testing */
        Qonversion.launch(this, "5KV0fBWmAdudW3_m6DuKt0F00M2FFDKo",
                false, new QonversionLaunchCallback() {
                    @Override
                    public void onSuccess(@NonNull QLaunchResult qLaunchResult) {}
                    @Override
                    public void onError(@NonNull QonversionError qonversionError) {
                        initialiseQonversion();
                    }
                }
        );

        PlayGamesSdk.initialize(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);

        initialiseQonversion();
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