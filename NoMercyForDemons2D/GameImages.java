import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * ALL BACKGROUNDS + ALL BUTTON / UI IMAGES live in this file.
 * Every background is drawn in code so the game runs with no art files. To use your own art, drop a
 * PNG named after the key into  assets/backgrounds/<key>.png  (e.g. assets/backgrounds/forest.png,
 * any size, it is stretched to 960x540) and it replaces the generated one.
 */
public class GameImages {

    // ---- background keys (one per scenario) ----
    public static final String TITLE = "title", CITY = "city", HIDEOUT = "hideout", FOREST = "forest",
        DUNGEON_ENTRANCE = "dungeonEntrance", DUNGEON_HALL = "dungeonHall", TREASURE = "treasure",
        TOWN = "town", ROAD = "road", DARK_ROAD = "darkRoad", BARRIER = "barrier",
        SURVIVORS = "survivors", LAB = "lab", LAIR = "lair", ENDING_WIN = "endingWin", ENDING_LOSE = "endingLose";

    /** Where the three dungeon doors are drawn in the hall background. */
    public static final int[] DOOR_X = {470, 640, 810};
    public static final int DOOR_W = 110, DOOR_H = 190;

    private static final int W = GamePanel.W, H = GamePanel.H, GROUND = GamePanel.FEET_Y - 10;
    private static final Map<String, BufferedImage> BG = new HashMap<>();
    private static final Map<String, BufferedImage> UI = new HashMap<>();

    // =====================================================================
    //  BACKGROUNDS
    // =====================================================================

    public static BufferedImage background(String key) {
        return BG.computeIfAbsent(key, k -> {
            BufferedImage png = load("backgrounds/" + k + ".jpe");
            if (png == null) return make(k);
            BufferedImage scaled = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaled.createGraphics();
            g.drawImage(png, 0, 0, W, H, null);
            g.dispose();
            return scaled;
        });
    }

    static BufferedImage load(String path) {
        try { File f = new File(path); if (f.isFile()) return ImageIO.read(f); }
        catch (Exception e) { /* fall back to the generated image */ }
        return null;
    }

    private static BufferedImage make(String key) {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Random r = new Random(key.hashCode());
        switch (key) {
            case TITLE: title(g, r); break;
            case CITY: city(g, r); break;
            case HIDEOUT: hideout(g); break;
            case FOREST: forest(g, r); break;
            case DUNGEON_ENTRANCE: dungeonEntrance(g, r); break;
            case DUNGEON_HALL: dungeonHall(g); break;
            case TREASURE: treasure(g, r); break;
            case TOWN: town(g, r); break;
            case ROAD: road(g, r); break;
            case DARK_ROAD: darkRoad(g, r); break;
            case BARRIER: barrier(g, r); break;
            case SURVIVORS: survivors(g); break;
            case LAB: lab(g, r); break;
            case LAIR: lair(g, r); break;
            case ENDING_WIN: endingWin(g); break;
            case ENDING_LOSE: endingLose(g, r); break;
            default: g.setColor(Color.DARK_GRAY); g.fillRect(0, 0, W, H);
        }
        g.dispose();
        return img;
    }

    // ---- drawing helpers ----
    private static Color c(int rgb) { return new Color(rgb); }

    private static void sky(Graphics2D g, Color a, Color b) {
        g.setPaint(new GradientPaint(0, 0, a, 0, GROUND, b));
        g.fillRect(0, 0, W, GROUND);
    }

    private static void ground(Graphics2D g, Color top, Color bottom) {
        g.setPaint(new GradientPaint(0, GROUND, top, 0, H, bottom));
        g.fillRect(0, GROUND, W, H - GROUND);
        g.setColor(top.brighter());
        g.fillRect(0, GROUND, W, 4);
    }

    private static void stars(Graphics2D g, Random r, int n, int maxY) {
        g.setColor(new Color(255, 255, 255, 190));
        for (int i = 0; i < n; i++) g.fillRect(r.nextInt(W), r.nextInt(maxY), 2, 2);
    }

    private static void tree(Graphics2D g, int x, int base, int h, Color trunk, Color leaf) {
        g.setColor(trunk);
        g.fillRect(x - h / 14, base - h / 3, h / 7, h / 3);
        g.setColor(leaf);
        for (int i = 0; i < 3; i++) {
            int w = h / 2 - i * h / 12, y = base - h / 4 - i * h / 4;
            g.fillPolygon(new int[]{x - w, x + w, x}, new int[]{y, y, y - h / 2}, 3);
        }
    }

