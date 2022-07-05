package com.nerdcoredevelopment.game2048champsfinal.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/* TODO -> ## Bug ## : The sizes are all traversed using 'dimensions' string for a question mark
           string like '? X ?' so when call to getGameModeEnum() method is made this mode better be
           covered in the last else block, or else the app will crash
*/
// TODO -> Replace the current preview images with more HD images
@Getter
public enum GameModes {
    SQUARE_3X3(3, 3, "3 X 3", "SQUARE", 256, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
    }}, "square_3x3.jpg"), // Total 9 cells.

    SQUARE_4X4(4, 4, "4 X 4", "SQUARE", 2048, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
    }}, "square_4x4.jpg"), // Total 16 cells.

    SQUARE_5X5(5,5,"5 X 5","SQUARE",4096, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
    }}, "square_5x5.jpg"), // Total 25 cells.

    SQUARE_6X6(6,6,"? X ?","SQUARE",8192, false, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
    }}, "arriving_game_mode.jpg"), // Total 36 cells.

    RECTANGLE_3X4(3,4,"3 X 4","RECTANGLE",512, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
    }}, "rectangle_3x4.jpg"), // Total 12 cells.

    RECTANGLE_3X5(3,5,"3 X 5","RECTANGLE",1024, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0);}});
    }}, "rectangle_3x5.jpg"), // Total 15 cells.

    RECTANGLE_4X5(4,5,"4 X 5","RECTANGLE",2048, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
    }}, "rectangle_4x5.jpg"), // Total 20 cells.

    RECTANGLE_4X6(4,6,"? X ?","RECTANGLE",4096, false, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
    }}, "arriving_game_mode.jpg"), // Total 24 cells.

    /*
    RECTANGLE_5X6(5,6,"5 X 6","RECTANGLE",8192, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
    }}, "rectangle_5x6.jpg"), // Total 30 cells.
    */

    BLOCK_MIDDLE_5X5(5, 5, "5 X 5", "BLOCK MIDDLE", 2048, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(-1); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
    }}, "block_middle_5x5.jpg"), // Total 25 cells.

    BLOCK_MIDDLE_6X6(6,6,"? X ?","BLOCK MIDDLE",4096, false, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(-1); add(-1); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(-1); add(-1); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
    }}, "arriving_game_mode.jpg"), // Total 36 cells.

    BLOCK_2_CORNERS_4X4(4, 4, "4 X 4", "BLOCK 2 CORNERS", 2048, true, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(-1); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(-1);}});
    }}, "block_2_corners_4x4.jpg"), // Total 16 cells.

    BLOCK_2_CORNERS_5X5(5, 5, "? X ?", "BLOCK 2 CORNERS", 4096, false, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(-1); add(-1); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(-1); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(-1);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(-1); add(-1);}});
    }}, "arriving_game_mode.jpg"); // Total 25 cells.

    /*
    BLOCK_2_CORNERS_6X6(6,6,"6 X 6","BLOCK 2 CORNERS",8192, new ArrayList<ArrayList<Integer>>() {{
        add(new ArrayList<Integer>() {{add(-1); add(-1); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(-1); add(-1); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(0); add(0);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(-1); add(-1);}});
        add(new ArrayList<Integer>() {{add(0); add(0); add(0); add(0); add(-1); add(-1);}});
    }}, "block_2_corners_6x6.jpg"); // Total 36 cells.
    */

    private final int rows;
    private final int columns;
    private final String dimensions;
    private final String mode;
    private final int goal;
    private final boolean canAccess;
    private final ArrayList<ArrayList<Integer>> blockCells; // '-1' is for a block cell
    private final String gamePreviewAssetFileName;
    private final GameLayoutProperties gameLayoutProperties;

    GameModes(int columns, int rows, String dimensions, String mode, int goal, boolean canAccess,
              ArrayList<ArrayList<Integer>> blockCells, String gamePreviewAssetFileName) {
        this.rows = rows;
        this.columns = columns;
        this.dimensions = dimensions;
        this.mode = mode;
        this.goal = goal;
        this.canAccess = canAccess;
        this.blockCells = blockCells;
        this.gamePreviewAssetFileName = gamePreviewAssetFileName;
        this.gameLayoutProperties = GameLayoutProperties.valueOf(name() + "_LAYOUT_PROPERTIES");
    }

    public static List<String> getAllGameVariantsOfMode(String mode) {
        List<String> gameModeVariants = new ArrayList<>();
        for (GameModes gameMode : GameModes.values()) {
            if (gameMode.getMode().equals(mode)) {
                gameModeVariants.add(gameMode.getDimensions());
            }
        }
        return gameModeVariants;
    }

    public static List<String> getAllGameModes() {
        List<String> allGameModes = new ArrayList<>();
        for (GameModes gameMode : GameModes.values()) {
            if (!allGameModes.contains(gameMode.getMode())) {
                allGameModes.add(gameMode.getMode());
            }
        }
        return allGameModes;
    }

    public static GameModes getGameModeEnum(int rows, int columns, String mode) {
        switch (mode) {
            // For RECTANGLE board
            case "RECTANGLE": {
                if (columns == 3) {
                    return ((rows == 4) ? valueOf("RECTANGLE_3X4") : valueOf("RECTANGLE_3X5"));
                } else if (columns == 4) {
                    return valueOf("RECTANGLE_4X5");
                } else {
                    return valueOf("RECTANGLE_4X6");
                }
            }
            // For BLOCK MIDDLE board
            case "BLOCK MIDDLE": {
                if (rows == 5) {
                    return valueOf("BLOCK_MIDDLE_5X5");
                } else {
                    return valueOf("BLOCK_MIDDLE_6X6");
                }
            }
            // For BLOCK 2 CORNERS board
            case "BLOCK 2 CORNERS": {
                if (rows == 4) {
                    return valueOf("BLOCK_2_CORNERS_4X4");
                } else if (rows == 6) {
                    return valueOf("BLOCK_2_CORNERS_6X6");
                } else {
                    return valueOf("BLOCK_2_CORNERS_5X5");
                }
            }
            // For SQUARE board
            default: {
                if (rows == 3) {
                    return valueOf("SQUARE_3X3");
                } else if (rows == 4) {
                    return valueOf("SQUARE_4X4");
                } else if (rows == 5) {
                    return valueOf("SQUARE_5X5");
                } else {
                    return valueOf("SQUARE_6X6");
                }
            }
        }
    }
}
