import java.util.Scanner;

/** Final stage: the Demon King. */
public class BossFight {

    private static final String LINE = "-------------------------------------------------";
    private static final String DOUBLE_LINE = "=================================================";

    // Parallel arrays: index 0 = Excalibur, 1 = Dagger of Life, 2 = Grimoire,
    // 3 = Poisonous Smoke, 4 = Elixir of Life
    private static final String[] WEAPONS = {
        "Excalibur", "Dagger of Life", "Grimoire", "Poisonous Smoke", "Elixir of Life"
    };
    private static final String[] ACTION = {"wield", "use", "channel", "use", "use"};
    private static final String[] ACTION_PAST = {"wielded", "used", "channeled", "used", "used"};

    // Conditions shown to the player
    private static final String[] REQ_CLASS_TEXT = {"Fighter", "Assassin", "Mage", "Craftmaster", "Healer"};
    private static final int[] REQ_HP = {70, 50, 100, 80, 120};
    private static final String[] REQ_ITEM_TEXT = {
        "Talisman", "Invisibility Cloak", "Magic Scroll", "Mask", "Syringe"
    };

    // Conditions actually checked (same values as your original code)
    private static final int[] REQ_ROLE = {1, 2, 4, 5, 3};
    private static final int[] REQ_POTION = {4, 4, 1, 6, 2};
    private static final int[] REQ_LOOT = {1, 4, 1, 2, 3};

