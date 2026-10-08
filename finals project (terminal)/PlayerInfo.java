import java.util.Scanner;

public class PlayerInfo {

    public static String playerInfo (Scanner input) {
        System.out.print("Enter your name: ");
        String name = input.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty. Please enter your name.");
                return playerInfo(input);
            }
        
        System.out.print("Enter your age: ");
        int age = input.nextInt();
        input.nextLine();

         if (age<=0) {
            System.out.println("Age cannot be negative or zero. Please enter your age.");
            return playerInfo(input);
         }

         if (age>100) {
            System.out.println("Age cannot be greater than 100. Please enter your age.");
            return playerInfo(input);
         }

        if (age<18){
            System.out.println("You do not have enough experience to fight the demons, train more and come back when you are stronger");
            return playerInfo(input);
        }

        if (age>=18) {
            System.out.println("You have come of age to venture out the world, choose a role before proceeding on your journey");
        }

        return name;
    }
}