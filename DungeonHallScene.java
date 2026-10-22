import java.awt.*;

/**
 * Dungeon.java, part 2: the three doors are drawn in the hall. When the hero walks up to them the
 * game freezes and the text box asks which path to take (the door you point at lights up).
 */
public class DungeonHallScene extends WalkScene {

    private int path;   // 0 = not chosen yet, otherwise 1-3
    private boolean generalDefeated;

    public DungeonHallScene() { super(GameImages.DUNGEON_HALL, 330); }

    @Override public String musicTrack() { return "dungeon"; }

    @Override protected void onTrigger(GamePanel gp) {
        gp.dialog.tell(() -> gp.dialog.ask("Which path do you choose?",
                new String[]{"Path 1: Poison", "Path 2: Traps", "Path 3: General Demon"}, i -> pick(gp, i)),
            "Choose your path:",
            "Path 1: The path is treacherous and full of poison.",
            "Path 2: The path is also dangerous, it is full of deadly traps.",
            "Path 3: The path is deadly, as what laid ahead of it is a general demon skilled in combat.");
    }

    private void pick(GamePanel gp, int i) {
        int role = gp.state.role;
        path = i + 1;
        String[] intro = {
            "You have chosen Path 1. You can only survive if you are a mage or a healer.",
            "You have chosen Path 2. You can only survive if you are an assassin or a craftmaster.",
            "You have chosen Path 3. You can only survive if you are a warrior."};
        boolean ok = i == 0 ? (role == 3 || role == 4) : i == 1 ? (role == 2 || role == 5) : role == 1;
        if (ok) {
            gp.dialog.tell(() -> {
                if (i == 2) generalDefeated = true;
                gp.go(new DungeonPassageScene());
            }, intro[i], i == 2
                ? "You have survived the path and you have defeated the general demon. You have completed your mission."
                : "You have survived the path and you have completed your mission.");
        } else {
            gp.dialog.tell(() -> gp.go(new FateScene(false)), intro[i], "You have failed to complete your mission.", "GAME OVER");
        }
    }

    @Override protected void drawProps(Graphics2D g, GamePanel gp) {
        if (path == 3 && !generalDefeated) {
            String frame = GameSprites.GENERAL_IDLE[(tick / 8) % GameSprites.GENERAL_IDLE.length];
            GameSprites.draw(g, frame, GameImages.DOOR_X[2] - 20, GamePanel.FEET_Y, 190, false);
        }
    }

    @Override protected void drawOverlay(Graphics2D g, GamePanel gp) {
        int s = gp.dialog.selected();
        if (s >= 0) {
            int x = GameImages.DOOR_X[s] - GameImages.DOOR_LIGHT_W[s] / 2;
            int y = 78;
            g.setColor(new Color(255, 255, 255, 55));
            g.fillRoundRect(x - 8, y - 8, GameImages.DOOR_LIGHT_W[s] + 16, GameImages.DOOR_LIGHT_H + 18, 50, 50);
            g.setColor(new Color(255, 255, 255, 190));
            g.setStroke(new BasicStroke(3));
            g.drawRoundRect(x - 8, y - 8, GameImages.DOOR_LIGHT_W[s] + 16, GameImages.DOOR_LIGHT_H + 18, 50, 50);
            g.setStroke(new BasicStroke(1));
        }
    }

    @Override protected Scene nextScene(GamePanel gp) { return new DungeonPassageScene(); }
}
