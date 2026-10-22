import java.awt.*;

/** The hero: walks left and right along the ground. */
public class Player {

    private static final double SPEED = 3.2;

    public double x = 80;
    public boolean faceLeft;
    private int frame, walkTick, idleTick;
    private boolean walking;

    public void update(boolean left, boolean right, int role) {
        walking = left != right;
        if (left && !right)  { x -= SPEED; faceLeft = true; }
        if (right && !left)  { x += SPEED; faceLeft = false; }
        x = Math.max(40, Math.min(GamePanel.W - 20, x));
        if (walking) {
            walkTick++;
            idleTick = 0;
            frame = (walkTick / 8) % GameSprites.playerFrameCount(role, true);
        } else {
            walkTick = 0;
            frame = (idleTick++ / 20) % GameSprites.playerFrameCount(role, false);
        }
    }

    public void stop() { walking = false; frame = 0; walkTick = 0; }

    public boolean isWalking() { return walking; }

    public void draw(Graphics2D g, int role) {
        GameSprites.drawPlayer(g, role, walking, frame, (int) x, GamePanel.FEET_Y, 150, faceLeft);
    }
}
