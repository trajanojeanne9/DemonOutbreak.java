import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Node;

/**
 * BACKGROUNDS + BUTTON / UI IMAGES live in this file.
 * Scene backgrounds use images from the assets folders. Missing background art is transparent.
 * Images are stretched to 960x540.
 * Buttons / text box / cursor can be replaced too, from  assets/ui/ :
 *   button.png, button_selected.png      every menu button (normal / highlighted)
 *   button_<label>.png, button_<label>_selected.png   one specific button, e.g. button_yes.png,
 *                                        button_path_1_poison.png, button_warrior_selected.png
 *   panel.png (text box, HUD, name form), cursor.png (arrow next to the highlighted button)
 * The label is lower-case with every run of other characters turned into "_".
 */
public class GameImages {

    // ---- background keys (one per scenario) ----
    public static final String TITLE = "title", CITY = "city", HIDEOUT = "hideout", FOREST = "forest1",
        FOREST_UNEASY = "forest2", FOREST_AMBUSH = "forest3",
        DUNGEON_ENTRANCE = "dungeonEntrance", DUNGEON_HALL = "dungeonHall",
        DUNGEON_PASSAGE = "dungeonPassage", TREASURE = "treasure",
        TOWN = "town", ROAD = "beggar", DARK_ROAD = "darkRoad", BARRIER = "barrier",
        SURVIVORS = "survivor", LAB = "lab", LAIR = "lair",
        LAIR_APPROACH_1 = "lairApproach1", LAIR_APPROACH_2 = "lairApproach2",
        LAIR_APPROACH_3 = "lairApproach3",
        ENDING_WIN = "endingWin", ENDING_LOSE = "endingLose";

    /** Where the three dungeon doors are drawn in the hall background. */
    public static final int[] DOOR_X = {135, 480, 825};
    public static final int[] DOOR_LIGHT_W = {120, 190, 120};
    public static final int DOOR_LIGHT_H = 245;

    private static final int W = GamePanel.W, H = GamePanel.H;
    private static final Map<String, BufferedImage> BG = new HashMap<>();
    private static final Map<String, AnimatedBackground> ANIMATED_BG = new HashMap<>();
    private static final Map<String, BufferedImage> UI = new HashMap<>();

    // =====================================================================
    //  BACKGROUNDS
    // =====================================================================

    public static BufferedImage background(String key) {
        return BG.computeIfAbsent(key, k -> {
            BufferedImage image = loadBackground(k);
            if (image == null) return new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
            BufferedImage scaled = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaled.createGraphics();
            g.drawImage(image, 0, 0, W, H, null);
            g.dispose();
            return scaled;
        });
    }

    private static BufferedImage loadBackground(String key) {
        String[] folders = {"assets/background/", "assets/backgrounds/", "backgorund/", "background/"};
        String[] extensions = {".png", ".jpg", ".jpeg", ".jpe"};
        for (String folder : folders)
            for (String extension : extensions) {
                BufferedImage image = load(folder + key + extension);
                if (image != null) return image;
            }
        return null;
    }

    public static BufferedImage backgroundFrame(String key, long elapsedMillis) {
        if (!LAIR.equals(key)) return background(key);
        AnimatedBackground animation = ANIMATED_BG.computeIfAbsent(key, GameImages::loadAnimatedBackground);
        return animation == null ? background(key) : animation.frameAt(elapsedMillis);
    }

