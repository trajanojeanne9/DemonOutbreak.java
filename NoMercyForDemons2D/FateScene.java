import java.awt.*;

/** GameFlow.finishStory(): the ending and the "fate" menu (Go Back in Time / Reincarnate / Accept). */
public class FateScene extends Scene {

    private final boolean won;

    public FateScene(boolean won) { this.won = won; }

    @Override public void enter(GamePanel gp) {
        gp.dialog.tell(() -> {
            if (gp.state.chancesLeft <= 0) {      // out of chances: no fate menu, the game is over
                gp.dialog.tell(() -> accept(gp), won ? "Congratulations! You saved humanity." : "You have no chances left. GAME OVER.");
            } else menu(gp);
        }, "Your journey has come to an end...");
    }

    private void menu(GamePanel gp) {
        GameState s = gp.state;
        if (won) {
            gp.dialog.tell(() -> gp.dialog.ask("Choose your fate:", new String[]{"Reincarnate", "Accept fate"}, i -> {
                if (i == 0) reincarnate(gp); else accept(gp);
            }),
                "Chances left: " + s.chancesLeft,
                "Congratulations! You have already saved humanity, but life goes on.",
                "Reincarnate - Create a new fate as a different survivor.");
        } else {
            gp.dialog.tell(() -> gp.dialog.ask("Choose your fate:", new String[]{"Go Back in Time", "Reincarnate", "Accept fate"}, i -> {
                if (i == 0) goBack(gp); else if (i == 1) reincarnate(gp); else accept(gp);
            }),
                "Chances left: " + s.chancesLeft,
                "But this is not necessarily your final fate.",
                "Go Back in Time - Change your fate and correct your mistakes.",
                "Reincarnate - Create a new fate as a different survivor.");
        }
    }

    /** Same name and role, replay from the first attack. */
    private void goBack(GamePanel gp) {
        gp.state.chancesLeft--;
        gp.dialog.tell(() -> { gp.state.resetRun(); gp.go(new ForestScene()); },
            "You chose to Go back in Time.", "The clock turns backward...", "You are given another chance to change your fate.");
    }

    /** Brand new survivor: title, name/age and role again. */
    private void reincarnate(GamePanel gp) {
        gp.state.chancesLeft--;
        gp.dialog.tell(() -> { gp.state.resetIdentity(); gp.go(new TitleScene()); },
            "You chose to Reincarnate.", "Your current life fades away...",
            "A new life begins in a world still threatened by the outbreak.");
    }

    private void accept(GamePanel gp) {
        gp.dialog.tell(() -> gp.dialog.ask("The story ends here.", new String[]{"Play again", "Exit"}, i -> {
            if (i == 0) { gp.state = new GameState(); gp.go(new TitleScene()); }
            else System.exit(0);
        }), "You have chosen to accept your fate.", "[The story ends here.]");
    }

    @Override public void draw(Graphics2D g, GamePanel gp) {
        g.drawImage(GameImages.background(won ? GameImages.ENDING_WIN : GameImages.ENDING_LOSE), 0, 0, null);
        if (gp.state.role > 0) GameSprites.draw(g, "player" + gp.state.role + "_0", 480, GamePanel.FEET_Y, 150, false);
        centered(g, won ? "HUMANITY IS SAVED" : "GAME OVER", new Font(Font.SERIF, Font.BOLD, 56), 150,
                 won ? new Color(0x7a4a00) : new Color(0xc23a2a));
    }
}
