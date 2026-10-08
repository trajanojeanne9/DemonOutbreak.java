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
        System.out.println("Would you like to take one? (yes/no)");
        String lootOpen = input.nextLine().trim();

        if (!lootOpen.equalsIgnoreCase("yes") && !lootOpen.equalsIgnoreCase("no")) {
            System.out.println("Invalid input. Please enter you answer.");
            return loot(input);
        }

        if (lootOpen.equalsIgnoreCase("no")) {
            System.out.println("You have chosen not to take any loot. You continued on your journey with your current loots of " + goldCoins + " gold coins and " + silverCoins + " silver coins.");
            return 0;
        }

        if (lootOpen.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.println("Choose one loot to take:");
                System.out.println("1. Invincibility Cloak");
                System.out.println("2. Mask");
                System.out.println("3. Magic Scroll");
                System.out.println("4. Talisman");
                System.out.println("5. Syringe");
                System.out.println("Enter your choice (1-5): ");
                int lootChoice = input.nextInt();
                input.nextLine();

                switch (lootChoice) {
                    case 1:
                        System.out.println("You have chosen the Invincibility Cloak. You can now become invincible for a short period of time.");
                        return lootChoice;
                    case 2:
                        System.out.println("You have chosen the Mask. You can now hide your identity from enemies.");
                        return lootChoice;
                    case 3:
                        System.out.println("You have chosen the Magic Scroll. You can now cast powerful spells.");
                        return lootChoice;
                    case 4:
                        System.out.println("You have chosen the Talisman. You can now protect yourself from curses.");
                        return lootChoice;
                    case 5:
                        System.out.println("You have chosen the Syringe. You can now heal yourself and others.");
                        return lootChoice;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                        break;
                }
            }
        }

        return 0;
    }
}