    private static AnimatedBackground loadAnimatedBackground(String key) {
        for (String path : new String[]{
                "assets/backgrounds/" + key + ".gif",
                "assets/background/" + key + ".gif",
                "backgorund/" + key + ".gif",
                "background/" + key + ".gif"}) {
            File file = new File(path);
            if (file.isFile()) {
                try {
                    return readGif(file);
                } catch (IOException e) {
                    System.err.println("Could not load animated background " + file + ": " + e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    private static AnimatedBackground readGif(File file) throws IOException {
        try (ImageInputStream input = ImageIO.createImageInputStream(file)) {
            if (input == null) throw new IOException("Unable to open image stream");
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) throw new IOException("No GIF reader is available");
            ImageReader reader = readers.next();
            try {
                reader.setInput(input, false, false);
                int width = reader.getWidth(0), height = reader.getHeight(0);
                Node streamMetadata = reader.getStreamMetadata() == null
                    ? null : reader.getStreamMetadata().getAsTree("javax_imageio_gif_stream_1.0");
                Node screen = child(streamMetadata, "LogicalScreenDescriptor");
                if (screen != null) {
                    width = intAttribute(screen, "logicalScreenWidth", width);
                    height = intAttribute(screen, "logicalScreenHeight", height);
                }

                int count = reader.getNumImages(true);
                if (count < 1) throw new IOException("GIF contains no frames");
                List<BufferedImage> frames = new ArrayList<>();
                List<Integer> delays = new ArrayList<>();
                BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                BufferedImage restoreCanvas = null;
                Rectangle previousBounds = null;
                String previousDisposal = "none";

                for (int i = 0; i < count; i++) {
                    if ("restoreToBackgroundColor".equals(previousDisposal) && previousBounds != null) {
                        Graphics2D clear = canvas.createGraphics();
                        clear.setComposite(AlphaComposite.Clear);
                        clear.fill(previousBounds);
                        clear.dispose();
                    } else if ("restoreToPrevious".equals(previousDisposal) && restoreCanvas != null) {
                        canvas = copy(restoreCanvas);
                    }

                    Node metadata = reader.getImageMetadata(i).getAsTree("javax_imageio_gif_image_1.0");
                    Node descriptor = child(metadata, "ImageDescriptor");
                    int left = intAttribute(descriptor, "imageLeftPosition", 0);
                    int top = intAttribute(descriptor, "imageTopPosition", 0);
                    int frameWidth = intAttribute(descriptor, "imageWidth", reader.getWidth(i));
                    int frameHeight = intAttribute(descriptor, "imageHeight", reader.getHeight(i));
                    Node control = child(metadata, "GraphicControlExtension");
                    String disposal = stringAttribute(control, "disposalMethod", "none");
                    int delay = intAttribute(control, "delayTime", 10);

                    restoreCanvas = "restoreToPrevious".equals(disposal) ? copy(canvas) : null;
                    BufferedImage frame = reader.read(i);
                    Graphics2D draw = canvas.createGraphics();
                    draw.drawImage(frame, left, top, left + frameWidth, top + frameHeight,
                                   0, 0, frame.getWidth(), frame.getHeight(), null);
                    draw.dispose();

                    BufferedImage scaled = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D scale = scaled.createGraphics();
                    scale.drawImage(canvas, 0, 0, W, H, null);
                    scale.dispose();
                    frames.add(scaled);
                    delays.add(Math.max(20, delay * 10));
                    previousBounds = new Rectangle(left, top, frameWidth, frameHeight);
                    previousDisposal = disposal;
                }
                return new AnimatedBackground(frames, delays);
            } finally {
                reader.dispose();
            }
        }
    }

    private static Node child(Node parent, String name) {
        if (parent == null) return null;
        for (Node node = parent.getFirstChild(); node != null; node = node.getNextSibling())
            if (name.equals(node.getNodeName())) return node;
        return null;
    }

    private static int intAttribute(Node node, String name, int fallback) {
        if (node == null || node.getAttributes() == null || node.getAttributes().getNamedItem(name) == null)
            return fallback;
        try { return Integer.parseInt(node.getAttributes().getNamedItem(name).getNodeValue()); }
        catch (NumberFormatException e) { return fallback; }
    }

    private static String stringAttribute(Node node, String name, String fallback) {
        if (node == null || node.getAttributes() == null || node.getAttributes().getNamedItem(name) == null)
            return fallback;
        return node.getAttributes().getNamedItem(name).getNodeValue();
    }

    private static BufferedImage copy(BufferedImage source) {
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return result;
    }

    private static final class AnimatedBackground {
        private final List<BufferedImage> frames;
        private final long[] frameEnds;
        private final long duration;

        private AnimatedBackground(List<BufferedImage> frames, List<Integer> delays) {
            this.frames = frames;
            frameEnds = new long[delays.size()];
            long elapsed = 0;
            for (int i = 0; i < delays.size(); i++) {
                elapsed += delays.get(i);
                frameEnds[i] = elapsed;
            }
            duration = elapsed;
        }

        private BufferedImage frameAt(long elapsedMillis) {
            long time = Math.floorMod(elapsedMillis, duration);
            for (int i = 0; i < frameEnds.length; i++)
                if (time < frameEnds[i]) return frames.get(i);
            return frames.get(0);
        }
    }

    static BufferedImage load(String path) {
        try { File f = new File(path); if (f.isFile()) return ImageIO.read(f); }
        catch (Exception e) { /* unavailable images are handled by the caller */ }
        return null;
    }

    //  BUTTONS / TEXT BOX / CURSOR
    // =====================================================================

    private static Color c(int rgb) { return new Color(rgb); }

    /** The framed box used for the dialogue box, the HUD and the name form. */
    public static BufferedImage panel(int w, int h) {
        return UI.computeIfAbsent("panel" + w + "x" + h, k -> {
            BufferedImage png = loadScaled("ui/panel.png", w, h);
            if (png != null) return png;
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
    public static BufferedImage button(int w, int h, boolean selected) { return button(w, h, selected, null); }

    /** Same, but a PNG made for this exact label (assets/ui/button_<label>.png) wins if it exists. */
    public static BufferedImage button(int w, int h, boolean selected, String label) {
        return UI.computeIfAbsent("button" + w + "x" + h + selected + "|" + label, k -> {
            String suffix = selected ? "_selected" : "";
            BufferedImage png = null;
            if (label != null) png = loadScaled("assets/ui/button_" + slug(label) + suffix + ".png", w, h);
            if (png == null) png = loadScaled("assets/ui/button" + suffix + ".png", w, h);
            if (png != null) return png;
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

    private static String slug(String label) {
        return label.toLowerCase().replaceAll("[^a-z0-9]+", "_").replaceAll("^_|_$", "");
    }

    /** Loads a PNG and stretches it to w x h; null if the file does not exist. */
    private static BufferedImage loadScaled(String path, int w, int h) {
        BufferedImage png = load(path);
        if (png == null) return null;
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(png, 0, 0, w, h, null);
        g.dispose();
        return out;
    }

    /** The little arrow shown next to the highlighted button. */
    public static BufferedImage cursor() {
        return UI.computeIfAbsent("cursor", k -> {
            BufferedImage png = loadScaled("assets/ui/cursor.png", 14, 16);
            if (png != null) return png;
            BufferedImage im = new BufferedImage(14, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = im.createGraphics();
            g.setColor(c(0x7a1a1a));
            g.fillPolygon(new int[]{0, 13, 0}, new int[]{0, 8, 16}, 3);
            g.dispose();
            return im;
        });
    }
}
