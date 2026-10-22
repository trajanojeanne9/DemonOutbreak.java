import java.awt.Font;
import java.io.File;
import java.io.IOException;

/** Loads the shared game font, falling back to each caller's requested family if unavailable. */
public final class GameFonts {

    private static final Font CUSTOM = load();

    private GameFonts() {}

    public static Font font(int style, int size) {
        return font(Font.SANS_SERIF, style, size);
    }

    public static Font font(String fallbackFamily, int style, int size) {
        return CUSTOM == null
            ? new Font(fallbackFamily, style, size)
            : CUSTOM.deriveFont(style, (float) size);
    }

    public static Font match(Font requested) {
        return CUSTOM == null
            ? requested
            : CUSTOM.deriveFont(requested.getStyle(), requested.getSize2D());
    }

    private static Font load() {
        for (String path : new String[]{"assets/fonts/game.ttf", "assets/fonts/game.otf"}) {
            File file = new File(path);
            if (!file.isFile()) continue;
            try {
                return Font.createFont(Font.TRUETYPE_FONT, file);
            } catch (IOException | java.awt.FontFormatException e) {
                System.err.println("Could not load custom font " + file + ": " + e.getMessage());
            }
        }
        return null;
    }
}
