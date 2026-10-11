import java.util.Scanner;

public class FirstAttack {

    public static int potionClass;

    private static final String[] POTION_NAMES = {
        "Blue", "Red", "Green", "Orange", "Black", "White"
    };
    private static final int[] POTION_HEAL = {
        40, 60, 80, 20, 30, 50
    };

    // Returns the player's HP, or -1 if the player died
    public static int firstAttack(Scanner input) {
        potionClass = 0;

        System.out.println("================");
        System.out.println("After choosing a role, you set out on a mission to defeat the demon king.");
        System.out.println("While in a forest, you are suddenly ambushed by a horde of demons.");
        System.out.println("Despite fighting to the best of your ability, your inexperience makes it hard for you to hold your own against them and a demon manages to hit you, HP down to 60.");
        int hp = 60;

        System.out.println("During the crucial time, your pouch containing the unknown potion that the master gave you suddenly glows.");
        System.out.println("Upon opening it, it flies out into the air and gives you different options for its class:");
        for (int i = 0; i < POTION_NAMES.length; i++) {
            System.out.println((i + 1) + ". " + POTION_NAMES[i] + " Potion (+" + POTION_HEAL[i] + " HP)");
        }

        String potionUse;
        while (true) {
            System.out.println("Would you like to use the potion? (yes/no)");
            potionUse = input.nextLine().trim();

            if (potionUse.equalsIgnoreCase("yes") || potionUse.equalsIgnoreCase("no")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (potionUse.equalsIgnoreCase("no")) {
            System.out.println("You chose not to use the potion. Being badly injured, you cannot fight anymore and was eventually killed by the demons.");
            System.out.println("=======================");
            System.out.println("GAME OVER");
            System.out.println("=======================");
            return -1;
        }

        while (true) {
            System.out.println("Which potion do you choose? (1-" + POTION_NAMES.length + ")");
            String choiceText = input.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(choiceText);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to " + POTION_NAMES.length + ".");
                continue;
            }

            if (choice < 1 || choice > POTION_NAMES.length) {
                System.out.println("Invalid choice. Please enter a number from 1 to " + POTION_NAMES.length + ".");
                continue;
            }

            potionClass = choice;

            if (choice == 3) {
                System.out.println("You used the Green Potion. Its power is too much for your body to handle, and you collapse.");
                System.out.println("=======================");
                System.out.println("GAME OVER");
                System.out.println("=======================");
                return -1;
            }

            hp += POTION_HEAL[choice - 1];
            System.out.println("You used the " + POTION_NAMES[choice - 1] + " Potion. Your HP is now " + hp);
            return hp;
        }
    }
}