    private static void deadTree(Graphics2D g, int x, int base, int h, Color col) {
        g.setColor(col);
        g.fillRect(x - 4, base - h, 8, h);
        g.setStroke(new BasicStroke(5));
        g.drawLine(x, base - h * 2 / 3, x - 38, base - h);
        g.drawLine(x, base - h / 2, x + 34, base - h * 3 / 4);
        g.drawLine(x, base - h, x + 14, base - h - 26);
        g.setStroke(new BasicStroke(1));
    }

    private static void bricks(Graphics2D g, int y0, int y1, Color col) {
        g.setColor(col);
        g.fillRect(0, y0, W, y1 - y0);
        g.setColor(col.darker());
        int row = 0;
        for (int y = y0; y < y1; y += 30, row++) {
            g.drawLine(0, y, W, y);
            for (int x = (row % 2) * 30; x < W; x += 60) g.drawLine(x, y, x, Math.min(y + 30, y1));
        }
    }

    private static void torch(Graphics2D g, int x, int y) {
        g.setColor(new Color(255, 160, 40, 45)); g.fillOval(x - 70, y - 80, 140, 140);
        g.setColor(c(0x5a3c1e));                g.fillRect(x - 4, y, 8, 38);
        g.setColor(c(0xff9a1e));                g.fillOval(x - 12, y - 28, 24, 34);
        g.setColor(c(0xffe05a));                g.fillOval(x - 6, y - 14, 12, 20);
    }

    private static void fire(Graphics2D g, int x, int y) {
        g.setColor(new Color(255, 120, 20, 210)); g.fillOval(x - 18, y - 44, 36, 54);
        g.setColor(new Color(255, 225, 90, 230)); g.fillOval(x - 9, y - 28, 18, 32);
    }

    // ---- the scenarios ----

    private static void title(Graphics2D g, Random r) {
        sky(g, c(0x120a24), c(0x6b1520));
        stars(g, r, 90, 200);
        g.setColor(new Color(255, 80, 60, 40)); g.fillOval(670, 20, 180, 180);
        g.setColor(c(0xc23a2a));                g.fillOval(700, 50, 120, 120);
        g.setColor(c(0x0b0614));
        g.fillRect(560, 180, 300, 170);
        for (int i = 0; i < 5; i++) g.fillRect(560 + i * 64, 160, 36, 30);
        g.fillRect(600, 90, 50, 100);  g.fillPolygon(new int[]{594, 625, 656}, new int[]{92, 50, 92}, 3);
        g.fillRect(780, 110, 50, 80);  g.fillPolygon(new int[]{774, 805, 836}, new int[]{112, 70, 112}, 3);
        g.setColor(c(0xffb030));
        for (int i = 0; i < 6; i++) g.fillRect(585 + i * 45, 215 + (i % 2) * 40, 12, 20);
        ground(g, c(0x1a0f26), c(0x0b0614));
        for (int x : new int[]{60, 200, 330}) deadTree(g, x, GROUND, 120, c(0x0b0614));
    }

    private static void city(Graphics2D g, Random r) {
        sky(g, c(0x2a1030), c(0xd2552a));
        g.setColor(new Color(40, 30, 40, 150));
        for (int i = 0; i < 8; i++) g.fillOval(r.nextInt(900), 30 + r.nextInt(120), 180 + r.nextInt(120), 50 + r.nextInt(30));
        for (int layer = 0; layer < 2; layer++) {
            g.setColor(layer == 0 ? c(0x3a1e2c) : c(0x1c0e18));
            for (int x = -20; x < W; x += 60 + r.nextInt(30)) {
                int h = 70 + r.nextInt(130) + layer * 25;
                g.fillRect(x, GROUND - h, 55, h);
                g.fillPolygon(new int[]{x, x + 55, x + 25 + r.nextInt(20)},
                              new int[]{GROUND - h, GROUND - h, GROUND - h - 15 - r.nextInt(25)}, 3);
            }
        }
        ground(g, c(0x2d1c1c), c(0x120a0a));
        for (int x : new int[]{150, 420, 700, 880}) fire(g, x, GROUND);
    }

    private static void hideout(Graphics2D g) {
        bricks(g, 0, GROUND, c(0x3a4150));
        for (int x : new int[]{150, 480, 810}) {
            g.setColor(c(0x8e1b22));
            g.fillRect(x - 40, 40, 80, 150);
            g.fillPolygon(new int[]{x - 40, x + 40, x}, new int[]{190, 190, 225}, 3);
            g.setColor(c(0xe8e0d0));
            g.fillRect(x - 4, 70, 8, 80);
            g.fillRect(x - 22, 90, 44, 8);
        }
        torch(g, 315, 150);
        torch(g, 645, 150);
        ground(g, c(0x6b4a2b), c(0x3d2914));
    }

