import java.util.Scanner;

public class Town {

    public static String town(Scanner input, int goldCoins, int silverCoins) {
        System.out.println("=====================");
        System.out.println("After getting out of the forest, you stumble upon a town that is the very symbol of poverty.");
        System.out.println("Many people are dying, food is scarce, the soil is dry.");
        System.out.println("----------------------");
        System.out.println("You saw a burly man dragging down a child.");
        System.out.println("You stopped the burly man and told him to treat the child well.");
        System.out.println("He snaps at you and tells you to mind your own business, stating that the child is his slave.");
        System.out.println("As the child's hood slips, you notice pointed ears and a faint glow in their eyes. This is no ordinary child, but an elf.");
        System.out.println("You got into a fight with him, and he tells you that if you really want the child, you must pay him 5 gold coins and 10 silver coins.");
        System.out.println("However, you hesitated as you might need your coins for future use.");
        System.out.println("----------------------");

        String buySlave;
        while (true) {
            System.out.println("Would you like to buy the child? (yes/no)");
            buySlave = input.nextLine().trim();

            if (buySlave.equalsIgnoreCase("yes") || buySlave.equalsIgnoreCase("no")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }

        if (buySlave.equalsIgnoreCase("no")) {
            System.out.println("You have chosen not to buy the child. You walk away, and the elf's faint glow fades behind you.");
            System.out.println("You continued on your journey with your current loots of " + goldCoins + " gold coins and " + silverCoins + " silver coins.");
            return "NO SLAVE";
        }

        System.out.println("You have chosen to buy the child. You have given 5 gold coins and 10 silver coins to the burly man.");
        goldCoins -= 5;
        silverCoins -= 10;
        Loot.goldCoins = goldCoins;
        Loot.silverCoins = silverCoins;

        System.out.println("The elf looks up at you and whispers, \"Thank you. I will join you, and my people's legendary weapons will answer to us.\"");
        System.out.println("You continued on your journey with your current loots of " + goldCoins + " gold coins and " + silverCoins + " silver coins.");
        return "SLAVE BOUGHT";
    }
}