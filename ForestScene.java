import java.awt.*;

/** FirstAttack.java: the ambush in the forest and the potion choice. */
public class ForestScene extends WalkScene {

    private boolean horde, potions;
    private int storyPanel;

    public ForestScene() { super(GameImages.FOREST, GamePanel.W - 30); }

    @Override protected void onTrigger(GamePanel gp) {
        storyPanel = 1;
        player.x = 40;
    }

    @Override protected boolean onRightEdge(GamePanel gp) {
        if (storyPanel != 1) return false;
        storyPanel = 2;
        player.x = 40;
        return true;
    }

    @Override protected void onWalk(GamePanel gp) {
        if (storyPanel == 2 && !horde && !cleared && player.x >= 700) startAmbush(gp);
    }

    private void startAmbush(GamePanel gp) {
        gp.state.hp = 60;
        horde = true;
        gp.dialog.tell(() -> askPotion(gp),
            "While in a forest, you are suddenly ambushed by a horde of demons.",
            "Despite fighting to the best of your ability, your inexperience makes it hard for you to hold your own against them and a demon manages to hit you, HP down to 60.",
            "During the crucial time, your pouch containing the unknown potion that the master gave you suddenly glows.",
            "Upon opening it, it flies out into the air and gives you different options for its class.");
    }

    @Override protected String backgroundKey() {
        if (storyPanel == 1) return GameImages.FOREST_UNEASY;
        if (storyPanel == 2) return GameImages.FOREST_AMBUSH;
        return bg;
    }

    @Override public String musicTrack() {
        if (storyPanel == 1) return "forest2";
        if (storyPanel == 2) return "forest3";
        return "forest1";
    }

    private void askPotion(GamePanel gp) {
        gp.dialog.ask("Would you like to use the potion?", YESNO, i -> {
            if (i == 1) {
                finish(gp, "You chose not to use the potion. You continue fighting with your current HP of " + gp.state.hp + ".");
                return;
            }
            potions = true;
            String[] labels = new String[6];
            for (int p = 1; p <= 6; p++) labels[p - 1] = GameState.POTIONS[p] + " (+" + GameState.POTION_HP[p] + " HP)";
            gp.dialog.ask("Which potion do you choose?", labels, p -> {
                potions = false;
                GameState s = gp.state;
                s.potion = p + 1;
                s.hp += GameState.POTION_HP[p + 1];
                finish(gp, "You used the " + GameState.POTIONS[p + 1] + ". Your HP is now " + s.hp + ".");
            });
        });
    }

    private void finish(GamePanel gp, String msg) {
        gp.dialog.tell(() -> { horde = false; clear(); }, msg, "You manage to defeat the horde of demons. The way forward is clear.");
    }

    @Override protected void drawProps(Graphics2D g, GamePanel gp) {
        if (horde) {
            int[] xs = {780, 850, 920}, hs = {125, 135, 120};
            for (int i = 0; i < xs.length; i++) {
                String[] frames = GameSprites.DEMON_HORDE_IDLE[i];
                String frame = frames[(tick / 8) % frames.length];
                GameSprites.draw(g, frame, xs[i], GamePanel.FEET_Y + (i % 2) * 10, hs[i], true);
            }
        }
    }

    @Override protected void drawOverlay(Graphics2D g, GamePanel gp) {
        if (potions) icons(g, GameSprites.POTION, gp.dialog.selected(), 270, 85, 130, 36, 54);
    }

    @Override protected Scene nextScene(GamePanel gp) { return new DungeonScene(); }
}
