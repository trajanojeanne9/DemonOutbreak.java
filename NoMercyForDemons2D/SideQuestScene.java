import java.awt.*;

/**
 * SideQuests.java: the role-specific side quest. Only appears when the role and the loot match
 * (Warrior+Talisman, Assassin+Cloak, Mage+Magic Scroll, Healer+Syringe, Craftmaster+Mask).
 */
public class SideQuestScene extends WalkScene {

    private final int role;
    private boolean gone;      // demons / barrier removed

    public SideQuestScene(int role) {
        super(bgFor(role), 380);
        this.role = role;
    }

    /** Next scene after the town: the side quest if there is one for this role + loot, else the boss. */
    static Scene forState(GameState s) {
        boolean applies = (s.role == 1 && s.loot == 4) || (s.role == 2 && s.loot == 1) || (s.role == 3 && s.loot == 3)
                       || (s.role == 4 && s.loot == 5) || (s.role == 5 && s.loot == 2);
        return applies ? new SideQuestScene(s.role) : new BossScene();
    }

    private static String bgFor(int role) {
        switch (role) {
            case 1: return GameImages.ROAD;
            case 2: return GameImages.DARK_ROAD;
            case 3: return GameImages.BARRIER;
            case 4: return GameImages.SURVIVORS;
            default: return GameImages.LAB;
        }
    }

    @Override protected void onTrigger(GamePanel gp) {
        GameState s = gp.state;
        switch (role) {
            case 1:
                decide(gp, new String[]{"As you continue forward, you encounter a child that is battered and asking for help.",
                                        "You still have the Talisman. It might be of help to him."},
                    "Would you like to use the Talisman to help him?", YESNO,
                    new String[]{"You use the Talisman to help the child. He is saved and thanks you for your bravery.",
                                 "However, you no longer have the Talisman for what lies ahead."},
                    new String[]{"You decide not to use the Talisman. The child's cries fade behind you as you press on.",
                                 "You continue forward, but you hear his distant screams.",
                                 "You press on, hoping to find a way to save humanity."},
                    () -> { s.warriorHelped = true; s.lootUsed = true; }, () -> {});
                break;
            case 2:
                decide(gp, new String[]{"As you continue forward, you encounter thousands of Demons that are blocking your path.",
                                        "You still have the Invincibility Cloak. It might be able to help you clear the way."},
                    "Would you like to use the Invincibility Cloak to get past them?", YESNO,
                    new String[]{"You use the Invincibility Cloak to run past the horde. You can now continue forward and face the challenges ahead.",
                                 "However, you no longer have the Invincibility Cloak for what lies ahead."},
                    new String[]{"You decide not to use the Invincibility Cloak. You don't have any choice but to fight them.",
                                 "When fighting the Demons, you lost your Invincibility Cloak."},
                    () -> { s.assassinUsedCloak = true; s.lootUsed = true; gone = true; },
                    () -> { s.lootUsed = true; gone = true; });
                break;
            case 3:
                decide(gp, new String[]{"As you continue forward, you encounter a massive magical barrier blocking your path. You still have the Magic Scroll.",
                                        "It might be able to dispel the barrier and allow you to continue forward."},
                    "Would you like to use the Magic Scroll to dispel the barrier?", YESNO,
                    new String[]{"You use the Magic Scroll to dispel the barrier. You can now continue forward and face the challenges ahead.",
                                 "However, you no longer have the Magic Scroll for what lies ahead."},
                    new String[]{"You decide not to use the Magic Scroll. The magical barrier blocks your path and you must find another way forward.",
                                 "You continue forward, taking a dangerous detour through the ruins.",
                                 "You press on, hoping to find a way to save humanity."},
                    () -> { s.mageUsedScroll = true; s.lootUsed = true; gone = true; }, () -> {});
                break;
            case 4:
                decide(gp, new String[]{"As you continue forward, you hear screaming. A group of survivors is trapped and begging for help.",
                                        "You still have the Mystery Syringe. It might be able to help them."},
                    "Would you like to give them the syringe, or save it for the fight ahead?", new String[]{"Give it", "Save it"},
                    new String[]{"You hand over the Mystery Syringe. The survivors are saved and thank you for your kindness.",
                                 "However, you no longer have the syringe for what lies ahead."},
                    new String[]{"You decide to save the syringe. The survivors' cries fade behind you as you press on.",
                                 "You continue forward, but you hear their distant screams.",
                                 "You press on, hoping to find a way to save humanity."},
                    () -> { s.healerGave = true; s.lootUsed = true; }, () -> {});
                break;
            default:
                decide(gp, new String[]{"Before fighting the Demon King, you go back to your laboratory to prepare for the final battle.",
                                        "You unexpectedly saw a lying folder hanging around the table, titled: Road to Immortality"},
                    "Would you like to read it?", YESNO,
                    new String[]{"You read the folder and found out that the Demon King's DNA is the key to immortality.",
                                 "You can now save humanity's greatest fear, death."},
                    new String[]{"You decide to not read the folder, and continue to prepare for the final battle."},
                    () -> s.craftRead = true, () -> {});
        }
    }

    private void decide(GamePanel gp, String[] intro, String prompt, String[] opts, String[] yes, String[] no,
                        Runnable onYes, Runnable onNo) {
        gp.dialog.tell(() -> gp.dialog.ask(prompt, opts, i -> {
            if (i == 0) { onYes.run(); gp.dialog.tell(this::clear, yes); }
            else        { onNo.run();  gp.dialog.tell(this::clear, no); }
        }), intro);
    }

    @Override protected void drawProps(Graphics2D g, GamePanel gp) {
        int fy = GamePanel.FEET_Y;
        switch (role) {
            case 1: GameSprites.draw(g, "child", 650, fy, 95, true); break;
            case 2:
                if (!gone) {
                    int[] xs = {610, 690, 770, 850, 925};
                    for (int i = 0; i < xs.length; i++) GameSprites.draw(g, "demon", xs[i], fy + (i % 2) * 10, 125, true);
                }
                break;
            case 3:
                if (!gone) {
                    g.setColor(new Color(170, 100, 255, 110)); g.fillRect(680, 110, 44, 240);
                    g.setColor(new Color(230, 200, 255, 200)); g.drawRect(680, 110, 44, 240);
                    for (int y = 150; y < 350; y += 40) g.drawLine(680, y, 724, y - 24);
                }
                break;
            case 4: GameSprites.draw(g, "survivors", 690, fy, 100, true); break;
            default:
                g.setColor(new Color(0x5a4630));
                g.fillRect(585, 300, 110, 10); g.fillRect(592, 310, 8, 40); g.fillRect(680, 310, 8, 40);
                GameSprites.draw(g, "folder", 640, 300, 32, false);
        }
    }

    @Override protected Scene nextScene(GamePanel gp) { return new BossScene(); }
}
