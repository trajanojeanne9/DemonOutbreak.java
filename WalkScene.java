import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * A scenario where the hero walks left/right. When he reaches triggerX the game freezes and
 * onTrigger() opens the text box. After the encounter is finished the scene calls clear():
 * the hero can walk again and leaves through the right edge to the next scene.
 */
public abstract class WalkScene extends Scene {

    protected final String bg;
    protected int triggerX;
    protected final Player player = new Player();
    protected boolean triggered, cleared;
    protected int tick;

    protected WalkScene(String bg, int triggerX) {
        this.bg = bg;
        this.triggerX = triggerX;
    }

    protected abstract void onTrigger(GamePanel gp);
    protected abstract Scene nextScene(GamePanel gp);
    protected String backgroundKey() { return bg; }
    protected boolean onRightEdge(GamePanel gp) { return false; }
    protected void onWalk(GamePanel gp) {}
    protected void drawProps(Graphics2D g, GamePanel gp) {}    // things behind the hero
    protected void drawOverlay(Graphics2D g, GamePanel gp) {}  // things in front of the hero
    protected void clear() { cleared = true; }

    @Override public void update(GamePanel gp) {
        tick++;
        if (gp.canAct() && !gp.dialog.active()) {
            player.update(gp.isDown(KeyEvent.VK_LEFT, KeyEvent.VK_A),
                           gp.isDown(KeyEvent.VK_RIGHT, KeyEvent.VK_D), gp.state.role);
            onWalk(gp);
        } else {
            player.stop();
        }
        if (!triggered && player.x >= triggerX) {
            triggered = true;
            player.stop();
            onTrigger(gp);
        }
        if (triggered && gp.canAct() && !gp.dialog.active() && player.x >= GamePanel.W - 30) {
            if (!onRightEdge(gp) && cleared) gp.go(nextScene(gp));
        }
    }

    @Override public void draw(Graphics2D g, GamePanel gp) {
        g.drawImage(GameImages.backgroundFrame(backgroundKey(), System.currentTimeMillis()), 0, 0, null);
        drawProps(g, gp);
        drawCharacter(g, gp);
        drawCompanion(g, gp);
        drawOverlay(g, gp);
        boolean idle = !gp.dialog.active();
        if (idle && cleared && (tick / 25) % 2 == 0) {            // "keep going" arrow
            g.setColor(Color.WHITE);
            g.fillPolygon(new int[]{GamePanel.W - 70, GamePanel.W - 40, GamePanel.W - 70},
                          new int[]{GamePanel.FEET_Y - 190, GamePanel.FEET_Y - 170, GamePanel.FEET_Y - 150}, 3);
        }
        if (idle && !triggered && tick < 420) {                    // controls hint
            g.setFont(GameFonts.font(Font.MONOSPACED, Font.BOLD, 16));
            String s = "Left / Right (or A / D) to walk";
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRoundRect(GamePanel.W / 2 - 190, 396 - 20, 380, 30, 10, 10);
            g.setColor(Color.WHITE);
            g.drawString(s, GamePanel.W / 2 - g.getFontMetrics().stringWidth(s) / 2, 396);
        }
    }

    protected void drawCharacter(Graphics2D g, GamePanel gp) {
        player.draw(g, gp.state.role);
    }

    protected void drawCompanion(Graphics2D g, GamePanel gp) {
        if (!gp.state.boughtChild) return;
        String frame = player.isWalking()
            ? GameSprites.CHILD_WALK[(tick / 8) % GameSprites.CHILD_WALK.length]
            : GameSprites.CHILD;
        int x = (int) player.x + (player.faceLeft ? 70 : -70);
        x = Math.max(50, Math.min(GamePanel.W - 50, x));
        GameSprites.draw(g, frame, x, GamePanel.FEET_Y, 95, player.faceLeft);
    }

    /** Floating, bobbing item icons (potions, loot, boss materials); the highlighted one glows and grows. */
    protected void icons(Graphics2D g, String[] keys, int sel, int x0, int dx, int y, int w, int h) {
        for (int i = 0; i < keys.length; i++) {
            int cx = x0 + i * dx, bob = (int) (Math.sin((tick + i * 9) / 12.0) * 6);
            boolean s = i == sel;
            if (s) { g.setColor(new Color(255, 240, 150, 110)); g.fillOval(cx - w, y + bob - h / 2, w * 2, h * 2); }
            int sw = s ? w * 5 / 4 : w, sh = s ? h * 5 / 4 : h;
            g.drawImage(GameSprites.get(keys[i]), cx - sw / 2, y + bob, sw, sh, null);
        }
    }
}