    private static void forest(Graphics2D g, Random r) {
        sky(g, c(0x0d1b24), c(0x2c4a45));
        stars(g, r, 40, 140);
        g.setColor(c(0xdfe8d0)); g.fillOval(120, 50, 70, 70);
        for (int x = -20; x < W; x += 70) tree(g, x + r.nextInt(30), GROUND - 20, 170 + r.nextInt(60), c(0x1a1410), c(0x12301f));
        ground(g, c(0x24421f), c(0x0e1d10));
        for (int x = 20; x < W; x += 190) tree(g, x + r.nextInt(50), GROUND + 12, 240 + r.nextInt(70), c(0x2a1c12), c(0x1c4a2a));
    }

    private static void dungeonEntrance(Graphics2D g, Random r) {
        sky(g, c(0x20242e), c(0x4a4f5e));
        stars(g, r, 50, 150);
        g.setColor(c(0x3c404c));
        g.fillPolygon(new int[]{480, 640, 760, 960, 960}, new int[]{GROUND, 140, 70, 160, GROUND}, 5);
        g.setColor(c(0x585d6d)); g.fillArc(700, 128, 190, 150, 0, 180); g.fillRect(700, 203, 190, GROUND - 203);
        g.setColor(Color.BLACK); g.fillArc(725, 150, 140, 120, 0, 180); g.fillRect(725, 210, 140, GROUND - 210);
        torch(g, 688, 230);
        torch(g, 902, 230);
        ground(g, c(0x4a4a4f), c(0x26262b));
    }

