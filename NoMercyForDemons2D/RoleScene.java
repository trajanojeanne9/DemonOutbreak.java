import java.awt.*;
import java.util.Arrays;

/** Role selection (same roles as Role.java; role 4 is now called Healer everywhere). */
public class RoleScene extends Scene {

    private static final int[] XS = {160, 320, 480, 640, 800};
    private int t;

    @Override public boolean showHud() { return false; }
    @Override public void update(GamePanel gp) { t++; }

    @Override public void enter(GamePanel gp) {
        String[] labels = Arrays.copyOfRange(GameState.ROLES, 1, 6);
        gp.dialog.ask("Choose your role:", labels, i -> {
            gp.state.role = i + 1;
            gp.dialog.tell(() -> gp.go(new ForestScene()),
                "You have chosen the " + GameState.ROLES[i + 1] + " role.",
                "Before venturing out, the master of The Resistance, who also acts as your father during the training, handed you a potion.",
                "He said \"This is an unknown potion, use it when the time of need has come. However, you must remember that you must not be greedy in using it as it can have dire consequences.\"");
        });
    }

    @Override public void draw(Graphics2D g, GamePanel gp) {
        g.drawImage(GameImages.background(GameImages.HIDEOUT), 0, 0, null);
        int sel = gp.dialog.selected();
        for (int i = 0; i < 5; i++) {
            boolean s = sel == i;
            if (s) {
                g.setColor(new Color(255, 230, 120, 70));
                g.fillOval(XS[i] - 70, 300, 140, 40);
            }
            GameSprites.draw(g, "player" + (i + 1) + "_" + (s ? (t / 15) % 2 : 0), XS[i], 335, s ? 160 : 125, false);
            if (s) centered(g, GameState.ROLES[i + 1], new Font(Font.SERIF, Font.BOLD, 30), 110, new Color(0xe8c15a));
        }
    }
}
