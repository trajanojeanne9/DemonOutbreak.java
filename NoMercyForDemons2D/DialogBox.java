import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import static java.awt.event.KeyEvent.*;

/**
 * The Pokemon-style box at the bottom of the screen.
 *   say(...)/tell(...) : typewriter text, ENTER advances, callback runs when finished
 *   ask(...)           : question with buttons, callback receives the chosen index
 * While the box is active the game is frozen (see active()).
 */
public class DialogBox {

    public static final int X = 10, Y = 366, BW = 940, BH = 164;
    private static final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, 19);
    private static final Font BTN  = new Font(Font.MONOSPACED, Font.BOLD, 16);
    private static final FontMetrics METRICS =
        new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics().getFontMetrics(FONT);
    private static final int TEXT_W = 890, LINES = 4;

    private enum Mode { NONE, TEXT, CHOICE }
    private Mode mode = Mode.NONE;
    private List<String> pages = new ArrayList<>();
    private int page, shown, tick, sel;
    private String[] options = new String[0];
    private Runnable done;
    private IntConsumer onChoice;

    // ---------- public API ----------

    public void say(String text, Runnable done) {
        List<String> lines = wrap(text);
        pages = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += LINES)
            pages.add(String.join("\n", lines.subList(i, Math.min(lines.size(), i + LINES))));
        page = 0; shown = 0; options = new String[0];
        this.done = done; mode = Mode.TEXT;
    }

    /** Same as say() but each argument starts on a new line. */
    public void tell(Runnable done, String... lines) { say(String.join("\n", lines), done); }

    public void ask(String prompt, String[] opts, IntConsumer cb) {
        List<String> lines = wrap(prompt);
        pages = new ArrayList<>();
        pages.add(String.join("\n", lines.subList(0, Math.min(2, lines.size()))));
        page = 0; shown = 0; options = opts; sel = 0;
        onChoice = cb; mode = Mode.CHOICE;
    }

    public boolean active() { return mode != Mode.NONE; }

    /** Index of the highlighted button while a question is open, otherwise -1. */
    public int selected() { return mode == Mode.CHOICE ? sel : -1; }

    public String text() { return pages.isEmpty() ? "" : pages.get(page); }
    public boolean isChoice() { return mode == Mode.CHOICE; }
    public String[] options() { return options; }

    // ---------- input ----------

    public void key(int code) {
        boolean ok = code == VK_ENTER || code == VK_SPACE || code == VK_Z;
        if (mode == Mode.TEXT) { if (ok) advance(); return; }
        if (mode != Mode.CHOICE) return;
        if (!ready()) { if (ok) shown = pages.get(0).length(); return; }
        int n = options.length, cols = cols(n);
        switch (code) {
            case VK_LEFT:  case VK_A: if (sel % cols > 0) sel--; break;
            case VK_RIGHT: case VK_D: if (sel % cols < cols - 1 && sel + 1 < n) sel++; break;
            case VK_UP:    case VK_W: if (sel - cols >= 0) sel -= cols; break;
            case VK_DOWN:  case VK_S: if (sel + cols < n) sel += cols; break;
            default:
                if (ok) choose(sel);
                else if (code >= VK_1 && code < VK_1 + n) choose(code - VK_1);
        }
    }

    public void mouseMove(int x, int y) {
        if (mode != Mode.CHOICE || !ready()) return;
        for (int i = 0; i < options.length; i++) if (rect(i).contains(x, y)) sel = i;
    }

    public void click(int x, int y) {
        if (mode == Mode.TEXT) advance();
        else if (mode == Mode.CHOICE) {
            if (!ready()) { shown = pages.get(0).length(); return; }
            for (int i = 0; i < options.length; i++) if (rect(i).contains(x, y)) choose(i);
        }
    }

    // ---------- internals ----------

    /** True once the question text has finished typing and the buttons are shown. */
    public boolean ready() { return shown >= pages.get(0).length(); }

    private void advance() {
        String cur = pages.get(page);
        if (shown < cur.length()) { shown = cur.length(); return; }
        if (page + 1 < pages.size()) { page++; shown = 0; return; }
        mode = Mode.NONE;
        Runnable d = done; done = null;
        if (d != null) d.run();
    }

    private void choose(int i) {
        mode = Mode.NONE;
        IntConsumer c = onChoice; onChoice = null;
        if (c != null) c.accept(i);
    }

    private static int cols(int n) { return n == 4 ? 2 : Math.min(n, 3); }

    private Rectangle rect(int i) {
        int n = options.length, cols = cols(n), rows = (n + cols - 1) / cols;
        int bw = n == 2 ? 300 : 280, bh = 32, gap = 14;
        int total = cols * bw + (cols - 1) * gap;
        int startX = X + (BW - total) / 2;
        int startY = rows == 1 ? Y + 98 : Y + 84;
        return new Rectangle(startX + (i % cols) * (bw + gap), startY + (i / cols) * (bh + 8), bw, bh);
    }

    private static List<String> wrap(String text) {
        List<String> out = new ArrayList<>();
        for (String para : text.split("\n", -1)) {
            if (para.isEmpty()) { out.add(""); continue; }
            StringBuilder line = new StringBuilder();
            for (String word : para.split(" ")) {
                String trial = line.length() == 0 ? word : line + " " + word;
                if (METRICS.stringWidth(trial) > TEXT_W && line.length() > 0) {
                    out.add(line.toString());
                    line = new StringBuilder(word);
                } else line = new StringBuilder(trial);
            }
            out.add(line.toString());
        }
        return out;
    }

    // ---------- update / draw ----------

    public void update() {
        tick++;
        if (mode != Mode.NONE) shown += 2;
    }

    public void draw(Graphics2D g) {
        if (mode == Mode.NONE) return;
        g.drawImage(GameImages.panel(BW, BH), X, Y, null);
        g.setFont(FONT);
        g.setColor(Color.WHITE);
        String cur = pages.get(page);
        String visible = cur.substring(0, Math.min(shown, cur.length()));
        int y = Y + 38;
        for (String ln : visible.split("\n", -1)) { g.drawString(ln, X + 26, y); y += 30; }

        if (mode == Mode.TEXT && shown >= cur.length() && (tick / 20) % 2 == 0) {
            int bx = X + BW - 44, by = Y + BH - 30;
            g.fillPolygon(new int[]{bx, bx + 16, bx + 8}, new int[]{by, by, by + 10}, 3);
        }
        if (mode == Mode.CHOICE && ready()) {
            g.setFont(BTN);
            FontMetrics fm = g.getFontMetrics();
            for (int i = 0; i < options.length; i++) {
                Rectangle r = rect(i);
                boolean s = i == sel;
                g.drawImage(GameImages.button(r.width, r.height, s), r.x, r.y, null);
                if (s) g.drawImage(GameImages.cursor(), r.x + 8, r.y + 8, null);
                g.setColor(s ? new Color(0x2a1a00) : Color.WHITE);
                g.drawString(options[i], r.x + (r.width - fm.stringWidth(options[i])) / 2, r.y + 21);
            }
        }
    }
}
