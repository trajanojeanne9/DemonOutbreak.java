import java.util.Scanner;

public class FirstAttack {

    public static int potionClass;

    public static int firstAttack(Scanner input) {
        potionClass = 0;

        System.out.println("================");
        System.out.println("After choosing a role, you set out on a mission to defeat the demon king.");
        System.out.println("While in a forest, you are suddenly ambushed by a horde of demons.");
        System.out.println("Despite fighting to the best of your ability, your inexperience makes it hard for you to hold your own against them and a demon manages to hit you, HP down to 60.");
        int hp = 60;

        System.out.println("During the crucial time, your pouch containing the unknown potion that the master gave you suddenly glows.");
        System.out.println("Upon opening it, it flies out into the air and gives you different options for its class:");
        System.out.println("1. Blue Potion (+40 HP)");
        System.out.println("2. Red Potion (+60 HP)");
        System.out.println("3. Green Potion (+80 HP)");
        System.out.println("4. Orange Potion (+20 HP)");
        System.out.println("5. Black Potion (+30 HP)");
        System.out.println("6. White Potion (+50 HP)");

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
            System.out.println("You chose not to use the potion. You continue fighting with your current HP of " + hp);
        } 
        
        if (potionUse.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.println("Which potion do you choose? (1-6)");
                String choice = input.nextLine().trim();

                switch (choice) {
                    case "1":
                        potionClass = 1;
                        hp += 40;
                        System.out.println("You used the Blue Potion. Your HP is now " + hp);
                        return hp;
                    case "2":
                        potionClass = 2;
                        hp += 60;
                        System.out.println("You used the Red Potion. Your HP is now " + hp);
                        return hp;
                    case "3":
                        potionClass = 3;
                        hp += 80;
                        System.out.println("You used the Green Potion. Your HP is now " + hp);
                        return hp;
                    case "4":
                        potionClass = 4;
                        hp += 20;
                        System.out.println("You used the Orange Potion. Your HP is now " + hp);
                        return hp;
                    case "5":
                        potionClass = 5;
                        hp += 30;
                        System.out.println("You used the Black Potion. Your HP is now " + hp);
                        return hp;
                    case "6":
                        potionClass = 6;
                        hp += 50;
                        System.out.println("You used the White Potion. Your HP is now " + hp);
                        return hp;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 6.");
                }
            }
        }

        return hp;
    }
}