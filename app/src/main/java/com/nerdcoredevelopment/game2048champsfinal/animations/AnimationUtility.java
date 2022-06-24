package com.nerdcoredevelopment.game2048champsfinal.animations;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nerdcoredevelopment.game2048champsfinal.enums.Direction;
import com.nerdcoredevelopment.game2048champsfinal.enums.GameLayoutProperties;

import java.util.List;

public class AnimationUtility {
    public static void gamePreviewShrinkAnimation(LottieAnimationView rotatingLightView, AppCompatImageView gameImage,
                                                  AppCompatImageView modeLeft, AppCompatImageView modeRight,
                                                  AppCompatImageView sizeLeft, AppCompatImageView sizeRight,
                                                  int duration, List<Boolean> gamePreviewAnimationsDone) {
        ObjectAnimator gameImageShrinkXAnimator =
                ObjectAnimator.ofFloat(gameImage, View.SCALE_X, 1f, 0.5f).setDuration(duration);
        ObjectAnimator gameImageShrinkYAnimator =
                ObjectAnimator.ofFloat(gameImage, View.SCALE_Y, 1f, 0.5f).setDuration(duration);
        ObjectAnimator rotatingLightViewFade =
                ObjectAnimator.ofFloat(rotatingLightView, View.ALPHA, 1f, 0f).setDuration(duration);

        AnimatorSet setupGamePreviewAnimator = new AnimatorSet();
        setupGamePreviewAnimator.playTogether(gameImageShrinkXAnimator, gameImageShrinkYAnimator, rotatingLightViewFade);
        setupGamePreviewAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                modeLeft.setClickable(false); modeRight.setClickable(false);
                sizeLeft.setClickable(false); sizeRight.setClickable(false);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                gamePreviewAnimationsDone.set(0, true);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        setupGamePreviewAnimator.start();
    }

    public static void gamePreviewEmergeAnimation(LottieAnimationView rotatingLightView, AppCompatImageView gameImage,
                                                  AppCompatImageView modeLeft, AppCompatImageView modeRight,
                                                  AppCompatImageView sizeLeft, AppCompatImageView sizeRight,
                                                  int duration, List<Boolean> gamePreviewAnimationsDone) {
        ObjectAnimator gameImageEmergeXAnimator =
                ObjectAnimator.ofFloat(gameImage, View.SCALE_X, 0.5f, 1f).setDuration(duration);
        ObjectAnimator gameImageEmergeYAnimator =
                ObjectAnimator.ofFloat(gameImage, View.SCALE_Y, 0.5f, 1f).setDuration(duration);
        ObjectAnimator rotatingLightViewAppear =
                ObjectAnimator.ofFloat(rotatingLightView, View.ALPHA, 0f, 1f).setDuration(duration);

        AnimatorSet setupGamePreviewAnimator = new AnimatorSet();
        setupGamePreviewAnimator.playTogether(gameImageEmergeXAnimator, gameImageEmergeYAnimator, rotatingLightViewAppear);
        setupGamePreviewAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                gamePreviewAnimationsDone.set(0, false);
                modeLeft.setClickable(true); modeRight.setClickable(true);
                sizeLeft.setClickable(true); sizeRight.setClickable(true);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        setupGamePreviewAnimator.start();
    }

