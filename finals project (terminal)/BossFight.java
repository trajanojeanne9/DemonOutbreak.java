import java.util.Scanner;

/** Final stage: the Demon King. */
public class BossFight {

    private static final String LINE = "-------------------------------------------------";
    private static final String DOUBLE_LINE = "=================================================";

    public static boolean bossFight(Scanner input, int role, int potionClass,
                                    int lootChoice, String talismanChoice, String daggerChoice, String scrollChoice, String syringeChoice, String maskChoice, boolean broughtElf) {
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
        System.out.println("\n  Different materials have appeared in front of\n   you, capable of defeating the Demon King.");
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
        System.out.println("1. Excalibur");
        System.out.println("2. Dagger of Life");
        System.out.println("3. Grimoire");
        System.out.println("4. Poisonous Smoke");
        System.out.println("5. Elixir of Life");
        System.out.println(" ");

        int materialChoice = askChoice(input, "Enter your choice (1-5): ", 5);

        switch (materialChoice) {

            // EXCALIBUR
            case 1:
                System.out.println(" ");
                System.out.println(DOUBLE_LINE);
                System.out.println("         You have chosen the Excalibur!");
                System.out.println(DOUBLE_LINE);
                System.out.println(" ");
                System.out.println("To wield the Excalibur, you must meet the following\nconditions:");
                System.out.println(" ");
                System.out.println("1. You must be a Fighter class.");
                System.out.println("2. You must have 80 hp.");
                System.out.println("3. You must have the Talisman.");
                System.out.println(" ");
                System.out.println(LINE);

                if (!askYesNo(input, "Would you like to attempt to wield the Excalibur? (yes/no): ")) {
                    lose("You have chosen not to attempt to wield the\nExcalibur.");
                    return false;
                }

                if (role == 1 && potionClass == 4 && lootChoice == 4 && talismanChoice.equalsIgnoreCase("no")) {
                    System.out.println(" ");
                    System.out.println(LINE);
                    System.out.println("The Excalibur glows brightly as the elf's power flows through it.");
                    System.out.println("You have successfully wielded the Excalibur!");
                    System.out.println(" ");
                    System.out.println("The Excalibur has defeated the Demon King!");
                    System.out.println(" ");
                    System.out.println("Congratulations! You have saved humanity!");
                    won = true;
                } else {
                    lose("You do not meet the conditions to wield the\nExcalibur. The elf looks away, unable to help you further.");
                    return false;
                }
                break;

            // DAGGER OF LIFE
            case 2:
                System.out.println(" ");
                System.out.println(DOUBLE_LINE);
                System.out.println("       You have chosen the Dagger of Life!");
                System.out.println(DOUBLE_LINE);
                System.out.println(" ");
                System.out.println("To use the Dagger of Life, you must meet the \nfollowing conditions:");
                System.out.println(" ");
                System.out.println("1. You must be an Assassin class.");
                System.out.println("2. You must have 90 hp.");
                System.out.println("3. You must have the Invincibility Cloak.");
                System.out.println(" ");
                System.out.println(LINE);

                if (!askYesNo(input, "Would you like to attempt to use the Dagger of Life? (yes/no): ")) {
                    lose("You have chosen not to attempt to use the Dagger of Life.");
                    return false;
                }

                if (role == 2 && potionClass == 5 && lootChoice == 1 && daggerChoice.equalsIgnoreCase("yes")) {
                    System.out.println(" ");
                    System.out.println(LINE);
                    System.out.println("The Dagger of Life glows brightly as the elf's power flows through it.");
                    System.out.println("You have successfully used the Dagger of Life!");
                    System.out.println(" ");
                    System.out.println("The Dagger of Life has defeated the Demon King!");
                    System.out.println(" ");
                    System.out.println("Congratulations! You have saved humanity!");
                    won = true;
                } else {
                    lose("You do not meet the conditions to use the Dagger of Life.\nThe elf looks away, unable to help you further.");
                    return false;
                }
                break;

            // GRIMOIRE
            case 3:
                System.out.println(" ");
                System.out.println(DOUBLE_LINE);
                System.out.println("         You have chosen the Grimoire!");
                System.out.println(DOUBLE_LINE);
                System.out.println(" ");
                System.out.println("To channel the Grimoire, you must meet the following\nconditions:");
                System.out.println(" ");
                System.out.println("1. You must be a Mage class.");
                System.out.println("2. You must have 100 hp.");
                System.out.println("3. You must have the Grimoire.");
                System.out.println(" ");
                System.out.println(LINE);

                if (!askYesNo(input, "Would you like to attempt to channel the Grimoire? (yes/no): ")) {
                    lose("You have chosen not to attempt to channel the\nGrimoire.");
                    return false;
                }

                if (role == 3 && potionClass == 1 && lootChoice == 3 && scrollChoice.equalsIgnoreCase("no")) {
                    System.out.println(" ");
                    System.out.println(LINE);
                    System.out.println("The Grimoire glows brightly as the elf's power flows through it.");
                    System.out.println("You have successfully channeled the Grimoire!");
                    System.out.println(" ");
                    System.out.println("The Grimoire has defeated the Demon King!");
                    System.out.println(" ");
                    System.out.println("Congratulations! You have saved humanity!");
                    won = true;
                } else {
                    lose("You do not meet the conditions to channel the\nGrimoire. The elf looks away, unable to help you further.");
                    return false;
                }
                break;

            // POISONOUS SMOKE
            case 4:
                System.out.println(" ");
                System.out.println(DOUBLE_LINE);
                System.out.println("       You have chosen the Poisonous Smoke!      ");
                System.out.println(DOUBLE_LINE);
                System.out.println(" ");
                System.out.println("To use the Poisonous Smoke, you must meet the \nfollowing conditions:");
                System.out.println(" ");
                System.out.println("1. You must be a Craftmaster class.");
                System.out.println("2. You must have 110 hp.");
                System.out.println("3. You must have the Mask.");
                System.out.println(" ");
                System.out.println(LINE);

                if (!askYesNo(input, "Would you like to attempt to use the Poisonous Smoke? (yes/no): ")) {
                    lose("You have chosen not to attempt to use the Poisonous\n                    Smoke.");
                    return false;
                }

                if (role == 5 && potionClass == 6 && lootChoice == 2 && maskChoice.equalsIgnoreCase("yes")) {
                    System.out.println(" ");
                    System.out.println(LINE);
                    System.out.println("The Poisonous Smoke glows brightly as the elf's power flows through it.");
                    System.out.println("You have successfully used the Poisonous Smoke!");
                    System.out.println(" ");
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
                    won = true;
                } else {
                    lose("You do not meet the conditions to use the Poisonous\n                    Smoke. The elf looks away, unable to help you further.");
                    return false;
                }
                break;

            // ELIXIR OF LIFE
            case 5:
                System.out.println(" ");
                System.out.println(DOUBLE_LINE);
                System.out.println("       You have chosen the Elixir of Life!     ");
                System.out.println(DOUBLE_LINE);
                System.out.println(" ");
                System.out.println("To use the Elixir of Life, you must meet the\n following conditions:");
                System.out.println(" ");
                System.out.println("1. You must be a Healer class.");
                System.out.println("2. You must have 120 hp.");
                System.out.println("3. You must have the Mystery Syringe.");
                System.out.println(" ");
                System.out.println(LINE);

                if (!askYesNo(input, "Would you like to attempt to use the Elixir of Life? (yes/no): ")) {
                    lose("You have chosen not to attempt to use the Elixir of Life.");
                    return false;
                }

                if (role == 4 && potionClass == 2 && lootChoice == 5 && syringeChoice.equalsIgnoreCase("no")) {
                    System.out.println(" ");
                    System.out.println(LINE);
                    System.out.println("The Elixir of Life glows brightly as the elf's power flows through it.");
                    System.out.println(" ");
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
                    won = true;
                } else {
                    lose("You do not meet the conditions to use the Elixir of Life.\nThe elf looks away, unable to help you further.");
                    return false;
                }
                break;
        }

        // Only reached after a win
        System.out.println(" ");
        System.out.println("The elf smiles at you. \"You freed me, and together we freed the world.\"");
        System.out.println(" ");
        System.out.println(LINE);
        return won;
    }

    // Prints the losing message
    private static void lose(String reason) {
        System.out.println(" ");
        System.out.println(LINE);
        System.out.println(" ");
        System.out.println(reason);
        System.out.println("The Demon King has defeated you.");
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
