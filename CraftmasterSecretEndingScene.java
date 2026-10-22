import java.awt.*;

/** The Craftmaster escapes the lair and returns to the laboratory with the Demon King's DNA. */
public class CraftmasterSecretEndingScene extends WalkScene {

    private boolean inLab;
    private boolean transformed;
    private boolean companionEscaped;
    private int transformationStartTick;

    public CraftmasterSecretEndingScene() {
        super(GameImages.LAIR, GamePanel.W - 30);
        player.x = 400;
    }

    @Override public String musicTrack() { return "terror"; }

    @Override protected String backgroundKey() {
        return inLab ? GameImages.LAB : bg;
    }

    @Override protected void onTrigger(GamePanel gp) {
        if (!inLab) {
            inLab = true;
            triggered = false;
            triggerX = 300;
            player.x = 40;
            player.stop();
            return;
        }

        transformed = true;
        transformationStartTick = tick;
        companionEscaped = gp.state.boughtChild;
        gp.dialog.tell(() -> gp.go(new FateScene(true, true)),
            "You begin analyzing the Demon King's DNA in your laboratory.",
            "The sample spills onto a wound, and your body begins to transform into a demon.",
            "Your companion escapes safely as the transformation completes.");
    }

    @Override protected void drawCharacter(Graphics2D g, GamePanel gp) {
        if (!transformed) {
            super.drawCharacter(g, gp);
            return;
        }
        int frame = Math.min((tick - transformationStartTick) / 16, GameSprites.DEMON_TRANSFORM.length - 1);
        GameSprites.draw(g, GameSprites.DEMON_TRANSFORM[frame], (int) player.x, GamePanel.FEET_Y, 180, false);
    }

    @Override protected void drawCompanion(Graphics2D g, GamePanel gp) {
        if (!companionEscaped) super.drawCompanion(g, gp);
    }

    @Override protected Scene nextScene(GamePanel gp) {
        return new FateScene(true, true);
    }
}
