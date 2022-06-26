package com.nerdcoredevelopment.game2048champsfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.nerdcoredevelopment.game2048champsfinal.enums.GameModes;

public class GameLayoutProvider {
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void provideGameFrameLayout(Context context, ConstraintLayout rootGameConstraintLayout,
                                              FrameLayout gameFrameLayout, GameModes gameMode) {
        float density = context.getResources().getDisplayMetrics().density;
        int padding = (int) (gameMode.getGameLayoutProperties().getSpacing() * density);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(rootGameConstraintLayout);
        constraintSet.setDimensionRatio(gameFrameLayout.getId(), gameMode.getGameLayoutProperties().getDimensionRatio());
        constraintSet.applyTo(rootGameConstraintLayout);

        GridLayout gameBackgroundGridLayout = new GridLayout(context);
        gameBackgroundGridLayout.setId(R.id.game_background_grid_layout);
        gameBackgroundGridLayout.setRowCount(gameMode.getRows());
        gameBackgroundGridLayout.setColumnCount(gameMode.getRows());
        gameBackgroundGridLayout.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_board));
        for (int i = 0; i < gameMode.getRows(); i++) {
            for (int j = 0; j < gameMode.getColumns(); j++) {
                AppCompatImageView imageView = new AppCompatImageView(context);
                if (gameMode.getBlockCells().get(i).get(j).equals(-1)) {
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.block_cell_x));
                } else {
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.rounded_corner_cell));
                }

                imageView.setTag("emptyCell" + i + j);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.height = 1;
                params.width = 1;
                params.topMargin = params.bottomMargin = params.leftMargin = params.rightMargin = padding;
                params.rowSpec = GridLayout.spec(i,1f);
                params.columnSpec = GridLayout.spec(j,1f);
                params.setGravity(Gravity.FILL);
                imageView.setLayoutParams(params);
                gameBackgroundGridLayout.addView(imageView);
            }
        }
        gameBackgroundGridLayout.setPadding(padding, padding, padding, padding);

        GridLayout gameGridLayout = new GridLayout(context);
        gameGridLayout.setId(R.id.game_grid_layout);
        gameGridLayout.setRowCount(gameMode.getRows());
        gameGridLayout.setColumnCount(gameMode.getColumns());
        for (int i = 0; i < gameMode.getRows(); i++) {
            for (int j = 0; j < gameMode.getColumns(); j++) {
                AppCompatTextView textView = new AppCompatTextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setTag("gameCell" + i + j);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_cell));
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.height = 1;
                params.width = 1;
                params.topMargin = params.bottomMargin = params.leftMargin = params.rightMargin = padding;
                params.rowSpec = GridLayout.spec(i,1f);
                params.columnSpec = GridLayout.spec(j,1f);
                params.setGravity(Gravity.FILL);
                textView.setLayoutParams(params);
                gameGridLayout.addView(textView);
            }
        }
        gameGridLayout.setPadding(padding, padding, padding, padding);

        gameFrameLayout.addView(gameBackgroundGridLayout);
        gameFrameLayout.addView(gameGridLayout);
    }
}
