import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Sprite keys match PNG names in assets/sprites, without the .png extension.
 * For example, DEMON loads assets/sprites/demon.png.
 */
public class GameSprites {

    public static final String 
        CHILD = "child", BURLY = "burly", HUMAN = "human";

    public static final String[] LOOT = {
        "cloak", "mask", "magicscroll", "talisman", "syringe"
    };
    public static final String[] POTION = {
        "blue", "red", "green", "orange", "black", "white"
    };
    public static final String[] MATERIAL = {
        "excalibur", "dagger", "grimoire", "smoke", "crystal"
    };
    public static final String[] CHEST_OPENING = {
        "chest_open_0", "chest_open_1", "chest_open_2",
        "chest_open_3", "chest_open_4", "chest_open_5"
    };
    public static final String[] KING_IDLE = {
        "king_idle_0", "king_idle_1", "king_idle_2", "king_idle_3",
        "king_idle_4", "king_idle_5", "king_idle_6", "king_idle_7"
    };
    public static final String[] DEMON_TRANSFORM = {
        "demon_transform_0", "demon_transform_1", "demon_transform_2", "demon_transform_3",
        "demon_transform_4", "demon_transform_5", "demon_transform_6",
    };
    public static final String[] DEMON_TO_KING = {
        "demon_to_king_0", "demon_to_king_1", "demon_to_king_2", "demon_to_king_3",
        "demon_to_king_4", "demon_to_king_5", "demon_to_king_6", "demon_to_king_7"
    };
    public static final String[] GENERAL_IDLE = {
        "general_idle_0", "general_idle_1", "general_idle_2", "general_idle_3",
        "general_idle_4", "general_idle_5", "general_idle_6", "general_idle_7"
    };
    public static final String[][] DEMON_HORDE_IDLE = {
        {
            "demon1_idle_0", "demon1_idle_1", "demon1_idle_2", "demon1_idle_3",
            "demon1_idle_4", "demon1_idle_5", "demon1_idle_6", "demon1_idle_7"
        },
        {
            "demon2_idle_0", "demon2_idle_1", "demon2_idle_2", "demon2_idle_3",
            "demon2_idle_4", "demon2_idle_5", "demon2_idle_6", "demon2_idle_7"
        },
        {
            "demon3_idle_0", "demon3_idle_1", "demon3_idle_2", "demon3_idle_3",
            "demon3_idle_4", "demon3_idle_5", "demon3_idle_6", "demon3_idle_7"
        }
    };
    public static final String[][] ASSASSIN_DEMON_IDLE = {
        {
            "assassin_demon1_idle_0", "assassin_demon1_idle_1", "assassin_demon1_idle_2", "assassin_demon1_idle_3",
            "assassin_demon1_idle_4", "assassin_demon1_idle_5", "assassin_demon1_idle_6", "assassin_demon1_idle_7"
        },
        {
            "assassin_demon2_idle_0", "assassin_demon2_idle_1", "assassin_demon2_idle_2", "assassin_demon2_idle_3",
            "assassin_demon2_idle_4", "assassin_demon2_idle_5", "assassin_demon2_idle_6", "assassin_demon2_idle_7"
        },
        {
            "assassin_demon3_idle_0", "assassin_demon3_idle_1", "assassin_demon3_idle_2", "assassin_demon3_idle_3",
            "assassin_demon3_idle_4", "assassin_demon3_idle_5", "assassin_demon3_idle_6", "assassin_demon3_idle_7"
        },
        {
            "assassin_demon4_idle_0", "assassin_demon4_idle_1", "assassin_demon4_idle_2", "assassin_demon4_idle_3",
            "assassin_demon4_idle_4", "assassin_demon4_idle_5", "assassin_demon4_idle_6", "assassin_demon4_idle_7"
        },
        {
            "assassin_demon5_idle_0", "assassin_demon5_idle_1", "assassin_demon5_idle_2", "assassin_demon5_idle_3",
            "assassin_demon5_idle_4", "assassin_demon5_idle_5", "assassin_demon5_idle_6", "assassin_demon5_idle_7"
        },
        {
            "assassin_demon6_idle_0", "assassin_demon6_idle_1", "assassin_demon6_idle_2", "assassin_demon6_idle_3",
            "assassin_demon6_idle_4", "assassin_demon6_idle_5", "assassin_demon6_idle_6", "assassin_demon6_idle_7"
        }
    };
    public static final String[] HEALER_SURVIVORS = {
        "healer_survivor1", "healer_survivor2", "healer_survivor3"
    };
    public static final String[] CHILD_WALK = {
        "child_walk_0", "child_walk_1", "child_walk_2"
    };

