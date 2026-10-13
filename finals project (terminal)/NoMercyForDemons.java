import java.util.Scanner;

public class NoMercyForDemons {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
            Opening.title();
            Opening.backStory();
            String playerInfo= PlayerInfo.playerInfo(input);
             int playerRole = Role.playerRole(input);
             Role.lastMessage();
             GameFlow.continueStory(input, playerInfo, playerRole);
    }
}
