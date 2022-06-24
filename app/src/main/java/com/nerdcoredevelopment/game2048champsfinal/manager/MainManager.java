package com.nerdcoredevelopment.game2048champsfinal.manager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.nerdcoredevelopment.game2048champsfinal.R;
import com.nerdcoredevelopment.game2048champsfinal.animations.AnimationUtility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainManager {
    private final Activity parentActivity;

    private final AppCompatImageView modeLeft;
    private final AppCompatImageView modeRight;
    private final AppCompatImageView sizeLeft;
    private final AppCompatImageView sizeRight;

    private final AppCompatImageView gamePreviewImage;
    private final LottieAnimationView rotatingLightView;
    private final List<Boolean> gamePreviewAnimationsDone; // 1st element is for shrink, 2nd for emerge

    public MainManager(Activity parentActivity) {
        this.parentActivity = parentActivity;

        modeLeft = parentActivity.findViewById(R.id.game_mode_left_arrow_image_view);
        modeRight = parentActivity.findViewById(R.id.game_mode_right_arrow_image_view);
        sizeLeft = parentActivity.findViewById(R.id.game_size_left_arrow_image_view);
        sizeRight = parentActivity.findViewById(R.id.game_size_right_arrow_image_view);

        gamePreviewImage = parentActivity.findViewById(R.id.game_preview_image_view);
        rotatingLightView = parentActivity.findViewById(R.id.game_preview_spotlight_lottie);
        gamePreviewAnimationsDone = new ArrayList<Boolean>(){{add(false); add(false);}};
    }

    public void updateModeBrowseIcons(String currentMode, List<String> allModes) {
        if (currentMode.equals(allModes.get(0)) &&
                !currentMode.equals(allModes.get(allModes.size() - 1))) {
            modeLeft.setVisibility(View.INVISIBLE);
            modeRight.setVisibility(View.VISIBLE);
        } else if (!currentMode.equals(allModes.get(0)) &&
                currentMode.equals(allModes.get(allModes.size() - 1))) {
            modeLeft.setVisibility(View.VISIBLE);
            modeRight.setVisibility(View.INVISIBLE);
        } else if (currentMode.equals(allModes.get(0)) &&
                currentMode.equals(allModes.get(allModes.size() - 1))) {
            modeLeft.setVisibility(View.INVISIBLE);
            modeRight.setVisibility(View.INVISIBLE);
        } else {
            modeLeft.setVisibility(View.VISIBLE);
            modeRight.setVisibility(View.VISIBLE);
        }
    }

    public void updateSizeBrowseIcons(String currentSize, List<String> allCurrentSizes) {
        if (currentSize.equals(allCurrentSizes.get(0)) &&
                !currentSize.equals(allCurrentSizes.get(allCurrentSizes.size() - 1))) {
            sizeLeft.setVisibility(View.INVISIBLE);
            sizeRight.setVisibility(View.VISIBLE);
        } else if (!currentSize.equals(allCurrentSizes.get(0)) &&
                currentSize.equals(allCurrentSizes.get(allCurrentSizes.size() - 1))) {
            sizeLeft.setVisibility(View.VISIBLE);
            sizeRight.setVisibility(View.INVISIBLE);
        } else if (currentSize.equals(allCurrentSizes.get(0)) &&
                currentSize.equals(allCurrentSizes.get(allCurrentSizes.size() - 1))) {
            sizeLeft.setVisibility(View.INVISIBLE);
            sizeRight.setVisibility(View.INVISIBLE);
        } else {
            sizeLeft.setVisibility(View.VISIBLE);
            sizeRight.setVisibility(View.VISIBLE);
        }
    }

    public void updatePreview(String gamePreviewAssetFileName) {
        AnimationUtility.gamePreviewShrinkAnimation(rotatingLightView, gamePreviewImage,
                modeLeft, modeRight, sizeLeft, sizeRight,
                250, gamePreviewAnimationsDone);

        /* 10000 ms -> Arbitrary long time for all animations to complete*/
        new CountDownTimer(10000, 25) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (gamePreviewAnimationsDone.get(0)) {
                    // Loading Image
                    try {
                        // get input stream
                        InputStream inputStream = parentActivity.getAssets().open(gamePreviewAssetFileName);
                        // load image as Drawable
                        Drawable iconDrawable = Drawable.createFromStream(inputStream, null);
                        // close stream
                        inputStream.close();
                        // set image to ImageView
                        gamePreviewImage.setImageDrawable(iconDrawable);
                    }
                    catch(IOException ex) {
                        ex.printStackTrace();
                    }

                    // Start Second Phase Animation
                    AnimationUtility.gamePreviewEmergeAnimation(rotatingLightView, gamePreviewImage,
                            modeLeft, modeRight, sizeLeft, sizeRight,
                            250, gamePreviewAnimationsDone);

                    // Cancel Current Timer
                    cancel();
                }
            }
            @Override
            public void onFinish() {}
        }.start();
    }
}
