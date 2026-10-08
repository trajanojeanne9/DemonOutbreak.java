import java.util.Scanner;

public class NoMercyForDemons {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
            Opening.title();
            String playerInfo= PlayerInfo.playerInfo(input);
             int playerRole = Role.playerRole(input);
             GameFlow.continueStory(input, playerInfo, playerRole);
    }
}