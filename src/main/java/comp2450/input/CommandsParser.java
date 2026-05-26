package comp2450.input;

import java.util.Scanner;

public class CommandsParser {

    public static void ParseCommand(Scanner input) {
        boolean valid = false;

        do {
            String getInput = input.nextLine().toLowerCase();

            switch (getInput) {
                case "help" -> listCommands(input);

                case "exit" -> valid = true;
                default -> System.out.println("Invalid");
            }
        } while (!valid);
    }

    //TODO: fill the rest
    public static void listCommands(Scanner input) {
        System.out.println("1. Add Player: add new player to the game (Except exist player)\n" +
                "2. Add Game: Start a new game\n" +
                "3. Select Board: Choosing a board for the game\n" +
                "4. Add Ship: Add a new ship to the currently selected game board\n" +
                "5. Add Select\n" +
                "6. Show Game\n" +
                "7. Show Ships\n" +
                "8. Show Effects\n" +
                "9. Move Ship\n" +
                "10. Remove Ship\n" +
                "11. Remove Effect\n" +
                "12. Remove Game\n");
    }

    public static void addPlayer(Scanner input) {
        System.out.println("Enter your name: ");
        String getName = input.nextLine();

    }




}
