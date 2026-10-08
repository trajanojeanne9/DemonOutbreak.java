import java.util.Scanner;

public class Town {

    public static String town(Scanner input, int goldCoins, int silverCoins) {
        System.out.println("=====================");
        System.out.println("After getting out of the forest, you stumble upon a town that is the very symbol of poverty.");
        System.out.println("Many people are dying, food is scarce, the soil is dry.");
        System.out.println("----------------------");
        System.out.println("You saw a burly man dragging down a child.");
        System.out.println(". You stopped the burly man and told him to treat the child well.");
        System.out.println("He snaps at you and tells you to mind your own business, stating that the child is his slave.");
        System.out.println("You got into a fight with him, and he tells you that if you really want the child, you must pay him 5 gold coins and 10 silver coins.");
        System.out.println("----------------------");
        System.out.println("Would you buy the slave child? (yes/no)");
        String buySlave = input.nextLine().trim();

        if (!buySlave.equalsIgnoreCase("yes") && !buySlave.equalsIgnoreCase("no")) {
            System.out.println("Invalid input. Please enter your answer.");
            return town(input, goldCoins, silverCoins);
        }

        if (buySlave.equalsIgnoreCase("no")) {
            System.out.println("You have chosen not to buy the slave child. You continued on your journey with your current loots of " + goldCoins + " gold coins and " + silverCoins + " silver coins.");
            return "NO SLAVE";
        }

        if (buySlave.equalsIgnoreCase("yes")) {
           if (goldCoins < 5 || silverCoins < 10) {
               System.out.println("You do not have enough coins to buy the slave child.");
               return "NO SLAVE";
           }
           System.out.println("You have chosen to buy the slave child. You have given 5 gold coins and 10 silver coins to the burly man.");
           goldCoins -= 5;
           silverCoins -= 10;
           Loot.goldCoins = goldCoins;
           Loot.silverCoins = silverCoins;
           System.out.println("You continued on your journey with your current loots of " + goldCoins + " gold coins and " + silverCoins + " silver coins.");
           return "SLAVE BOUGHT";
        }

        return buySlave;
    }
}