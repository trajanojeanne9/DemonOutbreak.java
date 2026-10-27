import java.util.Scanner;

public class Loot {

    public static int goldCoins = 0;
    public static int silverCoins = 0;

    public static int loot(Scanner input) {
        System.out.println("After clearing up the dungeon, you stumble upon the treasure room.");
        System.out.println("There lay a single chest, and upon opening it you gained different loots.");
        System.out.println("+10 gold coins.");
        System.out.println("+15 silver coins.");
        goldCoins = 10;
        silverCoins = 15;

        System.out.println("Different loots also came out of the chest.");

        String lootOpen;
        while (true) {
            System.out.println("Would you like to take one? (yes/no)");
            lootOpen = input.nextLine().trim();

            if (lootOpen.equalsIgnoreCase("yes") || lootOpen.equalsIgnoreCase("no")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (lootOpen.equalsIgnoreCase("no")) {
            System.out.println("You have chosen not to take any loot. You continued on your journey with your current loots of " + goldCoins + " gold coins and " + silverCoins + " silver coins.");
            return 0;
        }

        while (true) {
            System.out.println("Choose one loot to take:");
            System.out.println("1. Invincibility Cloak");
            System.out.println("2. Mask");
            System.out.println("3. Magic Scroll");
            System.out.println("4. Talisman");
            System.out.println("5. Syringe");
            System.out.print("Enter your choice (1-5): ");

            int lootChoice;
            try {
                lootChoice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 5.");
                continue;
            }

            if (lootChoice < 1 || lootChoice > 5) {
                System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                continue;
            }

            switch (lootChoice) {
                case 1:
                    System.out.println("You have chosen the Invincibility Cloak. You can now become invincible for a short period of time.");
                    break;
                case 2:
                    System.out.println("You have chosen the Mask. You now have a protection against different smoke.");
                    break;
                case 3:
                    System.out.println("You have chosen the Magic Scroll, containing ancient and forbidden spells.");
                    break;
                case 4:
                    System.out.println("You have chosen the Talisman, the seal needed for the legendary weapon from the ancient times.");
                    break;
                case 5:
                    System.out.println("You have chosen the Syringe, take care of it as it contains a hidden surprise.");
                    break;
            }
            return lootChoice;
        }
    }
}