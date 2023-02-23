package com.nerdcoredevelopment.game2048champsfinal.enums;

import static com.google.android.gms.games.achievement.Achievement.STATE_HIDDEN;
import static com.google.android.gms.games.achievement.Achievement.STATE_REVEALED;

import com.nerdcoredevelopment.game2048champsfinal.R;

import lombok.Getter;

@Getter
public enum EliminateValueToolAchievements {
    ELIMINATE_VALUE_TOOL_ACHIEVEMENT_LEVEL_1("Genin", R.string.achievement_genin,
            1, 100, STATE_REVEALED),
    ELIMINATE_VALUE_TOOL_ACHIEVEMENT_LEVEL_2("Chunin", R.string.achievement_chunin,
            2, 250, STATE_HIDDEN),
    ELIMINATE_VALUE_TOOL_ACHIEVEMENT_LEVEL_3("Jonin", R.string.achievement_jonin,
            3, 500, STATE_HIDDEN);

    private String nameOfAchievement; // Name as per the name given in the Google Play Games project
    private int achievementStringResourceId; // Achievement Id as per the Id generated in the Google Play Games project
    private int levelOfAchievement; // Custom information for us to keep track of the level of achievement completed
    private int achievementThresholdUseCount; // The assigned achievement threshold use count
    private int initialAchievementState; // The initial state of achievement as per the Google Play Games project

    EliminateValueToolAchievements(String nameOfAchievement, int achievementStringResourceId, int levelOfAchievement,
                                   int achievementThresholdUseCount, int initialAchievementState) {
        this.nameOfAchievement = nameOfAchievement;
        this.achievementStringResourceId = achievementStringResourceId;
        this.levelOfAchievement = levelOfAchievement;
        this.achievementThresholdUseCount = achievementThresholdUseCount;
        this.initialAchievementState = initialAchievementState;
    }
}