    private static void setTextViewAttributes(AppCompatTextView textView, int cellValue,
                                              int textColor, Drawable backgroundDrawable,
                                              GameLayoutProperties gameLayoutProperties) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(String.valueOf(cellValue));
        textView.setTextColor(textColor);
        textView.setTextSize(gameLayoutProperties.getTextSizeForValue(cellValue));
        textView.setBackground(backgroundDrawable);
    }

    /* Note: ViewGroup is the parent class for all the Layout classes and View is the parent class
    for all types of views
    */
    public static void executePopUpAnimation(AppCompatTextView textView, int cellValue, int textColor,
                                             Drawable backgroundDrawable, long duration, long delay,
                                             GameLayoutProperties gameLayoutProperties) {
        ObjectAnimator popUpXAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_X, 0.5f, 1f).setDuration(duration);
        ObjectAnimator popUpYAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 0.5f, 1f).setDuration(duration);
        AnimatorSet popUpAnimatorSet = new AnimatorSet();
        popUpAnimatorSet.playTogether(popUpXAnimator, popUpYAnimator);
        popUpAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setTextViewAttributes(textView, cellValue, textColor, backgroundDrawable, gameLayoutProperties);
                textView.setScaleX(0f);
                textView.setScaleY(0f);
            }
            @Override
            public void onAnimationEnd(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        popUpAnimatorSet.setStartDelay(delay);
        popUpAnimatorSet.start();
    }

    public static void undoResetState(AppCompatTextView textView, int cellValue, int textColor,
                                      Drawable backgroundDrawable, GameLayoutProperties gameLayoutProperties) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(String.valueOf(cellValue));
        textView.setTextColor(textColor);
        textView.setTextSize(gameLayoutProperties.getTextSizeForValue(cellValue));
        textView.setBackground(backgroundDrawable);
    }

    /**
     * The following is a substitute animation
     */
    public static AnimatorSet getEmptyAnimation(AppCompatTextView textView, long duration, long delay, int visibility) {
        AnimatorSet emptyAnimatorSet = new AnimatorSet();
        ObjectAnimator keepSameRotationAnimator = ObjectAnimator.ofFloat(textView, View.ROTATION, 0f, 0f).setDuration(duration);
        keepSameRotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textView.setVisibility(visibility);
            }
            @Override
            public void onAnimationEnd(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        emptyAnimatorSet.play(keepSameRotationAnimator);
        emptyAnimatorSet.setStartDelay(delay);
        return emptyAnimatorSet;
    }

    /**
     * The Following is the animation for showing a slide animation
     */
    public static AnimatorSet getSlideAnimation(int initRowPos, int initColumnPos, int finalRowPos, int finalColumnPos,
                                                int totalRows, int totalColumns, ViewGroup rootLayout, Direction direction,
                                                AppCompatTextView textView, long duration, long delay) {
        // Setting up slide animation in horizontal direction
        float pixelsToTravelX = textView.getWidth() +
                (float) (rootLayout.getWidth() - (totalColumns * textView.getWidth())) / (float) (totalColumns + 1);
        pixelsToTravelX *= Math.abs(initColumnPos - finalColumnPos);
        pixelsToTravelX = (direction == Direction.RIGHT) ? pixelsToTravelX : (-1 * pixelsToTravelX);
        ObjectAnimator slideXAnimator = ObjectAnimator.ofFloat(textView, View.TRANSLATION_X, pixelsToTravelX);
        float returnXPosition = textView.getTranslationX();
        slideXAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                textView.setVisibility(View.INVISIBLE);
                textView.setTranslationX(returnXPosition);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        slideXAnimator.setDuration(duration);

        // Setting up slide animation in vertical direction
        float pixelsToTravelY = textView.getHeight() +
                (float) (rootLayout.getHeight() - (totalRows * textView.getHeight())) / (float) (totalRows + 1);
        pixelsToTravelY *= Math.abs(initRowPos - finalRowPos);
        pixelsToTravelY = (direction == Direction.DOWN) ? pixelsToTravelY : (-1 * pixelsToTravelY);
        ObjectAnimator slideYAnimator = ObjectAnimator.ofFloat(textView, View.TRANSLATION_Y, pixelsToTravelY);
        float returnYPosition = textView.getTranslationY();
        slideYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                textView.setVisibility(View.INVISIBLE);
                textView.setTranslationY(returnYPosition);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        slideYAnimator.setDuration(duration);

        // Finally, choosing which animation to play
        AnimatorSet slideAnimatorSet = new AnimatorSet();
        slideAnimatorSet.play((direction == Direction.LEFT || direction == Direction.RIGHT) ?
                slideXAnimator : slideYAnimator);
        slideAnimatorSet.setStartDelay(delay);
        return slideAnimatorSet;
    }

    /**
     * The Following two are end part animations
     */
    public static AnimatorSet getSimplyAppearAnimation(AppCompatTextView textView, int cellValue, int textColor,
                                                       Drawable backgroundDrawable, long duration, long delay,
                                                       GameLayoutProperties gameLayoutProperties) {
        AnimatorSet simplyAppearAnimatorSet = new AnimatorSet();
        ObjectAnimator keepSameRotationAnimator = ObjectAnimator.ofFloat(textView, View.ROTATION, 0f, 0f).setDuration(duration);
        keepSameRotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setTextViewAttributes(textView, cellValue, textColor, backgroundDrawable, gameLayoutProperties);
            }
            @Override
            public void onAnimationEnd(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        simplyAppearAnimatorSet.play(keepSameRotationAnimator);
        simplyAppearAnimatorSet.setStartDelay(delay);
        return simplyAppearAnimatorSet;
    }

    public static AnimatorSet getMergeAnimation(AppCompatTextView textView, int cellValue, int textColor,
                                                Drawable backgroundDrawable, long duration, long delay,
                                                GameLayoutProperties gameLayoutProperties) {
        AnimatorSet mergeAnimatorSet = new AnimatorSet();
        ObjectAnimator keepSameRotationAnimator = ObjectAnimator.ofFloat(textView, View.ROTATION, 0f, 0f).setDuration(0);
        keepSameRotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setTextViewAttributes(textView, cellValue, textColor, backgroundDrawable, gameLayoutProperties);
            }
            @Override
            public void onAnimationEnd(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        ObjectAnimator mergeXAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_X, 0.5f, 1.15f).setDuration(duration);
        ObjectAnimator mergeYAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 0.5f, 1.15f).setDuration(duration);
        ObjectAnimator settleXAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_X, 1.15f, 1f).setDuration(0);
        ObjectAnimator settleYAnimator = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 1.15f, 1f).setDuration(0);
        mergeAnimatorSet.play(keepSameRotationAnimator).before(mergeXAnimator);
        mergeAnimatorSet.play(mergeXAnimator).with(mergeYAnimator);
        mergeAnimatorSet.play(settleXAnimator).with(settleYAnimator).after(mergeXAnimator);
        mergeAnimatorSet.setStartDelay(delay);
        return mergeAnimatorSet;
    }

    /**
     * The following are the animations for merging and splitting of the score displays in GameActivity
     */
    public static void mergeScoreDisplays(LinearLayout currentScoreLinearLayout, LinearLayout bestScoreLinearLayout,
                                          LottieAnimationView scoresMerge, float screenDensity, long duration) {
        int pixelAmount = 49; // This is a manual calculation as per the current state of the layout
        int slideByPixels = (int) (pixelAmount * screenDensity);

        ObjectAnimator currentScoreSlide = ObjectAnimator.ofFloat(currentScoreLinearLayout,
                View.TRANSLATION_X, slideByPixels).setDuration(duration);
        ObjectAnimator bestScoreSlide = ObjectAnimator.ofFloat(bestScoreLinearLayout,
                View.TRANSLATION_X, (-1 * slideByPixels)).setDuration(duration);
        AnimatorSet scoresAnimatorSet = new AnimatorSet();
        scoresAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}
            @Override
            public void onAnimationEnd(Animator animator) {
                currentScoreLinearLayout.setVisibility(View.INVISIBLE);
                scoresMerge.setVisibility(View.VISIBLE);
                scoresMerge.playAnimation();
                new CountDownTimer(4000, 4000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    @Override
                    public void onFinish() {
                        scoresMerge.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        scoresAnimatorSet.playTogether(currentScoreSlide, bestScoreSlide);
        scoresAnimatorSet.start();
    }

    public static void splitScoreDisplays(LinearLayout currentScoreLinearLayout, LinearLayout bestScoreLinearLayout,
                                          long duration) {
        int slideByPixels = 0;

        ObjectAnimator currentScoreSlide = ObjectAnimator.ofFloat(currentScoreLinearLayout,
                View.TRANSLATION_X, slideByPixels).setDuration(duration);
        ObjectAnimator bestScoreSlide = ObjectAnimator.ofFloat(bestScoreLinearLayout,
                View.TRANSLATION_X, slideByPixels).setDuration(duration);
        AnimatorSet scoresAnimatorSet = new AnimatorSet();
        scoresAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                currentScoreLinearLayout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animator) {}
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        scoresAnimatorSet.playTogether(currentScoreSlide, bestScoreSlide);
        scoresAnimatorSet.start();
    }
}