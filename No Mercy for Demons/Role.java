import java.util.Scanner;

public class Role {

    private static final String[] ROLES = {
        "Fighter", "Assassin", "Mage", "Healer", "Craftmaster"
    };

    public static int playerRole(Scanner input) {
        System.out.println("");
        System.out.println("");
        System.out.println("==========================================================================================");
        System.out.println("Choose what kind of warrior you want to be:");
        System.out.println("==========================================================================================");
        for (int i = 0; i < ROLES.length; i++) {
            System.out.println((i + 1) + "." + ROLES[i]);
        }
        System.out.println("------------------------------------------------------------------------------------------");

        int role;
        while (true) {
            System.out.print("Enter your choice (1-" + ROLES.length + "):");
            String roleChoice = input.nextLine().trim();

            try {
                role = Integer.parseInt(roleChoice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to" + ROLES.length + ".");
                continue;
            }

            if (role < 1 || role > ROLES.length) {
                System.out.println("Please choose what type of warrior you want to be in the given choices.");
                continue;
            }

            break;
        }
        System.out.println("");
        System.out.println("==========================================================================================");
        System.out.println("");
        System.out.println("You have chosen the " + ROLES[role - 1] + "  role.");

        switch (role) {
            case 1:
                System.out.println("You are strong in close combat and can take a lot of hits.");
                System.out.println("");
                break;
            case 2:
                System.out.println("You are a master of concealment and agility, able to get close secretly into an enemy and\nattack them.");
                System.out.println("");
                break;
            case 3:
                System.out.println("You can wield powerful spells from a distance and deal a lot of damage.");
                System.out.println("");
                break;
            case 4:
                System.out.println("You can heal yourself, and with enough experience, you can even regenerate yourself.");
                System.out.println("");
                break;
            case 5:
                System.out.println("You are proficient in creating different things from the simplest materials.");
                System.out.println("");
                break;
        }

        return role;
    }

    public static void lastMessage() {
        System.out.println("==========================================================================================");
        System.out.println("Before venturing out, the master of The Resistance, who also acts as your father during\nthe training, approached you.");
        System.out.println("He said \"This is an unknown potion, use it when the time of need has come. However, you\nmust remember that you must not be greedy in using it as it can have dire consequences.\"");
        System.out.println("You sadly bid farewell to him, grateful for all the guidance and love that he gave you.");
        System.out.println("Even if it saddened you to leave the place that you call home for many years, you know\nthat you have to as you have revenge to take.");
    }
}