    private static final String[][] PLAYER_IDLE = {
        {
            "player1_idle_0", "player1_idle_1", "player1_idle_2", "player1_idle_3",
            "player1_idle_4", "player1_idle_5", "player1_idle_6"
        },
        {
            "player2_idle_0", "player2_idle_1", "player2_idle_2", "player2_idle_3",
            "player2_idle_4", "player2_idle_5", "player2_idle_6"
        },
        {
            "player3_idle_0", "player3_idle_1", "player3_idle_2", "player3_idle_3",
            "player3_idle_4", "player3_idle_5", "player3_idle_6"
        },
        {
            "player4_idle_0", "player4_idle_1", "player4_idle_2", "player4_idle_3",
            "player4_idle_4", "player4_idle_5", "player4_idle_6"
        },
        {
            "player5_idle_0", "player5_idle_1", "player5_idle_2", "player5_idle_3",
            "player5_idle_4", "player5_idle_5", "player5_idle_6"
        }
    };
    private static final String[][] PLAYER_WALK = {
        {"player1_walk_0", "player1_walk_1", "player1_walk_2"},
        {"player2_walk_0", "player2_walk_1", "player2_walk_2"},
        {"player3_walk_0", "player3_walk_1", "player3_walk_2"},
        {"player4_walk_0", "player4_walk_1", "player4_walk_2"},
        {"player5_walk_0", "player5_walk_1", "player5_walk_2"}
    };

    private static final Map<String, BufferedImage> CACHE = new HashMap<>();
    private static final BufferedImage EMPTY = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    public static BufferedImage get(String key) {
        return CACHE.computeIfAbsent(key, GameSprites::load);
    }

    public static int playerFrameCount(int role, boolean walking) {
        return playerFrames(role, walking).length;
    }

    /** Draws a frame using its explicit role-animation key from the lists above. */
    public static void drawPlayer(Graphics2D g, int role, boolean walking, int frame,
                                  int cx, int feetY, int h, boolean flip) {
        String[] frames = playerFrames(role, walking);
        String[] fallbackFrames = playerFrames(role, !walking);
        String frameKey = frames[Math.floorMod(frame, frames.length)];
        BufferedImage image = CACHE.computeIfAbsent(frameKey, key -> {
            BufferedImage png = load(key);
            if (png == EMPTY) png = load(fallbackFrames[Math.floorMod(frame, fallbackFrames.length)]);
            if (png == EMPTY) png = load(frames[0]);
            if (png == EMPTY) png = load(fallbackFrames[0]);
            return png;
        });
        draw(g, image, cx, feetY, h, flip);
    }

    /** Draws a sprite centered on cx, standing on feetY, scaled to height h (flip = face left). */
    public static void draw(Graphics2D g, String key, int cx, int feetY, int h, boolean flip) {
        draw(g, get(key), cx, feetY, h, flip);
    }

    private static BufferedImage load(String key) {
        BufferedImage png = GameImages.load("assets/sprites/" + key + ".png");
        return png == null ? EMPTY : png;
    }

    private static String[] playerFrames(int role, boolean walking) {
        if (role < 1 || role > PLAYER_IDLE.length)
            throw new IllegalArgumentException("Player role must be between 1 and 5: " + role);
        return (walking ? PLAYER_WALK : PLAYER_IDLE)[role - 1];
    }

    private static void draw(Graphics2D g, BufferedImage image, int cx, int feetY, int h, boolean flip) {
        int imageWidth = image.getWidth(), imageHeight = image.getHeight();
        int width = Math.max(1, imageWidth * h / imageHeight);
        int x = cx - width / 2, y = feetY - h;
        if (flip) g.drawImage(image, x + width, y, x, y + h, 0, 0, imageWidth, imageHeight, null);
        else g.drawImage(image, x, y, width, h, null);
    }
}
