import java.awt.*;

/** FirstAttack.java: the ambush in the forest and the potion choice. */
public class ForestScene extends WalkScene {

    private boolean horde, potions;

    public ForestScene() { super(GameImages.FOREST, 470); }

    @Override protected void onTrigger(GamePanel gp) {
        gp.state.hp = 60;
        horde = true;
        gp.dialog.tell(() -> askPotion(gp),
            "After choosing a role, you set out on a mission to defeat the demon king.",
            "While in a forest, you are suddenly ambushed by a horde of demons.",
            "Despite fighting to the best of your ability, your inexperience makes it hard for you to hold your own against them and a demon manages to hit you, HP down to 60.",
            "During the crucial time, your pouch containing the unknown potion that the master gave you suddenly glows.",
            "Upon opening it, it flies out into the air and gives you different options for its class.");
    }

    private void askPotion(GamePanel gp) {
        gp.dialog.ask("Would you like to use the potion?", YESNO, i -> {
            if (i == 1) {
                finish(gp, "You chose not to use the potion. You continue fighting with your current HP of " + gp.state.hp + ".");
                return;
            }
            potions = true;
            String[] labels = new String[6];
            for (int p = 1; p <= 6; p++) labels[p - 1] = GameState.POTIONS[p] + " (+" + GameState.POTION_HP[p] + " HP)";
            gp.dialog.ask("Which potion do you choose?", labels, p -> {
                potions = false;
                GameState s = gp.state;
                s.potion = p + 1;
                s.hp += GameState.POTION_HP[p + 1];
                finish(gp, "You used the " + GameState.POTIONS[p + 1] + ". Your HP is now " + s.hp + ".");
            });
        });
    }

    private void finish(GamePanel gp, String msg) {
        gp.dialog.tell(() -> { horde = false; clear(); }, msg, "You manage to defeat the horde of demons. The way forward is clear.");
    }

    @Override protected void drawProps(Graphics2D g, GamePanel gp) {
        if (horde) {
            int[] xs = {690, 770, 850, 925}, hs = {125, 135, 120, 130};
            for (int i = 0; i < xs.length; i++) GameSprites.draw(g, "demon", xs[i], GamePanel.FEET_Y + (i % 2) * 10, hs[i], true);
        }
    }

    @Override protected void drawOverlay(Graphics2D g, GamePanel gp) {
        if (potions) icons(g, "potion", 1, 6, gp.dialog.selected(), 270, 85, 130, 36, 54);
    }

    @Override protected Scene nextScene(GamePanel gp) { return new DungeonScene(); }
}
