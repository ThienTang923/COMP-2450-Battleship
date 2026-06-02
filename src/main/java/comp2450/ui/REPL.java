package comp2450.ui;

import comp2450.logic.Game;
import comp2450.model.Board;
import comp2450.model.Player;
import comp2450.output.GamePrinter;

import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class REPL {

    private static final int DEFAULT_BOARD_SIZE = 10;

    private static Map<String, Player> players = new LinkedHashMap<>();
    private static Game game = null;
    private static Player selectPlayer = null;
    private static Board selectBoard = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to Battleship++");
        System.out.println("Type HELP to see available commands.");

        while (running) {

            System.out.print("> ");
            String command = scanner.nextLine().trim().toUpperCase();

            switch (command) {

                case "HELP":
                    GamePrinter.printHelp();
                    break;

                case "ADD PLAYER":
                    break;

                case "ADD GAME":

                    break;

                case "SELECT BOARD":

                    break;

                case "ADD SHIP":

                    break;

                case "ADD EFFECT":

                    break;

                case "SHOW GAME":

                    break;

                case "SHOW SHIPS":

                    break;

                case "SHOW EFFECTS":

                    break;

                case "MOVE SHIP":

                    break;

                case "REMOVE SHIP":

                    break;

                case "REMOVE EFFECT":

                    break;

                case "REMOVE GAME":

                    break;

                case "EXIT":
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Unknown command.");
            }
        }

        scanner.close();
    }

    private static void addPlayer(Scanner scanner) {
        System.out.println("Enter player game: ");
        String name = scanner.nextLine().trim();

        if (players.containsKey(name)) {
            System.out.println("Player already exists.");
            return;
        }

        Board board = new Board(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE);
        Player player = new Player(name, board);

        players.put(name, player);

        System.out.println("Player added: " + name);
    }

    public static void addGame(Scanner scanner) {
        if (Game.hasGame()) {
            System.out.println(" A game already exists. Use REMOVE GAME first");
            return;
        }

        System.out.print("Enter player 1 name: ");
        String playerName1 = scanner.nextLine().trim();

        System.out.print("Enter player 2 name: ");
        String playerName2 = scanner.nextLine().trim();

        Player player1 = players.get(playerName1);
        Player player2 = players.get(playerName2);

        if (player1 == null || player2 == null) {
            System.out.println("Both players must exist before creating a game.");
            return;
        }

        if (player1 == player2) {
            System.out.println("Both players must be exist before creating a game");
            return;
        }

        game = Game.getInstance(player1, player2);

        System.out.println("Game created with " + player1Name + " and " + player2Name + ".");
    }
}
