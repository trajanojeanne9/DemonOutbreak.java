import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * ALL CHARACTER / ENEMY / LOOT SPRITES live in this file (drawn in code as small pixel art).
 * To use your own art, put a PNG named after the key in  assets/sprites/<key>.png , e.g.
 *   player1_0.png, player1_1.png (walk frames for roles 1-5), demon.png, king.png, general.png, child.png,
 *   burly.png, survivors.png, chest_closed.png, chest_open.png, folder.png, human.png,
 *   loot1..loot5.png, potion1..potion6.png, mat0..mat4.png
 * PNGs can be any size: sprites are scaled to the height the game asks for.
 */
public class GameSprites {

    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    public static BufferedImage get(String key) {
        return CACHE.computeIfAbsent(key, k -> {
            BufferedImage png = GameImages.load("assets/sprites/" + k + ".png");
            return png != null ? png : make(k);
        });
    }

    /** Draws a sprite centered on cx, standing on feetY, scaled to height h (flip = face left). */
    public static void draw(Graphics2D g, String key, int cx, int feetY, int h, boolean flip) {
        BufferedImage im = get(key);
        int iw = im.getWidth(), ih = im.getHeight();
        int w = Math.max(1, iw * h / ih), x = cx - w / 2, y = feetY - h;
        if (flip) g.drawImage(im, x + w, y, x, y + h, 0, 0, iw, ih, null);
        else g.drawImage(im, x, y, w, h, null);
    }

    // ---------------------------------------------------------------

    private static BufferedImage make(String key) {
        if (key.startsWith("player")) return player(key.charAt(6) - '0', key.charAt(8) - '0');
        if (key.startsWith("loot"))   return loot(key.charAt(4) - '0');
        if (key.startsWith("potion")) return potion(key.charAt(6) - '0');
        if (key.startsWith("mat"))    return material(key.charAt(3) - '0');
        switch (key) {
            case "demon": return demon();
            case "king": return king();
            case "general": return general();
            case "child": return child();
            case "burly": return burly();
            case "survivors": return survivors();
            case "chest_closed": return chest(false);
            case "chest_open": return chest(true);
            case "folder": return folder();
            case "human": return human();
            default: {
                BufferedImage im = canvas(16, 16);
                Graphics2D g = im.createGraphics();
                r(g, 0xff00ff, 0, 0, 16, 16);
                return im;
            }
        }
    }

