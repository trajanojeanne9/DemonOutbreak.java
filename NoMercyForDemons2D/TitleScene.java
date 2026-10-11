import java.awt.*;
import java.awt.event.KeyEvent;

public class TitleScene extends Scene {

    private int t;

    private static final String BACKSTORY =
        "You are born in a world that is dominated by demons.\n" +
        "Growing up, you resented them for the childhood that they robbed from you. You never tasted true freedom\u2014you have always been oppressed.\n" +
        "However, it amplifies when they killed your parents.\n" +
        "After their death, you joined \"The Resistance\"\u2014an organization that had a mission of eliminating every demon.\n" +
        "Under their guidance and after much rigorous training, you are finally given a chance to venture out into the world to actualize the organization's mission.";

    @Override public boolean showHud() { return false; }
    @Override public void update(GamePanel gp) { t++; }

    @Override public void draw(Graphics2D g, GamePanel gp) {
        g.drawImage(GameImages.background(GameImages.TITLE), 0, 0, null);
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 110, GamePanel.W, 230);
        centered(g, "Welcome to", new Font(Font.SERIF, Font.ITALIC, 28), 160, new Color(0xe9dfc8));
        centered(g, "NO MERCY FOR DEMONS", new Font(Font.SERIF, Font.BOLD, 62), 228, new Color(0xe8c15a));
        Font small = new Font(Font.SERIF, Font.PLAIN, 20);
        centered(g, "Here, your courage and wit will be tested against the most fearsome demons.", small, 276, Color.WHITE);
        centered(g, "Will you conquer the game, or will the game conquer you?", small, 306, Color.WHITE);
        if ((t / 30) % 2 == 0) centered(g, "Press ENTER to start", new Font(Font.MONOSPACED, Font.BOLD, 24), 420, Color.WHITE);
        centered(g, "Left/Right or A/D: move   Up/Down: choose   ENTER: confirm   F11: fullscreen",
                 new Font(Font.MONOSPACED, Font.PLAIN, 14), 510, new Color(0xcccccc));
    }

    @Override public void keyPressed(int code, GamePanel gp) {
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            gp.go(new StoryScene(GameImages.CITY, BACKSTORY, InfoScene::new));
        }
    }
}
