import java.awt.*;

/** The hero: walks left and right along the ground. */
public class Player {

    private static final double SPEED = 3.2;

    public double x = 80;
    public boolean faceLeft;
    private int frame, walkTick;

    public void update(boolean left, boolean right) {
        boolean moving = false;
        if (left && !right)  { x -= SPEED; faceLeft = true;  moving = true; }
        if (right && !left)  { x += SPEED; faceLeft = false; moving = true; }
        x = Math.max(40, Math.min(GamePanel.W - 20, x));
        if (moving) { walkTick++; frame = (walkTick / 8) % 2; } else stop();
    }

    public void stop() { frame = 0; walkTick = 0; }

    public void draw(Graphics2D g, int role) {
        GameSprites.draw(g, "player" + role + "_" + frame, (int) x, GamePanel.FEET_Y, 150, faceLeft);
    }
}
