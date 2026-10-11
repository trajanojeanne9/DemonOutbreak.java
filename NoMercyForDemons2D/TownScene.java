import java.awt.*;

/** Town.java: the burly man and the child. */
public class TownScene extends WalkScene {

    private boolean bought;

    public TownScene() { super(GameImages.TOWN, 380); }

    @Override protected void onTrigger(GamePanel gp) {
        GameState s = gp.state;
        gp.dialog.tell(() -> gp.dialog.ask("Would you buy the slave child?", YESNO, i -> {
            if (i == 1) {
                gp.dialog.tell(this::clear, "You have chosen not to buy the slave child. You continued on your journey with your current loots of "
                    + s.gold + " gold coins and " + s.silver + " silver coins.");
            } else if (s.gold < 5 || s.silver < 10) {
                gp.dialog.tell(this::clear, "You do not have enough coins to buy the slave child.");
            } else {
                s.gold -= 5;
                s.silver -= 10;
                s.boughtChild = true;
                bought = true;
                gp.dialog.tell(this::clear,
                    "You have chosen to buy the slave child. You have given 5 gold coins and 10 silver coins to the burly man.",
                    "You continued on your journey with your current loots of " + s.gold + " gold coins and " + s.silver + " silver coins.");
            }
        }),
            "After getting out of the forest, you stumble upon a town that is the very symbol of poverty.",
            "Many people are dying, food is scarce, the soil is dry.",
            "You saw a burly man dragging down a child.",
            "You stopped the burly man and told him to treat the child well.",
            "He snaps at you and tells you to mind your own business, stating that the child is his slave.",
            "You got into a fight with him, and he tells you that if you really want the child, you must pay him 5 gold coins and 10 silver coins.");
    }

    @Override protected void drawProps(Graphics2D g, GamePanel gp) {
        GameSprites.draw(g, "burly", 650, GamePanel.FEET_Y, 175, true);
        if (!bought) GameSprites.draw(g, "child", 740, GamePanel.FEET_Y, 95, false);
    }

    @Override protected void drawOverlay(Graphics2D g, GamePanel gp) {
        if (bought) GameSprites.draw(g, "child", (int) player.x - 70, GamePanel.FEET_Y, 95, player.faceLeft);
    }

    @Override protected Scene nextScene(GamePanel gp) { return SideQuestScene.forState(gp.state); }
}
