package com.nerdcoredevelopment.game2048champsfinal.enums;

import lombok.Getter;

@Getter
public enum GameOverDialogOptions {
    /* If the user wants to return to the main menu */
    MAIN_MENU,

    /* If the user wants to play the game again */
    PLAY_AGAIN,

    /* User has made one or more moves */
    UNDO_LAST_MOVE
}