    public static boolean bossFight(Scanner input, int role, int potionClass,
                                    int lootChoice, String syringeChoice, boolean broughtElf) {
        boolean won = false;

        System.out.println(" ");
        System.out.println(LINE);
        System.out.println("\n  You have reached the base of the Demon King, \n               the final boss.");
        System.out.println(" ");
        System.out.println("        Preparing for the final battle...");
        System.out.println(" ");
        System.out.println(LINE);
        System.out.println(" ");
        System.out.println("Your attacks seem to have no effect on the Demon\n                      King.");
        System.out.println(" ");
        System.out.println("You are in a predicament...");
        System.out.println(" ");
        System.out.println(LINE);

        // The legendary weapons only appear if the elf is with the player
        if (!broughtElf) {
            System.out.println(" ");
            System.out.println("You remember the elf from the town, and the legend of the");
            System.out.println("weapons that only answer to his people.");
            System.out.println("But you left him behind. Nothing appears before you.");
            lose("Without the elf, the legendary weapons never answer your call.");
            return false;
        }

        System.out.println(" ");
        System.out.println("The elf steps forward and raises his hands.");
        System.out.println("\"Now is the time. My people's legendary weapons will answer to us.\"");
        System.out.println(" ");
        System.out.println("\n  Different materials has appeared in front of\n   you, capable of defeating the Demon King.");
        System.out.println(" ");

        if (!askYesNo(input, "Would you like to attempt to use them? (yes/no): ")) {
            lose("You have chosen not to attempt to use the materials.");
            return false;
        }

        System.out.println(" ");
        System.out.println(LINE);
        System.out.println("You have chosen to attempt to use the materials.");
        System.out.println("Choose one material you would like to use:");
        System.out.println(" ");
        for (int i = 0; i < WEAPONS.length; i++) {
            System.out.println((i + 1) + ". " + WEAPONS[i]);
        }
        System.out.println(" ");

        int idx = askChoice(input, "Enter your choice (1-" + WEAPONS.length + "): ", WEAPONS.length) - 1;
        String weapon = WEAPONS[idx];

        System.out.println(" ");
        System.out.println(DOUBLE_LINE);
        System.out.println("         You have chosen the " + weapon + "!");
        System.out.println(DOUBLE_LINE);
        System.out.println(" ");
        System.out.println("To " + ACTION[idx] + " the " + weapon + ", you must meet the following conditions:");
        System.out.println(" ");
        System.out.println("1. You must be a " + REQ_CLASS_TEXT[idx] + " class.");
        System.out.println("2. You must have " + REQ_HP[idx] + " hp.");
        System.out.println("3. You must have the " + REQ_ITEM_TEXT[idx] + ".");
        System.out.println(" ");
        System.out.println(LINE);

        if (!askYesNo(input, "Would you like to attempt to " + ACTION[idx] + " the " + weapon + "? (yes/no): ")) {
            lose("You have chosen not to attempt to " + ACTION[idx] + " the " + weapon + ".");
            return false;
        }

        boolean meetsConditions = role == REQ_ROLE[idx]
                && potionClass == REQ_POTION[idx]
                && lootChoice == REQ_LOOT[idx];

        // The Elixir of Life also needs the syringe to have been kept
        if (idx == 4) {
            meetsConditions = meetsConditions && syringeChoice.equalsIgnoreCase("save");
        }

        if (!meetsConditions) {
            lose("You do not meet the conditions to " + ACTION[idx] + " the " + weapon + ".\nThe elf looks away, unable to help you further.");
            return false;
        }

        // Success. Weapons 3 and 4 have their own special endings.
        System.out.println(" ");
        System.out.println(LINE);
        System.out.println("The " + weapon + " glows brightly as the elf's power flows through it.");
        System.out.println("You have successfully " + ACTION_PAST[idx] + " the " + weapon + "!");
        System.out.println(" ");

        switch (idx) {
            case 3:
                System.out.println("The Poisonous Smoke makes the Demon King vulnerable!");
                System.out.println(" ");
                String extract;
                while (true) {
                    System.out.print("Would you like to capture the Demon King and extract his DNA or kill him? (extract/kill): ");
                    extract = input.nextLine().trim();
                    if (extract.equalsIgnoreCase("extract") || extract.equalsIgnoreCase("kill")) {
                        break;
                    }
                    System.out.println("Invalid input. Please enter 'extract' or 'kill'.");
                }

                System.out.println(" ");
                System.out.println(LINE);
                if (extract.equalsIgnoreCase("extract")) {
                    System.out.println("You have successfully extracted the Demon King's DNA!");
                    System.out.println(DOUBLE_LINE);
                    System.out.println("              * SECRET ENDING *              ");
                    System.out.println(DOUBLE_LINE);
                    System.out.println("After the battle, you go back to your laboratory to analyze the sample. You unexpectedly spill the sample on your wound.");
                    System.out.println("You feel a strange sensation, and you look at the mirror and see that you become the new Demon King.");
                    System.out.println(" ");
                    System.out.println("Congratulations! You saved humanity...... or did you?");
                } else {
                    System.out.println("You have chosen to kill the Demon King.");
                    System.out.println("\nThe Demon King has been defeated, but his DNA is lost forever.");
                    System.out.println(" ");
                    System.out.println("Congratulations! You have saved humanity, but at a great cost.");
                }
                break;

            case 4:
                System.out.println("You lower your weapon.");
                System.out.println("\nInstead of striking the Demon King, you plunge the \nElixir of Life into him, hoping that it \n                  can still save him.");
                System.out.println(" ");
                System.out.println("The virus burns away, his skin slowly returns to normal, and his empty, dead eyes slowly get their color back.");
                System.out.println(" ");
                System.out.println("The Demon King collapses to his knees... once a monster, now a man.");
                System.out.println(" ");
                System.out.println(DOUBLE_LINE);
                System.out.println("              * SECRET ENDING *              ");
                System.out.println("     You found the true cure, and saved humanity   ");
                System.out.println("            without spilling more blood.          ");
                System.out.println(DOUBLE_LINE);
                System.out.println(" ");
                System.out.println("Congratulations! You have saved humanity... and \n                redeemed a King.");
                break;

            default:
                System.out.println("The " + weapon + " has defeated the Demon King!");
                System.out.println(" ");
                System.out.println("Congratulations! You have saved humanity!");
                break;
        }

        System.out.println(" ");
        System.out.println("The elf smiles at you. \"You freed me, and together we freed the world.\"");
        System.out.println(" ");
        System.out.println(LINE);
        won = true;
        return won;
    }

    // Prints the losing message
    private static void lose(String reason) {
        System.out.println(" ");
        System.out.println(LINE);
        System.out.println(" ");
        System.out.println(reason);
        System.out.println("The Demon King has defeated you and your party.");
        System.out.println(" ");
        System.out.println("Once again, humanity has lost its hope...");
        System.out.println(" ");
        System.out.println(LINE);
        System.out.println("               [[ Game Over. ]]");
    }

    // Asks until the answer is yes or no. Returns true for yes.
    private static boolean askYesNo(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = input.nextLine().trim();
            if (answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }
    }

    // Asks until the player enters a whole number from 1 to max
    private static int askChoice(Scanner input, String prompt, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int choice = Integer.parseInt(input.nextLine().trim());
                if (choice >= 1 && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // fall through to the message below
            }
            System.out.println("Invalid input. Please enter a number from 1 to " + max + ".");
        }
    }
}
