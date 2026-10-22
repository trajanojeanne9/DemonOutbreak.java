import java.awt.*;

/** The buried treasure and the chest. */
public class TreasureScene extends WalkScene {

    private static final int CHEST_X = 840, CHEST_FEET_Y = 338, CHEST_HEIGHT = 100;

    private static final String[] MESSAGES = {
        "You have chosen the Invincibility Cloak. You can now become invincible for a short period of time.",
        "You have chosen the Mask. You can now hide your identity from enemies.",
        "You have chosen the Magic Scroll. You can now cast powerful spells.",
        "You have chosen the Talisman. You can now protect yourself from curses.",
        "You have chosen the Syringe. You can now heal yourself and others."};

    private boolean open, choosing;
    private int openingTick;

    public TreasureScene() { super(GameImages.TREASURE, 740); }

    @Override public String musicTrack() { return "dungeon"; }

    @Override protected void onTrigger(GamePanel gp) {
        GameState s = gp.state;
        open = true;
        openingTick = tick;
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
            "At the end of the passage, you find a chest buried beneath disturbed earth.",
            "You uncover and open the chest, revealing different loots.",
            "+10 gold coins.",
            "+15 silver coins.",
            "Different loots also came out of the chest.");
    }

    @Override protected void drawProps(Graphics2D g, GamePanel gp) {
        String chest = GameSprites.CHEST_OPENING[0];
        if (open) {
            int frame = Math.min((tick - openingTick) / 8, GameSprites.CHEST_OPENING.length - 1);
            chest = GameSprites.CHEST_OPENING[frame];
        }
        GameSprites.draw(g, chest, CHEST_X, CHEST_FEET_Y, CHEST_HEIGHT, false);
    }

    @Override protected void drawOverlay(Graphics2D g, GamePanel gp) {
        if (choosing) icons(g, GameSprites.LOOT, gp.dialog.selected(), 400, 90, 150, 56, 56);
    }

    @Override protected Scene nextScene(GamePanel gp) { return new TownScene(); }
}
