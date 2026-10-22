/** Everything the game needs to remember about the current run (replaces the old static variables). */
public class GameState {

    public static final String[] ROLES   = {"", "Warrior", "Assassin", "Mage", "Healer", "Craftmaster"};
    public static final String[] POTIONS = {"No Potion", "Blue Potion", "Red Potion", "Green Potion", "Orange Potion", "Black Potion", "White Potion"};
    public static final int[]    POTION_HP = {0, 40, 60, 80, 20, 30, 50};
    public static final String[] LOOTS   = {"Nothing", "Invincibility Cloak", "Mask", "Magic Scroll", "Talisman", "Syringe"};

    /** Shared by "Go Back in Time" and "Reincarnate" (3 chances total). */
    public int chancesLeft = 3;

    public int age;
    public int role;                 // 1 Warrior, 2 Assassin, 3 Mage, 4 Healer, 5 Craftmaster

    public int hp = 100;
    public int potion;               // 0 = none, 1-6 = potion chosen in the forest
    public int loot;                 // 0 = none, 1-5 = loot chosen in the treasure room
    public boolean lootUsed;         // true once the loot item has been used up / given away / lost
    public int gold, silver;

    // choices made in the side quests / town (these decide who can win the boss fight)
    public boolean boughtChild;
    public boolean warriorHelped;       // Warrior gave the Talisman to the child
    public boolean assassinUsedCloak;   // Assassin used the Cloak to escape the horde
    public boolean mageUsedScroll;      // Mage used the Magic Scroll on the barrier
    public boolean healerGave;          // Healer gave the Syringe to the survivors
    public boolean craftRead;           // Craftmaster read the "Road to Immortality" folder

    /** Used by "Go Back in Time": same age and role, everything else starts over. */
    public void resetRun() {
        hp = 100; potion = 0; loot = 0; lootUsed = false; gold = 0; silver = 0;
        boughtChild = warriorHelped = assassinUsedCloak = mageUsedScroll = healerGave = craftRead = false;
    }

    /** Used by "Reincarnate": a brand new survivor (chances are kept). */
    public void resetIdentity() {
        resetRun();
        age = 0; role = 0;
    }

    public boolean hasLoot() { return loot > 0 && !lootUsed; }
}
