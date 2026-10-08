import java.util.Scanner;

public class Role {

    public static int playerRole (Scanner input) {
        System.out.println("===================");
        System.out.println("Choose your role:");
        System.out.println("===================");
        System.out.println("1. Warrior");
        System.out.println("2. Assassin");
        System.out.println("3. Mage");
        System.out.println("4. Healer");
        System.out.println("5. Craftmaster");
        System.out.println("--------------------");
        System.out.println("Enter your choice (1-5): ");
        int roleChoice = input.nextInt();

        if (roleChoice < 1 || roleChoice > 5) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            return playerRole(input);
        }

        switch (roleChoice) {
            case 1:
                System.out.println("===================");
                System.out.println("You have chosen the Warrior role.");
                break;
            case 2:
                System.out.println("===================");
                System.out.println("You have chosen the Assassin role.");
                break;
            case 3:
                System.out.println("===================");
                System.out.println("You have chosen the Mage role.");
                break;
            case 4:
                System.out.println("===================");
                System.out.println("You have chosen the Witch role.");
                break;
            case 5:
                System.out.println("===================");
                System.out.println("You have chosen the Craftmaster role.");
                break;
        }
        return roleChoice;
    }

    public static void lastMessage() {
        System.out.println("===================");
        System.out.println("Before venturing out, the master of The Resistance, who also acts as your father during the training, handed you a potion.");
        System.out.println(" He said \"This is an unknown potion, use it when the time of need has come. However, you must remember that you must not be greedy in using it as it can have dire consequences.\"");
    }
}