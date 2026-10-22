import java.awt.*;

/** BossFight.java: the Demon King. Win conditions follow the rules you gave. */
public class BossScene extends WalkScene {

    private static final String[] NAMES = {"Excalibur", "Dagger of Life", "Grimoire", "Poisonous Smoke", "Elixir of Life"};
    private static final String[] VERB  = {"wield the Excalibur", "use the Dagger of Life", "channel the Grimoire",
                                           "use the Poisonous Smoke", "use the Elixir of Life"};
    private static final String[] CLASS = {"Warrior", "Assassin", "Mage", "Craftmaster", "Healer"};
    private static final int[] ROLE   = {1, 2, 3, 5, 4};   // role that can use each material
    private static final int[] POTION = {4, 5, 1, 6, 2};   // Orange, Black, Blue, White, Red
    private static final int[] LOOT   = {4, 1, 3, 2, 5};   // Talisman, Cloak, Scroll, Mask, Syringe
    private static final String[] ITEM = {
        "the Talisman, and must not have given it to the child",
        "the Invincibility Cloak, and must have used it to run from the demon horde",
        "the Magic Scroll, and must not have used it to lift the barrier",
        "the Mask",
        "the Mystery Syringe, and must not have given it to the survivors"};

    private int king;          // 0 = standing, 1 = defeated, 2 = healed (a man again)
    private boolean mats;
    private boolean kingTransforming;
    private int kingTransformationTick;

    public BossScene() { super(GameImages.LAIR, 400); }

    @Override public String musicTrack() { return "terror"; }

    @Override public boolean showHudLoot() { return !mats; }

    @Override protected void onTrigger(GamePanel gp) {
        gp.dialog.tell(() -> gp.dialog.ask("Would you like to attempt to use them?", YESNO, i -> {
            if (i == 1) lose(gp, "You have chosen not to attempt to use the materials.");
            else chooseMaterial(gp);
        }),
            "You have reached the base of the Demon King, the final boss.",
            "Preparing for the final battle...",
            "Your attacks seem to have no effect on the Demon King.",
            "Your party is in a predicament...",
            "Different materials has appeared in front of you, capable of defeating the Demon King.");
    }

    private void chooseMaterial(GamePanel gp) {
        mats = true;
        gp.dialog.ask("Choose one material you would like to use:", NAMES, m -> { mats = false; conditions(gp, m); });
    }

    private void conditions(GamePanel gp, int m) {
        GameState s = gp.state;
        int hpNeeded = 60 + GameState.POTION_HP[POTION[m]];
        gp.dialog.tell(() -> gp.dialog.ask("Attempt to " + VERB[m] + "?", YESNO, i -> {
            if (i == 1) lose(gp, "You have chosen not to attempt to " + VERB[m] + ".");
            else if (!meets(m, s)) lose(gp, "You do not meet the conditions to " + VERB[m] + ".");
            else succeed(gp, m);
        }),
            "You have chosen the " + NAMES[m] + "!",
            "To " + VERB[m] + ", you must meet the following conditions:",
            "1. You must be " + (CLASS[m].startsWith("A") ? "an " : "a ") + CLASS[m] + " class.",
            "2. You must have exactly " + hpNeeded + " HP.",
            "3. You must have " + ITEM[m] + ".");
    }

    /** role + potion (which decides the HP) + loot + the side-quest choice. */
    private static boolean meets(int m, GameState s) {
        if (s.role != ROLE[m] || s.potion != POTION[m] || s.loot != LOOT[m]) return false;
        switch (m) {
            case 0: return !s.warriorHelped;       // kept the Talisman
            case 1: return s.assassinUsedCloak;    // used the Cloak on the horde
            case 2: return !s.mageUsedScroll;      // kept the Magic Scroll
            case 4: return !s.healerGave;          // kept the Syringe
            default: return true;                  // Craftmaster: Mask + White Potion
        }
    }

