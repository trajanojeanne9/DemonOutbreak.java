/** Walkable passage between the chosen dungeon path and the buried treasure. */
public class DungeonPassageScene extends WalkScene {

    public DungeonPassageScene() { super(GameImages.DUNGEON_PASSAGE, 470); }

    @Override public String musicTrack() { return "dungeon"; }

    @Override protected void onTrigger(GamePanel gp) {
        gp.dialog.tell(this::clear,
            "You continue deeper through the dungeon passage.",
            "At the end of the tunnel, you notice freshly disturbed earth.");
    }

    @Override protected Scene nextScene(GamePanel gp) { return new TreasureScene(); }
}
