/** Three walkable panels on the approach to the Demon King's lair. */
public class LairApproachScene extends WalkScene {

    private static final String[] BACKGROUNDS = {
        GameImages.LAIR_APPROACH_1, GameImages.LAIR_APPROACH_2, GameImages.LAIR_APPROACH_3
    };
    private static final String[][] NARRATION = {
        {
            "The road grows colder as you leave the last signs of civilization behind.",
            "You press onward toward the Demon King's territory."
        },
        {
            "The path winds through the Demon King's desolate outer lands.",
            "A distant red glow marks the way ahead."
        },
        {
            "The entrance to the Demon King's lair looms before you.",
            "You gather your courage and approach the final battle."
        }
    };

    private final int panel;

    public LairApproachScene(int panel) {
        super(backgroundFor(panel), 470);
        this.panel = panel;
    }

    @Override public String musicTrack() { return "terror"; }

    private static String backgroundFor(int panel) {
        if (panel < 1 || panel > BACKGROUNDS.length)
            throw new IllegalArgumentException("Lair approach panel must be between 1 and 3: " + panel);
        return BACKGROUNDS[panel - 1];
    }

    @Override protected void onTrigger(GamePanel gp) {
        gp.dialog.tell(this::clear, NARRATION[panel - 1]);
    }

    @Override protected Scene nextScene(GamePanel gp) {
        return panel < BACKGROUNDS.length ? new LairApproachScene(panel + 1) : new BossScene();
    }
}
