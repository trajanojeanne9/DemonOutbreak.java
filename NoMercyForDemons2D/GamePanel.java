import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

/** The game window contents: loop, drawing (scaled to any screen size), input, HUD and scene fades. */
public class GamePanel extends JPanel {

    public static final int W = 960, H = 540;   // logical size, scaled to fit the real window
    public static final int FEET_Y = 350;       // the ground line the hero walks on

    public GameState state = new GameState();
    public final DialogBox dialog = new DialogBox();

    final JFrame frame;
    Scene scene, pending;
    private int phase;                  // 0 = playing, 1 = fading out, 2 = fading in
    private float fade;
    private final Set<Integer> down = new HashSet<>();
    private boolean fullscreen;
    private double scale = 1, offX, offY;

    public GamePanel(JFrame frame) {
        this.frame = frame;
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) { onKey(e.getKeyCode()); }
            @Override public void keyReleased(KeyEvent e) { down.remove(e.getKeyCode()); }
            @Override public void keyTyped(KeyEvent e) {
                if (canAct() && !dialog.active()) scene.keyTyped(e.getKeyChar(), GamePanel.this);
            }
        });
        addFocusListener(new FocusAdapter() { @Override public void focusLost(FocusEvent e) { down.clear(); } });
        MouseAdapter mouse = new MouseAdapter() {
            @Override public void mouseMoved(MouseEvent e) { dialog.mouseMove(lx(e), ly(e)); }
            @Override public void mousePressed(MouseEvent e) { if (canAct()) dialog.click(lx(e), ly(e)); }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        scene = new TitleScene();
        scene.enter(this);
    }

    public void start() { new javax.swing.Timer(16, e -> { tick(); repaint(); }).start(); }

    // ---------- scenes ----------

    /** Fade out, switch to the next scene, fade in. */
    public void go(Scene next) {
        if (phase != 0) return;
        pending = next; phase = 1; fade = 0;
    }

    public boolean canAct() { return phase == 0; }

    public boolean isDown(int... codes) {
        for (int c : codes) if (down.contains(c)) return true;
        return false;
    }

    public void tick() {
        if (phase == 1) {
            fade += 0.06f;
            if (fade >= 1f) { fade = 1f; scene = pending; scene.enter(this); phase = 2; }
        } else if (phase == 2) {
            fade -= 0.06f;
            if (fade <= 0f) { fade = 0f; phase = 0; }
        }
        dialog.update();
        if (phase != 1) scene.update(this);
    }

    // ---------- input ----------

    void onKey(int code) {
        boolean first = down.add(code);
        if (code == KeyEvent.VK_F11 && first) { setFullscreen(!fullscreen); return; }
        if (code == KeyEvent.VK_ESCAPE && fullscreen) { setFullscreen(false); return; }
        if (!canAct()) return;
        if (dialog.active()) { if (first) dialog.key(code); }
        else scene.keyPressed(code, this);
    }

    private int lx(MouseEvent e) { return (int) ((e.getX() - offX) / scale); }
    private int ly(MouseEvent e) { return (int) ((e.getY() - offY) / scale); }

    public void setFullscreen(boolean full) {
        fullscreen = full;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        frame.dispose();
        frame.setUndecorated(full);
        if (full) {
            if (gd.isFullScreenSupported()) gd.setFullScreenWindow(frame);
            else { frame.setExtendedState(JFrame.MAXIMIZED_BOTH); frame.setVisible(true); }
        } else {
            gd.setFullScreenWindow(null);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setSize(1100, 640);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        requestFocusInWindow();
    }

    // ---------- drawing ----------

    @Override protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0.create();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        scale = Math.min(getWidth() / (double) W, getHeight() / (double) H);
        offX = (getWidth() - W * scale) / 2;
        offY = (getHeight() - H * scale) / 2;
        g.translate(offX, offY);
        g.scale(scale, scale);
        g.setClip(0, 0, W, H);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        scene.draw(g, this);
        if (scene.showHud() && state.role > 0) drawHud(g);
        dialog.draw(g);
        if (fade > 0) {
            g.setColor(new Color(0, 0, 0, Math.min(255, (int) (fade * 255))));
            g.fillRect(0, 0, W, H);
        }
        g.dispose();
    }

    /** Top-left corner: name, HP bar, coins and the loot item. Top-right: chances left. */
    private void drawHud(Graphics2D g) {
        GameState s = state;
        g.drawImage(GameImages.panel(350, 100), 8, 8, null);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        g.setColor(Color.WHITE);
        g.drawString(s.name + " - " + GameState.ROLES[s.role], 24, 32);
        g.drawString("HP", 24, 56);
        int bw = 190;
        double ratio = Math.min(1.0, s.hp / 140.0);
        g.setColor(new Color(0x111111));
        g.fillRect(52, 43, bw + 4, 16);
        g.setColor(ratio > 0.5 ? new Color(0x3ec45a) : ratio > 0.25 ? new Color(0xe0b02a) : new Color(0xd03a3a));
        g.fillRect(54, 45, (int) (bw * ratio), 12);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(s.hp), 252, 56);
        g.setColor(new Color(0xf1c40f)); g.fillOval(24, 68, 18, 18);
        g.setColor(Color.WHITE);         g.drawString(String.valueOf(s.gold), 48, 83);
        g.setColor(new Color(0xc0c7d1)); g.fillOval(104, 68, 18, 18);
        g.setColor(Color.WHITE);         g.drawString(String.valueOf(s.silver), 128, 83);
        if (s.hasLoot()) g.drawImage(GameSprites.get("loot" + s.loot), 300, 44, 40, 40, null);

        g.drawImage(GameImages.panel(150, 40), W - 158, 8, null);
        g.drawString("Chances: " + s.chancesLeft, W - 146, 34);
    }
}
