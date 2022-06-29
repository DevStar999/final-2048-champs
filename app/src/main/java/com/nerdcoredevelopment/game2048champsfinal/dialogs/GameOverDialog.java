package com.nerdcoredevelopment.game2048champsfinal.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nerdcoredevelopment.game2048champsfinal.R;

/* TODO -> ## Bug ## : We need an answer among one of the two options given in this dialog from the
           user. If in case it happens that we get an unexpected action or event from user then we
           need to handle that in such a way that undo is called automatically (Starting a
           new game is more beneficial to us then letting the user go to the menu as there are
           higher chances that they might stop playing the game for now)
*/
public class GameOverDialog extends Dialog {
    private LottieAnimationView gameOverLottie;
    private AppCompatTextView gameOverText;
    private LinearLayout gameOverButtonsLinearLayout;
    private AppCompatButton gameOverMainMenu;
    private AppCompatButton gameOverPlayAgain;
    private GameOverDialogListener gameOverDialogListener;

    private void initialise() {
        gameOverLottie = findViewById(R.id.game_over_lottie);
        gameOverText = findViewById(R.id.game_over_text);
        gameOverButtonsLinearLayout = findViewById(R.id.game_over_buttons_linear_layout);
        gameOverMainMenu = findViewById(R.id.game_over_main_menu);
        gameOverPlayAgain = findViewById(R.id.game_over_play_again);
    }

    private void setVisibilityOfViews(int visibility) {
        gameOverLottie.setVisibility(visibility);
        gameOverText.setVisibility(visibility);
        gameOverButtonsLinearLayout.setVisibility(visibility);
    }

    public GameOverDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogTheme);
        setContentView(R.layout.dialog_game_over);

        initialise();

        gameOverMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // First, the views will disappear, then the dialog box will close
                setVisibilityOfViews(View.INVISIBLE);
                new CountDownTimer(100, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    @Override
                    public void onFinish() {
                        gameOverDialogListener.getResponseOfOverDialog(false);
                        dismiss();
                    }
                }.start();
            }
        });
        gameOverPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // First, the views will disappear, then the dialog box will close
                setVisibilityOfViews(View.INVISIBLE);
                new CountDownTimer(100, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    @Override
                    public void onFinish() {
                        gameOverDialogListener.getResponseOfOverDialog(true);
                        dismiss();
                    }
                }.start();
            }
        });
    }

    @Override
    public void show() {
        // Set the dialog to not focusable.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        // Show the dialog!
        super.show();

        // Set the dialog to immersive sticky mode
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Clear the not focusable flag from the window
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        // First, the dialog box will open, then the views will show
        new CountDownTimer(400, 400) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                setVisibilityOfViews(View.VISIBLE);
            }
        }.start();
    }

    public void setGameOverDialogListener(GameOverDialogListener gameOverDialogListener) {
        this.gameOverDialogListener = gameOverDialogListener;
    }

    public interface GameOverDialogListener {
        void getResponseOfOverDialog(boolean response);
    }
}