    private static void dungeonHall(Graphics2D g) {
        bricks(g, 0, GROUND, c(0x34363f));
        Color[] dc = {c(0x2e8b3a), c(0xc47a1c), c(0x8e1b22)};      // poison, traps, general demon
        for (int i = 0; i < 3; i++) {
            int x = DOOR_X[i] - DOOR_W / 2, y = GROUND - DOOR_H;
            g.setColor(new Color(dc[i].getRed(), dc[i].getGreen(), dc[i].getBlue(), 70));
            g.fillOval(x - 40, y - 30, DOOR_W + 80, DOOR_H + 60);
            g.setColor(c(0x6a6e7e)); g.fillRoundRect(x - 8, y - 8, DOOR_W + 16, DOOR_H + 48, 60, 60);
            g.setColor(dc[i].darker().darker()); g.fillRoundRect(x, y, DOOR_W, DOOR_H + 40, 50, 50);
            g.setColor(dc[i]);
            for (int p = 1; p < 4; p++) g.fillRect(x + p * DOOR_W / 4 - 2, y + 60, 4, DOOR_H);
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SERIF, Font.BOLD, 34));
            g.drawString(String.valueOf(i + 1), x + DOOR_W / 2 - 9, y + 48);
        }
        for (int x : new int[]{395, 555, 725, 895}) torch(g, x, 130);
        torch(g, 90, 130);
        ground(g, c(0x50525c), c(0x2a2b33));
    }

    private static void treasure(Graphics2D g, Random r) {
        bricks(g, 0, GROUND, c(0x2c2236));
        torch(g, 130, 140);
        torch(g, 830, 140);
        ground(g, c(0x6a5330), c(0x2d2412));
        for (int i = 0; i < 6; i++) {
            int x = 60 + i * 150 + r.nextInt(40);
            g.setColor(c(0xd9a521));
            g.fillPolygon(new int[]{x - 50, x + 50, x}, new int[]{GROUND + 6, GROUND + 6, GROUND - 30 - r.nextInt(20)}, 3);
            g.setColor(c(0xffe27a));
            for (int k = 0; k < 8; k++) g.fillOval(x - 40 + r.nextInt(80), GROUND - 25 + r.nextInt(30), 6, 6);
        }
    }

    private static void town(Graphics2D g, Random r) {
        sky(g, c(0xc9a36a), c(0xe9d1a4));
        g.setColor(new Color(255, 240, 200, 160)); g.fillOval(760, 40, 110, 110);
        for (int x = -10; x < W; x += 110 + r.nextInt(40)) {
            int w = 90 + r.nextInt(30), h = 70 + r.nextInt(70);
            g.setColor(c(0x8a6a48)); g.fillRect(x, GROUND - h, w, h);
            g.setColor(c(0x4a3322)); g.fillPolygon(new int[]{x - 8, x + w + 8, x + w / 2}, new int[]{GROUND - h, GROUND - h, GROUND - h - 35}, 3);
            g.setColor(c(0x2a1c12)); g.fillRect(x + 15, GROUND - h + 20, 18, 24); g.fillRect(x + w - 33, GROUND - h + 20, 18, 24);
        }
        ground(g, c(0x9c7a4f), c(0x5c4630));
        g.setColor(c(0x4a3a28));
        for (int i = 0; i < 14; i++) { int x = r.nextInt(W), y = GROUND + 10 + r.nextInt(180); g.drawLine(x, y, x + 20 + r.nextInt(40), y + r.nextInt(14) - 7); }
        deadTree(g, 90, GROUND, 130, c(0x3a2a1c));
        deadTree(g, 880, GROUND, 110, c(0x3a2a1c));
    }

    private static void road(Graphics2D g, Random r) {
        sky(g, c(0x4a4f57), c(0x8a8e94));
        g.setColor(c(0x5d626a));
        g.fillPolygon(new int[]{0, 200, 420, 640, 960, 960, 0}, new int[]{GROUND, 200, 250, 190, 230, GROUND, GROUND}, 7);
        for (int x : new int[]{80, 330, 560, 900}) deadTree(g, x, GROUND, 100 + r.nextInt(50), c(0x2a2a2a));
        ground(g, c(0x5a5a52), c(0x2e2e2a));
        g.setColor(c(0x3e3e38)); g.fillRect(0, GROUND + 30, W, 70);
        g.setColor(c(0x77776c));
        for (int i = 0; i < 18; i++) g.fillOval(r.nextInt(W), GROUND + 10 + r.nextInt(180), 10 + r.nextInt(14), 7 + r.nextInt(8));
    }

    private static void darkRoad(Graphics2D g, Random r) {
        sky(g, c(0x1a0508), c(0x7a1a1a));
        g.setColor(c(0xb02a2a)); g.fillOval(420, 40, 140, 140);
        g.setColor(c(0x0d0304));
        for (int x = 0; x < W; x += 90 + r.nextInt(40)) {
            int h = 90 + r.nextInt(120);
            g.fillPolygon(new int[]{x, x + 40, x + 20}, new int[]{GROUND, GROUND, GROUND - h}, 3);
        }
        ground(g, c(0x24120f), c(0x0d0605));
        g.setColor(c(0xcfc6b0));
        for (int i = 0; i < 10; i++) g.fillOval(r.nextInt(W), GROUND + 20 + r.nextInt(150), 8, 5);
    }

    private static void barrier(Graphics2D g, Random r) {
        sky(g, c(0x1a1040), c(0x5a3a9a));
        stars(g, r, 70, 200);
        g.setColor(c(0x25204a));
        for (int x : new int[]{100, 260, 420, 880}) { int h = 110 + r.nextInt(80); g.fillRect(x, GROUND - h, 36, h); g.fillRect(x - 6, GROUND - h - 8, 48, 10); }
        ground(g, c(0x3a3a55), c(0x1b1b2c));
    }

    private static void survivors(Graphics2D g) {
        bricks(g, 0, GROUND, c(0x4a3b2e));
        g.setColor(c(0x2a1c12));
        for (int x = 0; x < W; x += 240) g.fillRect(x, 0, 16, GROUND);
        g.fillRect(0, 40, W, 14);
        torch(g, 200, 150);
        torch(g, 560, 150);
        torch(g, 900, 150);
        ground(g, c(0x4a3b2e), c(0x241b13));
    }

    private static void lab(Graphics2D g, Random r) {
        g.setColor(c(0x2f4a4f)); g.fillRect(0, 0, W, GROUND);
        g.setColor(c(0x3c5d63));
        for (int x = 0; x < W; x += 80) g.fillRect(x, 0, 4, GROUND);
        int[] flask = {0x7ddf8a, 0xd62839, 0x3b82f6, 0xf1c40f, 0xb07af0};
        for (int sx : new int[]{70, 360, 650}) {
            g.setColor(c(0x5a4630)); g.fillRect(sx, 120, 230, 8); g.fillRect(sx, 220, 230, 8);
            for (int k = 0; k < 5; k++) {
                g.setColor(c(flask[r.nextInt(5)])); g.fillOval(sx + 10 + k * 44, 88, 26, 32);
                g.setColor(c(0xcfe8f5)); g.fillRect(sx + 17 + k * 44, 76, 12, 14);
                g.setColor(c(flask[r.nextInt(5)])); g.fillOval(sx + 10 + k * 44, 188, 26, 32);
                g.setColor(c(0xcfe8f5)); g.fillRect(sx + 17 + k * 44, 176, 12, 14);
            }
        }
        g.setColor(new Color(80, 255, 140, 130)); g.fillRoundRect(870, 90, 70, 220, 30, 30);
        g.setColor(c(0xcfe8f5)); g.drawRoundRect(870, 90, 70, 220, 30, 30);
        ground(g, c(0x8a9aa0), c(0x4a5a60));
        g.setColor(c(0x6f8085));
        for (int x = 0; x < W; x += 80) g.drawLine(x, GROUND, x - 40, H);
        for (int y = GROUND + 40; y < H; y += 45) g.drawLine(0, y, W, y);
    }

    private static void lair(Graphics2D g, Random r) {
        sky(g, c(0x1a0306), c(0x8a1c10));
        g.setColor(c(0x0a0203));
        for (int x = -40; x < W; x += 80) { int h = 90 + r.nextInt(130); g.fillPolygon(new int[]{x, x + 100, x + 50}, new int[]{GROUND - 40, GROUND - 40, GROUND - 40 - h}, 3); }
        g.setPaint(new GradientPaint(0, 270, c(0xff7a18), 0, GROUND, c(0xb01808)));
        g.fillRect(0, 270, W, GROUND - 270);
        g.setColor(c(0x140808));
        g.fillRect(60, 80, 44, GROUND - 80); g.fillRect(40, 70, 84, 14);
        g.fillRect(860, 80, 44, GROUND - 80); g.fillRect(840, 70, 84, 14);
        ground(g, c(0x2a1412), c(0x0f0605));
        g.setColor(new Color(255, 160, 40, 170));
        for (int i = 0; i < 45; i++) g.fillOval(r.nextInt(W), 120 + r.nextInt(380), 3, 3);
    }

    private static void endingWin(Graphics2D g) {
        sky(g, c(0xf7b15a), c(0xfce9a8));
        g.setColor(c(0xfff2b0)); g.fillOval(380, 170, 200, 200);
        g.setColor(c(0x7ab05a)); g.fillOval(-200, 250, 700, 300); g.fillOval(400, 270, 800, 300);
        ground(g, c(0x5a9a4a), c(0x2d5a28));
    }

    private static void endingLose(Graphics2D g, Random r) {
        sky(g, c(0x0a0a10), c(0x3a2230));
        stars(g, r, 60, 200);
        g.setColor(c(0x8e1b22)); g.fillOval(400, 60, 110, 110);
        for (int x : new int[]{110, 300, 700, 860}) deadTree(g, x, GROUND, 120, c(0x050508));
        ground(g, c(0x1c1a1f), c(0x09090c));
    }

    // =====================================================================
    //  BUTTONS / TEXT BOX / CURSOR
    // =====================================================================

    /** The framed box used for the dialogue box, the HUD and the name form. */
    public static BufferedImage panel(int w, int h) {
        return UI.computeIfAbsent("panel" + w + "x" + h, k -> {
            BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = im.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setPaint(new GradientPaint(0, 0, c(0x1e2a55), 0, h, c(0x0c1230)));
            g.fill(new RoundRectangle2D.Float(2, 2, w - 4, h - 4, 18, 18));
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(3));
            g.draw(new RoundRectangle2D.Float(2, 2, w - 5, h - 5, 18, 18));
            g.setColor(c(0x7f94d8));
            g.setStroke(new BasicStroke(1));
            g.draw(new RoundRectangle2D.Float(7, 7, w - 15, h - 15, 12, 12));
            g.dispose();
            return im;
        });
    }

    /** Menu button; selected = highlighted (gold) version. */
    public static BufferedImage button(int w, int h, boolean selected) {
        return UI.computeIfAbsent("button" + w + "x" + h + selected, k -> {
            BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = im.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setPaint(selected ? new GradientPaint(0, 0, c(0xffe27a), 0, h, c(0xf0a830))
                                : new GradientPaint(0, 0, c(0x4a68b0), 0, h, c(0x273d78)));
            g.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, 12, 12));
            g.setColor(selected ? Color.WHITE : c(0xcfd9ff));
            g.setStroke(new BasicStroke(2));
            g.draw(new RoundRectangle2D.Float(1, 1, w - 3, h - 3, 12, 12));
            g.dispose();
            return im;
        });
    }

    /** The little arrow shown next to the highlighted button. */
    public static BufferedImage cursor() {
        return UI.computeIfAbsent("cursor", k -> {
            BufferedImage im = new BufferedImage(14, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = im.createGraphics();
            g.setColor(c(0x7a1a1a));
            g.fillPolygon(new int[]{0, 13, 0}, new int[]{0, 8, 16}, 3);
            g.dispose();
            return im;
        });
    }
}
