import java.awt.*;
import java.awt.event.KeyEvent;

/** Name and age (same rules as PlayerInfo.java). */
public class InfoScene extends Scene {

    private final StringBuilder name = new StringBuilder(), age = new StringBuilder();
    private int stage, t;

    @Override public boolean showHud() { return false; }
    @Override public void update(GamePanel gp) { t++; }

    @Override public void draw(Graphics2D g, GamePanel gp) {
        g.drawImage(GameImages.background(GameImages.HIDEOUT), 0, 0, null);
        g.drawImage(GameImages.panel(560, 190), 200, 70, null);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        g.setColor(Color.WHITE);
        g.drawString(stage == 0 ? "Enter your name:" : "Enter your age:", 232, 118);
        g.setColor(Color.WHITE);
        g.fillRect(232, 134, 496, 40);
        g.setColor(Color.BLACK);
        String text = (stage == 0 ? name : age).toString() + ((t / 25) % 2 == 0 ? "_" : "");
        g.drawString(text, 242, 163);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        g.setColor(new Color(0xbfcaf0));
        g.drawString("Press ENTER to confirm" + (stage == 1 ? "  (" + name.toString().trim() + ")" : ""), 232, 206);
    }

    @Override public void keyTyped(char c, GamePanel gp) {
        if (c < 32 || c == 127) return;
        if (stage == 0 && name.length() < 16) name.append(c);
        if (stage == 1 && Character.isDigit(c) && age.length() < 3) age.append(c);
    }

    @Override public void keyPressed(int code, GamePanel gp) {
        StringBuilder sb = stage == 0 ? name : age;
        if (code == KeyEvent.VK_BACK_SPACE && sb.length() > 0) sb.setLength(sb.length() - 1);
        if (code == KeyEvent.VK_ENTER) submit(gp);
    }

    private void submit(GamePanel gp) {
        if (stage == 0) {
            if (name.toString().trim().isEmpty()) {
                gp.dialog.tell(null, "Name cannot be empty. Please enter your name.");
                return;
            }
            stage = 1;
            return;
        }
        int a;
        try { a = Integer.parseInt(age.toString()); }
        catch (NumberFormatException e) { gp.dialog.tell(null, "Please enter your age."); return; }

        if (a <= 0)   { age.setLength(0); gp.dialog.tell(null, "Age cannot be negative or zero. Please enter your age."); return; }
        if (a > 100)  { age.setLength(0); gp.dialog.tell(null, "Age cannot be greater than 100. Please enter your age."); return; }
        if (a < 18) {
            name.setLength(0); age.setLength(0); stage = 0;
            gp.dialog.tell(null, "You do not have enough experience to fight the demons, train more and come back when you are stronger.");
            return;
        }
        gp.state.name = name.toString().trim();
        gp.state.age = a;
        gp.dialog.tell(() -> gp.go(new RoleScene()),
            "You have come of age to venture out the world, choose a role before proceeding on your journey.");
    }
}
