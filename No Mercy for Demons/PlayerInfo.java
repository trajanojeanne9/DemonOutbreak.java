import java.util.Scanner;

public class PlayerInfo {

    public static String playerInfo(Scanner input) {
        while (true) { // outer loop: restarts from name
            System.out.println("==========================================================================================");

            System.out.print("Enter your name:");
            String name = input.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty. Please enter your name.");
                continue;
            }

            int age;
            while (true) { // inner loop: re-asks age only
                System.out.print("Enter your age:");
                String ageInput = input.nextLine().trim();

                try {
                    age = Integer.parseInt(ageInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number for your age.");
                    continue;
                }

                if (age <= 0) {
                    System.out.println("Age cannot be negative or zero. Please enter your age.");
                    continue;
                }

                if (age > 100) {
                    System.out.println("Age cannot be greater than 100. Please enter your age.");
                    continue;
                }

                break; // valid age (1-100)
            }

            if (age < 18) {
                System.out.println("You do not have enough experience to fight the demons, train more and come back when you\nare stronger.");
                System.exit(0);
            }

            System.out.println("You have come of age to venture out into the world, complete the mission and defeat the\nDemon King.");
            return name;
        }
    }
} 