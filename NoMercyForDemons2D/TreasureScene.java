import java.awt.*;

/** Loot.java: the treasure room and the chest. */
public class TreasureScene extends WalkScene {

    private static final String[] MESSAGES = {
        "You have chosen the Invincibility Cloak. You can now become invincible for a short period of time.",
        "You have chosen the Mask. You can now hide your identity from enemies.",
        "You have chosen the Magic Scroll. You can now cast powerful spells.",
        "You have chosen the Talisman. You can now protect yourself from curses.",
        "You have chosen the Syringe. You can now heal yourself and others."};

    private boolean open, choosing;

    public TreasureScene() { super(GameImages.TREASURE, 470); }

    @Override protected void onTrigger(GamePanel gp) {
        GameState s = gp.state;
        open = true;
        s.gold = 10;
        s.silver = 15;
        gp.dialog.tell(() -> gp.dialog.ask("Would you like to take one?", YESNO, i -> {
            if (i == 1) {
                gp.dialog.tell(this::clear, "You have chosen not to take any loot. You continued on your journey with your current loots of "
                    + s.gold + " gold coins and " + s.silver + " silver coins.");
                return;
            }
            choosing = true;
            String[] labels = new String[5];
            for (int k = 1; k <= 5; k++) labels[k - 1] = GameState.LOOTS[k];
            gp.dialog.ask("Choose one loot to take:", labels, k -> {
                choosing = false;
                s.loot = k + 1;
                gp.dialog.tell(this::clear, MESSAGES[k]);
            });
        }),
            "After clearing up the dungeon, you stumble upon the treasure room.",
            "There lay a single chest, and upon opening it you gained different loots.",
            "+10 gold coins.",
            "+15 silver coins.",
            "Different loots also came out of the chest.");
    }

    @Override protected void drawProps(Graphics2D g, GamePanel gp) {
        GameSprites.draw(g, open ? "chest_open" : "chest_closed", 660, GamePanel.FEET_Y, 100, false);
    }

    @Override protected void drawOverlay(Graphics2D g, GamePanel gp) {
        if (choosing) icons(g, "loot", 1, 5, gp.dialog.selected(), 450, 80, 150, 44, 44);
    }

    @Override protected Scene nextScene(GamePanel gp) { return new TownScene(); }
}
