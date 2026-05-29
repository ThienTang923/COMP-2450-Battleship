package comp2450.output;

import comp2450.model.Board;
import comp2450.model.Player;
import comp2450.model.Ship;

public class GamePrinter {
    public static void printHelp() {

        System.out.println("=== Battleship++ Commands ===");

        System.out.println("HELP");
        System.out.println("  Show available commands");

        System.out.println("ADD PLAYER");
        System.out.println("  Input: player name");

        System.out.println("ADD GAME");
        System.out.println("  Input: two existing players");

        System.out.println("SELECT BOARD");
        System.out.println("  Input: player name");

        System.out.println("ADD SHIP");
        System.out.println("  Input: ship size and coordinates");

        System.out.println("ADD EFFECT");
        System.out.println("  Input: effect type and coordinate");

        System.out.println("SHOW GAME");
        System.out.println("  Display both boards");

        System.out.println("SHOW SHIPS");
        System.out.println("  Display all ships");

        System.out.println("SHOW EFFECTS");
        System.out.println("  Display all effects");

        System.out.println("MOVE SHIP");
        System.out.println("  Input: ship id and new coordinates");

        System.out.println("REMOVE SHIP");
        System.out.println("  Input: ship id");

        System.out.println("REMOVE EFFECT");
        System.out.println("  Input: effect id");

        System.out.println("REMOVE GAME");
        System.out.println("  Delete current game");

        System.out.println("EXIT");
        System.out.println("  Close program");
    }

    public static void printPlayer(Player player) {
        System.out.println("Player: " + player.getPlayerName());
        System.out.println("Ships: " + player.getShips().size());
        System.out.println("Successful Hits: " + player.getSuccessfulHits());
        System.out.println("Missed Attack: " + player.getMissedAttacks());

    }

    public static void printShip(Board board) {
        System.out.println("Ships on board: ");

        int index = 0;

        for (Ship ship : board.getShips()) {

            System.out.println("[" + index + "]" + "Size: " + ship.getSize() +
                    ", health: " + ship.getCurrentHealth());
            index++;
        }
    }

    public static void print() {
        
    }
}
