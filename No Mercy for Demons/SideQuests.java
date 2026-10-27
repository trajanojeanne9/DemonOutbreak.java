import java.util.Scanner;

/** Role-specific side quests (Fighter, Assassin, Mage, Healer, Craftmaster). */
public class SideQuests {

    // FIGHTER SIDE QUEST
    public static String fighterScenario(Scanner input, int roleChoice, int potionClass, int lootChoice) {
        String talismanChoice = "";
        if (roleChoice == 1 && lootChoice == 4) {
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");
            System.out.println("\nAs you continue forward, you encounter a child that is battered and asking for help.");
            System.out.println("You still have the Talisman. It might be of help to him.");
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");

            talismanChoice = askYesNo(input, "Would you like to use the Talisman to help him? (yes/no): ");

            if (talismanChoice.equals("yes")) {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You use the Talisman to help the child. He is saved and thanks you for your bravery.]");
                System.out.println("[However, you no longer have the Talisman for what lies ahead]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            } else {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You decide not to use the Talisman. The child's cries fade behind you as you press on.]");
                System.out.println("[You continue forward, but you hear distant screams. You cannot help him as you have no means to do so]");
                System.out.println("[You press on, hoping to find a way to save humanity]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            }
        }
        return talismanChoice;
    }

    // ASSASSIN SIDE QUEST
    public static String assassinScenario(Scanner input, int roleChoice, int potionClass, int lootChoice) {
        String daggerChoice = "";
        if (roleChoice == 2 && lootChoice == 1) {
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");
            System.out.println("\nAs you continue forward, you encounter thousands of demons that are blocking your path.");
            System.out.println("You still have the Invincibility Cloak. It might be able to help you clear the way.");
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");

            daggerChoice = askYesNo(input, "Would you like to use the Invincibility Cloak to get past them? (yes/no): ");

            if (daggerChoice.equals("yes")) {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You use the Invincibility Cloak to clear the way. You can now continue forward and face the challenges ahead.]");
                System.out.println("[You can only use it one more time.]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            } else {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You decide not to use the Invincibility Cloak. You don't have any choice but to fight them.]");
                System.out.println("[When fighting the demons, you lost your Invincibility Cloak.]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            }
        }
        return daggerChoice;
    }

    // MAGE SIDE QUEST
    public static String mageScenario(Scanner input, int roleChoice, int potionClass, int lootChoice) {
        String scrollChoice = "";
        if (roleChoice == 3 && lootChoice == 3) { // Mage class and Grimoire loot
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");
            System.out.println("\nAs you continue forward, you encounter a massive magical \nbarrier blocking your path. You still have the Grimoire.");
            System.out.println("It might be able to dispel the barrier and allow you to \ncontinue forward.");
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");

            scrollChoice = askYesNo(input, "Would you like to use the Grimoire to dispel the barrier? (yes/no): ");

            if (scrollChoice.equals("yes")) {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You use the Grimoire to dispel the barrier. You can now \ncontinue forward and face the challenges ahead.]");
                System.out.println("[However, you no longer have the Grimoire for what lies ahead]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            } else {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You decide not to use the Grimoire. The magical barrier \nblocks your path and you must find another way forward.]");
                System.out.println("[You continue forward, taking a dangerous detour through the ruins.]");
                System.out.println("[You press on, hoping to find a way to save humanity]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            }
        }
        return scrollChoice;
    }

    // HEALER SIDE QUEST
    public static String healerScenario(Scanner input, int roleChoice, int potionClass, int lootChoice) {
        String syringeChoice = "";

        if (roleChoice == 4 && lootChoice == 5) {
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");
            System.out.println("\nAs you continue forward, you hear screaming. A group \n   of survivors is trapped and begging for help.");
            System.out.println("You still have the Mystery Syringe. It might be able \n                  to help them.");
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");

            syringeChoice = askYesNo(input, "Would you like to give them the syringe? (yes = give, no = save it for the fight ahead): ");

            if (syringeChoice.equals("yes")) {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You hand over the Mystery Syringe. The survivors are \nsaved and thank you for your kindness]");
                System.out.println("[However, you no longer have the syringe for what lies \nahead]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            } else {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println(" [You decide to save the syringe. The survivors' \n         cries fade behind you as you press on.");
                System.out.println("\nYou continue forward, but you hear distant screams. \nYou cannot help them as you have no means to do so]");
                System.out.println(" ");
                System.out.println("[You press on, hoping to find a way to save humanity]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            }
        }
        return syringeChoice;
    }

    // CRAFTMASTER SIDE QUEST
    public static String craftmasterScenario(Scanner input, int roleChoice, int potionClass, int lootChoice) {
        String maskChoice = "";
        if (roleChoice == 5 && lootChoice == 2) {
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");
            System.out.println("Before fighting the Demon King, you go back to your laboratory to prepare for the final battle.");
            System.out.println("You unexpectedly saw a folder lying on the table, titled: Road to Immortality");
            System.out.println(" ");
            System.out.println("-------------------------------------------------");
            System.out.println(" ");

            maskChoice = askYesNo(input, "Would you like to read it? (yes/no): ");

            if (maskChoice.equals("yes")) {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You read the folder and found out that the Demon King's DNA is the key to immortality.]");
                System.out.println("[You can now save humanity's greatest fear, death.]");
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
            } else {
                System.out.println(" ");
                System.out.println("-------------------------------------------------");
                System.out.println(" ");
                System.out.println("[You decide to not read the folder, and continue to prepare for the final battle.]");
                System.out.println("-------------------------------------------------");
            }
        }
        return maskChoice;
    }

    // Asks until the answer is yes or no. Returns "yes" or "no" (lowercase).
    private static String askYesNo(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = input.nextLine().trim();
            if (answer.equalsIgnoreCase("yes")) {
                return "yes";
            }
            if (answer.equalsIgnoreCase("no")) {
                return "no";
            }
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }
    }
}