    private void succeed(GamePanel gp, int m) {
        switch (m) {
            case 0:
                win(gp, 1, "You have successfully wielded the Excalibur!", "The Excalibur has defeated the Demon King!",
                    "Congratulations! You have saved humanity!");
                break;
            case 1:
                win(gp, 1, "You have successfully used the Dagger of Life!", "The Dagger of Life has defeated the Demon King!",
                    "Congratulations! You have saved humanity!");
                break;
            case 2:
                win(gp, 1, "You have successfully channeled the Grimoire!", "The Grimoire has defeated the Demon King!",
                    "Congratulations! You have saved humanity!");
                break;
            case 3:
                gp.dialog.tell(() -> {
                    if (gp.state.craftRead) {          // only if he read the folder
                        gp.dialog.ask("Capture the Demon King and extract his DNA, or kill him?",
                            new String[]{"Extract DNA", "Kill him"}, i -> {
                                if (i == 0) {
                                    gp.dialog.tell(() -> gp.go(new CraftmasterSecretEndingScene()),
                                        "You have captured the Demon King and secured a sample of his DNA.",
                                        "* SECRET ENDING *",
                                        "You leave the lair and return to your laboratory.");
                                } else {
                                    win(gp, 1, "You have chosen to kill the Demon King.",
                                        "The Demon King has been defeated, but his DNA is lost forever.",
                                        "Congratulations! You have saved humanity, but at a great cost.");
                                }
                            });
                    } else {
                        win(gp, 1, "The Poisonous Smoke finishes off the Demon King.", "Congratulations! You have saved humanity!");
                    }
                }, "You have successfully used the Poisonous Smoke!", "The Poisonous Smoke makes the Demon King vulnerable!");
                break;
            default:
                king = 2;
                kingTransforming = true;
                kingTransformationTick = tick;
                gp.dialog.tell(() -> gp.go(new FateScene(true)), "You lower your weapon.",
                    "Instead of striking the Demon King, you plunge the Elixir of Life into him, hoping that it can still save him.",
                    "The virus burns away, his skin slowly returns to normal, and his empty, dead eyes slowly get their color back.",
                    "The Demon King collapses to his knees... once a monster, now a man.",
                    "* SECRET ENDING *",
                    "You found the true cure, and saved humanity without spilling more blood.",
                    "Congratulations! You have saved humanity... and redeemed a King.");
        }
    }

    private void win(GamePanel gp, int kingAfter, String... lines) {
        king = kingAfter;
        gp.dialog.tell(() -> gp.go(new FateScene(true)), lines);
    }

    private void lose(GamePanel gp, String first) {
        gp.dialog.tell(() -> gp.go(new FateScene(false)), first,
            "The Demon King has defeated you and your party.", "Once again, humanity has lost its hope...", "[[ Game Over ]]");
    }

    @Override protected void drawProps(Graphics2D g, GamePanel gp) {
        if (king == 0) {
            String frame = GameSprites.KING_IDLE[(tick / 8) % GameSprites.KING_IDLE.length];
            GameSprites.draw(g, frame, 760, GamePanel.FEET_Y, 250, false);
        }
        else if (king == 2 && kingTransforming) {
            int frame = Math.min((tick - kingTransformationTick) / 16, GameSprites.DEMON_TO_KING.length - 1);
            GameSprites.draw(g, GameSprites.DEMON_TO_KING[frame], 760, GamePanel.FEET_Y, 150, false);
        }
        else if (king == 2) GameSprites.draw(g, GameSprites.HUMAN, 760, GamePanel.FEET_Y, 140, false);
    }

    @Override protected void drawOverlay(Graphics2D g, GamePanel gp) {
        if (mats) icons(g, GameSprites.MATERIAL, gp.dialog.selected(), 230, 110, 120, 62, 62);
    }

    @Override protected Scene nextScene(GamePanel gp) { return new FateScene(false); }   // never used
}
