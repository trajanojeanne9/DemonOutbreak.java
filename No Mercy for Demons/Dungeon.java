import java.util.Scanner;

public class Dungeon {

    public static String dungeon(Scanner input, int roleChoice) {
        System.out.println(""); 
        System.out.println(""); 
        System.out.println("==========================================================================================");
        System.out.println("After defeating the horde of demons, you came across a dungeon.");
        System.out.println("It stands between you and the exit to the forest, therefore, you have no choice but to\nconquer it.");
        System.out.println("After entering the dungeon, you came across three paths, each with its own challenges.");

        String dungeonChoice;
        while (true) {
            System.out.println(""); 
            System.out.println("Would you like to go through them? (yes/no)");
            dungeonChoice = input.nextLine().trim();

            if (dungeonChoice.equalsIgnoreCase("yes") || dungeonChoice.equalsIgnoreCase("no")) {
                break;
            }
            System.out.println("Invalid input. Please enter your answer.");
        }

        if (dungeonChoice.equalsIgnoreCase("no")) {
            System.out.println("You have chosen not to enter the dungeon. You have failed to complete your mission.");
            System.out.println("GAME OVER");
            return "GAME OVER";
        }

        while (true) {
            System.out.println("==========================================================================================");
            System.out.println("");
            System.out.println("Choose your path:");
            System.out.println("");
            System.out.println("Path 1: The path is treacherous and full of poison. To pass it, you must be able to cast\nlong distance attack or heal yourself.");
            System.out.println("");
            System.out.println("Path 2: The path is also dangerous, it is full of deadly traps. You must be agile or\nknowledgeable about different materials to pass it");
            System.out.println("");
            System.out.println("Path 3: The path is deadly, as what lies ahead of it is a general demon skilled in combat.\nYou need to be skilled in combat in order to kill the general demon and pass the passage.");
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.print("Which path do you choose? (1-3):");

            int pathChoice;
            try {
                pathChoice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Invalid input. Please choose a path from 1 to 3.");
                continue;
            }

            switch (pathChoice) {
                case 1:
                    System.out.println();
                    System.out.println("You have chosen Path 1. You can only survive if you are a mage or a healer.");
                    if (!attemptPath(input)) {
                        continue; // back to path selection
                    }
                    if (roleChoice == 3 || roleChoice == 4) {
                        System.out.println();
                        System.out.println("You have survived the path and you have conquered the dungeon.");
                        return "DUNGEON CONQUERED";
                    }
                    return failPath();

                case 2:
                    System.out.println();
                    System.out.println("You have chosen Path 2. You can only survive if you are an assassin or a craftmaster.");
                    if (!attemptPath(input)) {
                        continue;
                    }
                    if (roleChoice == 2 || roleChoice == 5) {
                        System.out.println();
                        System.out.println("You have survived the path and conquered the dungeon.");
                        return "DUNGEON CONQUERED";
                    }
                    return failPath();

                case 3:
                    System.out.println();
                    System.out.println("You have chosen Path 3. You can only survive if you are a warrior.");
                    if (!attemptPath(input)) {
                        continue;
                    }
                    if (roleChoice == 1) {
                        System.out.println();
                        System.out.println("==========================================================================================");
                        System.out.println("You have survived the path and you have defeated the general demon. You have passed the\ndungeon.");
                        return "DUNGEON CONQUERED";
                    }
                    return failPath();

                default:
                    System.out.println();
                    System.out.println("Invalid input. Please choose a path from 1 to 3.");
                    break;
            }
        }
    }

    // Prints the failure message and returns the game over result
    private static String failPath() {
        System.out.println("You have failed to pass the dungeon.");
        System.out.println("GAME OVER");
        return "GAME OVER";
    }

    // Asks yes/no until valid. Returns true for yes, false for no.
    private static boolean attemptPath(Scanner input) {
        while (true) {
            System.out.print("Would you like to attempt going through the path? (yes/no):");
            String answer = input.nextLine().trim();

            if (answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("no")) {
                System.out.println("You step back and return to the three paths.");
                return false;
            }
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }
    }
}