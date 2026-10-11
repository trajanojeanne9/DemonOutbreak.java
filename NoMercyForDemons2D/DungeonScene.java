/** Dungeon.java, part 1: the entrance (enter or fail the mission). */
public class DungeonScene extends WalkScene {

    public DungeonScene() { super(GameImages.DUNGEON_ENTRANCE, 470); }

    @Override protected void onTrigger(GamePanel gp) {
        gp.dialog.tell(() -> gp.dialog.ask("Would you like to go through them?", YESNO, i -> {
            if (i == 1) {
                gp.dialog.tell(() -> gp.go(new FateScene(false)),
                    "You have chosen not to enter the dungeon. You have failed to complete your mission.", "GAME OVER");
            } else {
                gp.dialog.tell(this::clear, "You step into the darkness of the dungeon...");
            }
        }),
            "After defeating the horde of demons, you came across a dungeon.",
            "After entering the dungeon, you came across three paths, each with its own challenges.");
    }

    @Override protected Scene nextScene(GamePanel gp) { return new DungeonHallScene(); }
}