    private static BufferedImage canvas(int w, int h) { return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB); }
    private static void r(Graphics2D g, int rgb, int x, int y, int w, int h) { g.setColor(new Color(rgb)); g.fillRect(x, y, w, h); }

    // ---- the five heroes (32x48, facing right, 2 walk frames) ----
    private static BufferedImage player(int role, int f) {
        BufferedImage im = canvas(32, 48);
        Graphics2D g = im.createGraphics();
        int skin = 0xf1c27d, dark = 0x2b2b3a;
        int[] body = {0, 0x8d98a8, 0x2a2a35, 0x6a3fb5, 0xf2f2f2, 0xc86a1e};
        boolean robe = role == 3 || role == 4;
        if (robe) r(g, body[role], 8, 22, 16, 25);
        else if (f == 0) { r(g, dark, 10, 35, 5, 13); r(g, dark, 17, 35, 5, 13); }
        else             { r(g, dark, 8, 35, 5, 12);  r(g, dark, 18, 35, 5, 13); }
        r(g, body[role], 9, 20, 14, 16);
        r(g, role == 2 ? dark : skin, 5, 22, 4, 11);
        r(g, role == 2 ? dark : skin, 23, 22, 4, 11);
        r(g, skin, 10, 6, 12, 13);
        r(g, 0x222222, 13, 11, 2, 3);
        r(g, 0x222222, 18, 11, 2, 3);
        switch (role) {
            case 1:  // Warrior: helmet, shield, sword
                r(g, 0x9aa4b4, 9, 3, 14, 6); r(g, 0xc0392b, 14, 0, 4, 4);
                r(g, 0x2f5db3, 2, 22, 7, 13); r(g, 0xffd24a, 4, 26, 3, 5);
                r(g, 0xe6e9ef, 26, 8, 3, 24); r(g, 0x7a4a21, 23, 32, 9, 2); r(g, 0x7a4a21, 26, 34, 3, 4);
                break;
            case 2:  // Assassin: hood, mask, scarf, dagger
                r(g, 0x15151c, 9, 3, 14, 7); r(g, 0x15151c, 10, 14, 12, 5);
                r(g, 0xb3262e, 9, 18, 14, 3);
                r(g, 0xd8dde6, 26, 26, 2, 9); r(g, 0x555555, 25, 35, 4, 2);
                break;
            case 3:  // Mage: pointy hat, beard, staff
                g.setColor(new Color(0x4b2a8a));
                g.fillPolygon(new int[]{16, 7, 25}, new int[]{0, 9, 9}, 3);
                r(g, 0x4b2a8a, 6, 8, 20, 3); r(g, 0xf1c40f, 10, 7, 12, 1);
                r(g, 0xeeeeee, 12, 15, 8, 5);
                r(g, 0x7a4a21, 27, 8, 2, 38); r(g, 0x6fe3ff, 25, 3, 6, 6);
                break;
            case 4:  // Healer: white hood, red cross, staff
                r(g, 0xf5f5f5, 9, 3, 14, 6); r(g, 0xf5f5f5, 9, 3, 2, 14); r(g, 0xf5f5f5, 21, 3, 2, 14);
                r(g, 0xd62839, 14, 24, 4, 9); r(g, 0xd62839, 11, 27, 10, 3);
                r(g, 0x2e9e5b, 8, 38, 16, 2);
                r(g, 0x7a4a21, 27, 12, 2, 34); r(g, 0x2e9e5b, 25, 8, 6, 5);
                break;
            default: // Craftmaster: hair, goggles, apron, hammer
                r(g, 0x5b3a1e, 10, 4, 12, 4);
                r(g, 0xc9a227, 10, 9, 12, 3); r(g, 0x6fe3ff, 12, 9, 3, 3); r(g, 0x6fe3ff, 17, 9, 3, 3);
                r(g, 0x6b3f1d, 11, 24, 10, 12);
                r(g, 0x7a4a21, 26, 18, 3, 18); r(g, 0x8d98a8, 23, 15, 9, 5);
        }
        g.dispose();
        return im;
    }

    private static BufferedImage demon() {
        BufferedImage im = canvas(32, 40);
        Graphics2D g = im.createGraphics();
        r(g, 0x2a1a1a, 7, 0, 3, 7); r(g, 0x2a1a1a, 22, 0, 3, 7);
        r(g, 0xb02a2a, 9, 4, 14, 12);
        r(g, 0xffe14a, 11, 9, 3, 3); r(g, 0xffe14a, 18, 9, 3, 3);
        r(g, 0x1a0a0a, 12, 14, 8, 2); r(g, 0xffffff, 13, 14, 1, 1); r(g, 0xffffff, 18, 14, 1, 1);
        r(g, 0x8f2020, 8, 16, 16, 15);
        r(g, 0xb02a2a, 3, 17, 5, 12); r(g, 0xb02a2a, 24, 17, 5, 12);
        r(g, 0xeeeeee, 3, 29, 1, 3); r(g, 0xeeeeee, 6, 29, 1, 3); r(g, 0xeeeeee, 25, 29, 1, 3); r(g, 0xeeeeee, 28, 29, 1, 3);
        r(g, 0x6a1515, 10, 31, 5, 9); r(g, 0x6a1515, 17, 31, 5, 9);
        r(g, 0xb02a2a, 27, 30, 4, 2);
        g.dispose();
        return im;
    }

    private static BufferedImage king() {
        BufferedImage im = canvas(64, 80);
        Graphics2D g = im.createGraphics();
        r(g, 0x7a0f1c, 6, 30, 52, 48);
        r(g, 0x3a1f5c, 16, 28, 32, 34); r(g, 0x4b2a7a, 22, 34, 20, 6);
        r(g, 0x4b2a7a, 6, 30, 10, 28); r(g, 0x4b2a7a, 48, 30, 10, 28);
        r(g, 0xeeeedd, 6, 58, 3, 6); r(g, 0xeeeedd, 12, 58, 3, 6); r(g, 0xeeeedd, 49, 58, 3, 6); r(g, 0xeeeedd, 55, 58, 3, 6);
        r(g, 0x2a1540, 18, 62, 10, 18); r(g, 0x2a1540, 36, 62, 10, 18);
        r(g, 0xe8e0c0, 12, 0, 6, 18); r(g, 0xe8e0c0, 46, 0, 6, 18);
        r(g, 0x5a3590, 20, 10, 24, 20);
        r(g, 0xf1c40f, 22, 6, 20, 6); r(g, 0xf1c40f, 22, 2, 4, 4); r(g, 0xf1c40f, 30, 0, 4, 6); r(g, 0xf1c40f, 38, 2, 4, 4);
        r(g, 0xff2a2a, 25, 17, 6, 4); r(g, 0xff2a2a, 35, 17, 6, 4);
        r(g, 0x120818, 27, 25, 10, 3); r(g, 0xffffff, 28, 25, 2, 2); r(g, 0xffffff, 34, 25, 2, 2);
        r(g, 0x7a4a21, 58, 20, 3, 50); r(g, 0xff2a2a, 55, 14, 9, 9);
        g.dispose();
        return im;
    }

    private static BufferedImage general() {
        BufferedImage im = canvas(48, 64);
        Graphics2D g = im.createGraphics();
        r(g, 0x2a1a1a, 10, 0, 4, 10); r(g, 0x2a1a1a, 34, 0, 4, 10);
        r(g, 0xc23a2a, 14, 6, 20, 16);
        r(g, 0xffe14a, 18, 12, 4, 3); r(g, 0xffe14a, 27, 12, 4, 3);
        r(g, 0x1a0a0a, 19, 18, 10, 2);
        r(g, 0x5a5f6e, 10, 22, 28, 22);
        r(g, 0x7b8194, 4, 20, 10, 8); r(g, 0x7b8194, 34, 20, 10, 8);
        r(g, 0xc23a2a, 4, 28, 7, 18); r(g, 0xc23a2a, 37, 28, 7, 18);
        r(g, 0x7a1a1a, 14, 44, 8, 20); r(g, 0x7a1a1a, 26, 44, 8, 20);
        r(g, 0x7a4a21, 43, 10, 3, 48); r(g, 0xcfd5df, 38, 8, 10, 12);
        g.dispose();
        return im;
    }

    private static BufferedImage child() {
        BufferedImage im = canvas(20, 28);
        Graphics2D g = im.createGraphics();
        r(g, 0xf1c27d, 5, 2, 10, 9); r(g, 0x3a2a1e, 5, 1, 10, 3);
        r(g, 0x222222, 7, 6, 2, 2); r(g, 0x222222, 12, 6, 2, 2);
        r(g, 0x7a3a5a, 6, 9, 3, 2); r(g, 0xffffff, 12, 2, 3, 3);
        r(g, 0x8a8076, 4, 11, 12, 12);
        r(g, 0xf1c27d, 2, 12, 2, 8); r(g, 0xf1c27d, 16, 12, 2, 8);
        r(g, 0xf1c27d, 6, 23, 3, 5); r(g, 0xf1c27d, 11, 23, 3, 5);
        g.dispose();
        return im;
    }

    private static BufferedImage burly() {
        BufferedImage im = canvas(36, 52);
        Graphics2D g = im.createGraphics();
        r(g, 0x3a2f2a, 9, 36, 8, 16); r(g, 0x3a2f2a, 19, 36, 8, 16);
        r(g, 0x7a4a21, 5, 14, 26, 23); r(g, 0x2a1a10, 5, 32, 26, 3);
        r(g, 0xd9a066, 0, 15, 6, 19); r(g, 0xd9a066, 30, 15, 6, 19);
        r(g, 0xd9a066, 11, 2, 14, 13); r(g, 0x2a1a10, 11, 10, 14, 6);
        r(g, 0x111111, 14, 7, 2, 2); r(g, 0x111111, 20, 7, 2, 2);
        r(g, 0xaaaaaa, 31, 32, 5, 2);
        g.dispose();
        return im;
    }

    private static BufferedImage survivors() {
        BufferedImage im = canvas(60, 36);
        Graphics2D g = im.createGraphics();
        int[] cols = {0x6a8caf, 0x8fae6a, 0xaf6a6a};
        for (int i = 0; i < 3; i++) {
            int x = i * 20;
            r(g, 0xf1c27d, x + 6, 4, 8, 8);
            r(g, cols[i], x + 4, 12, 12, 14);
            r(g, 0xf1c27d, x + 1, 6, 3, 10); r(g, 0xf1c27d, x + 16, 6, 3, 10);
            r(g, 0x333333, x + 6, 26, 3, 10); r(g, 0x333333, x + 11, 26, 3, 10);
        }
        g.dispose();
        return im;
    }

    private static BufferedImage chest(boolean open) {
        BufferedImage im = canvas(32, 24);
        Graphics2D g = im.createGraphics();
        r(g, 0x7a4a21, 2, 10, 28, 14);
        r(g, 0xc9a227, 2, 14, 28, 3); r(g, 0xc9a227, 14, 12, 4, 8);
        if (!open) {
            r(g, 0x8d5a2b, 2, 2, 28, 8); r(g, 0xc9a227, 2, 5, 28, 2); r(g, 0xffe27a, 14, 9, 4, 4);
        } else {
            r(g, 0x6a3f1d, 2, 0, 28, 5);
            r(g, 0xffd34a, 4, 6, 24, 5); r(g, 0xffffff, 8, 6, 2, 2); r(g, 0xffffff, 20, 7, 2, 2);
        }
        g.dispose();
        return im;
    }

    private static BufferedImage folder() {
        BufferedImage im = canvas(24, 20);
        Graphics2D g = im.createGraphics();
        r(g, 0xd9b86a, 1, 4, 22, 15); r(g, 0xd9b86a, 1, 2, 9, 3);
        r(g, 0xf2dc9a, 3, 7, 18, 10); r(g, 0xb23b3b, 6, 10, 8, 2);
        g.dispose();
        return im;
    }

    private static BufferedImage human() {
        BufferedImage im = canvas(32, 48);
        Graphics2D g = im.createGraphics();
        r(g, 0x2b2b3a, 10, 40, 5, 8); r(g, 0x2b2b3a, 17, 40, 5, 8);
        r(g, 0x6a3fb5, 8, 20, 16, 22);
        r(g, 0xf1c27d, 5, 22, 4, 11); r(g, 0xf1c27d, 23, 22, 4, 11);
        r(g, 0xf1c27d, 10, 6, 12, 13);
        r(g, 0x222222, 13, 11, 2, 3); r(g, 0x222222, 18, 11, 2, 3);
        r(g, 0xc9c9c9, 10, 4, 12, 3);
        r(g, 0xf1c40f, 11, 1, 10, 4);
        g.dispose();
        return im;
    }

    // ---- loot items (24x24): 1 Cloak, 2 Mask, 3 Scroll, 4 Talisman, 5 Syringe ----
    private static BufferedImage loot(int id) {
        BufferedImage im = canvas(24, 24);
        Graphics2D g = im.createGraphics();
        switch (id) {
            case 1:
                g.setColor(new Color(0x4a6fe3)); g.fillPolygon(new int[]{12, 22, 2}, new int[]{2, 22, 22}, 3);
                r(g, 0xffd24a, 10, 4, 4, 4); r(g, 0xffffff, 8, 13, 2, 2); r(g, 0xffffff, 14, 16, 2, 2);
                break;
            case 2:
                r(g, 0xf5f5f5, 3, 5, 18, 14); r(g, 0x222222, 6, 9, 4, 3); r(g, 0x222222, 14, 9, 4, 3);
                r(g, 0xcccccc, 11, 12, 2, 4); r(g, 0x7a4a21, 0, 10, 3, 2); r(g, 0x7a4a21, 21, 10, 3, 2);
                break;
            case 3:
                r(g, 0xf2e2b0, 5, 3, 14, 18); r(g, 0xd9b86a, 3, 2, 18, 3); r(g, 0xd9b86a, 3, 19, 18, 3);
                r(g, 0x6a4a2a, 8, 8, 8, 1); r(g, 0x6a4a2a, 8, 11, 8, 1); r(g, 0x6a4a2a, 8, 14, 6, 1);
                r(g, 0xb23b3b, 14, 15, 4, 4);
                break;
            case 4:
                r(g, 0xf5d442, 7, 2, 10, 20); r(g, 0xb23b3b, 9, 5, 6, 1); r(g, 0xb23b3b, 11, 5, 2, 12);
                r(g, 0xb23b3b, 9, 9, 6, 1); r(g, 0xb23b3b, 11, 22, 2, 2);
                break;
            default:
                r(g, 0xcfe8f5, 6, 9, 11, 6); r(g, 0x4cd964, 8, 10, 7, 4);
                r(g, 0x999999, 2, 11, 4, 2); r(g, 0x999999, 1, 9, 2, 6);
                r(g, 0xdddddd, 17, 11, 6, 1); r(g, 0x999999, 17, 8, 2, 8);
        }
        g.dispose();
        return im;
    }

    // ---- potions (16x24): 1 Blue, 2 Red, 3 Green, 4 Orange, 5 Black, 6 White ----
    private static BufferedImage potion(int id) {
        BufferedImage im = canvas(16, 24);
        Graphics2D g = im.createGraphics();
        int[] cols = {0, 0x3b82f6, 0xdc2626, 0x22c55e, 0xf97316, 0x222222, 0xf5f5f5};
        r(g, 0x8d5a2b, 6, 0, 4, 3);
        r(g, 0xcfe8f5, 6, 3, 4, 5);
        r(g, cols[id], 4, 7, 8, 1);
        r(g, cols[id], 3, 8, 10, 14);
        r(g, cols[id], 4, 22, 8, 1);
        r(g, id == 6 ? 0xd0d8e8 : id == 5 ? 0x666666 : 0xffffff, 5, 10, 2, 7);
        g.dispose();
        return im;
    }

    // ---- boss materials (32x32): 0 Excalibur, 1 Dagger of Life, 2 Grimoire, 3 Poisonous Smoke, 4 Elixir ----
    private static BufferedImage material(int m) {
        BufferedImage im = canvas(32, 32);
        Graphics2D g = im.createGraphics();
        switch (m) {
            case 0:
                r(g, 0xe6e9ef, 14, 1, 4, 21); r(g, 0xffffff, 15, 2, 1, 18);
                r(g, 0xf1c40f, 8, 22, 16, 3); r(g, 0x7a4a21, 14, 25, 4, 5); r(g, 0x3b82f6, 14, 30, 4, 2);
                break;
            case 1:
                r(g, 0xcfd5df, 14, 6, 4, 14); r(g, 0x4cd964, 15, 8, 2, 8);
                r(g, 0x555555, 10, 20, 12, 3); r(g, 0x2a1a10, 14, 23, 4, 6); r(g, 0xd62839, 14, 29, 4, 2);
                break;
            case 2:
                r(g, 0x5a2d91, 6, 4, 20, 24); r(g, 0x3d1d63, 6, 4, 3, 24);
                r(g, 0xffd24a, 14, 13, 6, 6); r(g, 0xffd24a, 24, 14, 2, 6); r(g, 0xf2e2b0, 9, 27, 16, 1);
                break;
            case 3:
                r(g, 0x4a4f5e, 10, 14, 12, 14); r(g, 0x6a6f7e, 12, 10, 8, 4);
                g.setColor(new Color(0x7ddf8a));
                g.fillOval(6, 2, 10, 10); g.fillOval(14, 0, 12, 12); g.fillOval(10, 6, 10, 8);
                break;
            default:
                g.setColor(new Color(0xd62839)); g.fillOval(7, 12, 18, 18);
                r(g, 0xcfe8f5, 13, 4, 6, 10); r(g, 0x8d5a2b, 13, 2, 6, 3); r(g, 0xffffff, 11, 16, 3, 5);
        }
        g.dispose();
        return im;
    }
}
