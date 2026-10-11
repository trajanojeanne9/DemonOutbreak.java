import java.awt.*;

/** One screen of the game. */
public abstract class Scene {

    protected static final String[] YESNO = {"Yes", "No"};

    public void enter(GamePanel gp) {}
    public void update(GamePanel gp) {}
    public abstract void draw(Graphics2D g, GamePanel gp);
    public void keyPressed(int code, GamePanel gp) {}
    public void keyTyped(char c, GamePanel gp) {}
    public boolean showHud() { return true; }

    /** Draws text centered on the screen with a small shadow. */
    protected static void centered(Graphics2D g, String s, Font f, int y, Color c) {
        g.setFont(f);
        int x = (GamePanel.W - g.getFontMetrics().stringWidth(s)) / 2;
        g.setColor(Color.BLACK);
        g.drawString(s, x + 3, y + 3);
        g.setColor(c);
        g.drawString(s, x, y);
    }
}
