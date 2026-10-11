import java.awt.*;
import java.util.function.Supplier;

/** A background with narration only (no walking). */
public class StoryScene extends Scene {

    private final String bg, text;
    private final Supplier<Scene> next;

    public StoryScene(String bg, String text, Supplier<Scene> next) {
        this.bg = bg; this.text = text; this.next = next;
    }

    @Override public boolean showHud() { return false; }

    @Override public void enter(GamePanel gp) {
        gp.dialog.say(text, () -> gp.go(next.get()));
    }

    @Override public void draw(Graphics2D g, GamePanel gp) {
        g.drawImage(GameImages.background(bg), 0, 0, null);
    }
}
