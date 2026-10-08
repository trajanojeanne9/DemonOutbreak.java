import java.util.Scanner;

public class Dungeon {
    public static String dungeon(Scanner input, int roleChoice) {

        System.out.println("====================");
        System.out.println("After defeating the horde of demons, you came across a dungeon.");
        System.out.println("After entering the dungeon, you came across three paths, each with its own challenges.");
        System.out.println("Would you like to go through them? (yes/no)");
        String dungeonChoice = input.nextLine().trim();

        if (!dungeonChoice.equalsIgnoreCase("yes") && !dungeonChoice.equalsIgnoreCase("no")) {
            System.out.println("Invalid input. Please enter you answer.");
            return dungeon(input, roleChoice);
        }

        if (dungeonChoice.equalsIgnoreCase("no")) {
            System.out.println("You have chosen not to enter the dungeon. You have failed to complete your mission.");
            System.out.println("GAME OVER");
            return "GAME OVER";
        }

        if (dungeonChoice.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.println("Choose your path:");
                System.out.println("Path 1: The path is treacherous and full of poison.");
                System.out.println("Path 2: The path is also dangerous, it is full of deadly traps.");
                System.out.println("Path 3: The path is deadly, as what laid ahead of it is a general demon skilled in combat.");
                System.out.println("--------------------");
                System.out.println("Which path do you choose? (1-3)");
                int pathChoice = input.nextInt();
                input.nextLine();

                switch (pathChoice) {
                case 1:
                    System.out.println("You have chosen Path 1. You can only survive if you are a mage or a healer.");
                        if (roleChoice == 3 || roleChoice == 4) {
                            System.out.println("You have survived the path and you have completed your mission.");
                            return "MISSION COMPLETE";
                        } else {
                            System.out.println("You have failed to complete your mission.");
                            System.out.println("GAME OVER");
                            return "GAME OVER";
                        }

                case 2:
                    System.out.println("You have chosen Path 2. You can only survive if you are an assassin or a craftmaster.");
                        if (roleChoice == 2 || roleChoice == 5) {
                            System.out.println("You have survived the path and you have completed your mission.");
                            return "MISSION COMPLETE";
                        } else {
                            System.out.println("You have failed to complete your mission.");
                            System.out.println("GAME OVER");
                            return "GAME OVER";
                        }

                case 3:
                    System.out.println("You have chosen Path 3. You can only survive if you are a warrior.");
                        if (roleChoice == 1) {
                            System.out.println("You have survived the path and you have defeated the general demon. You have completed your mission.");
                            return "MISSION COMPLETE";
                        } else {
                            System.out.println("You have failed to complete your mission.");
                            System.out.println("GAME OVER");
                            return "GAME OVER";
                        }

                default:
                    System.out.println("Invalid input. Please choose a path from 1 to 3.");
                    break;
                }
            }
        }
        return "GAME OVER";
    }
}