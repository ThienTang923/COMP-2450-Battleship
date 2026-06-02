package comp2450.ui;

import comp2450.logic.Game;
import comp2450.model.*;
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
                    addPlayer(scanner);
                    break;

                case "ADD GAME":
                    addGame(scanner);
                    break;

                case "SELECT BOARD":
                    selectBoard(scanner);
                    break;

                case "ADD SHIP":
                    addShip(scanner);
                    break;

                case "ADD EFFECT":
                    addEffect(scanner);
                    break;

                case "SHOW GAME":
                    showGame();
                    break;

                case "SHOW SHIPS":
                    showShips();
                    break;

                case "SHOW EFFECTS":
                    showEffects();
                    break;

                case "MOVE SHIP":
                    moveShip(scanner);
                    break;

                case "REMOVE SHIP":
                    removeShip(scanner);
                    break;

                case "REMOVE EFFECT":
                    removeEffect(scanner);
                    break;

                case "REMOVE GAME":
                    removeGame();
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
        if (!hasGameRun()) {
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

        System.out.println("Game created with " + playerName1 + " and " + playerName2 + ".");
    }


    private static void selectBoard(Scanner scanner) {
        if (!hasGameRun()) {
            return;
        }

        System.out.println("Enter player name");
        String name = scanner.nextLine().trim();

        Player player = players.get(name);

        if(player == null) {
            System.out.println("Player does not exist");
            return;
        }

        selectPlayer = player;
        selectBoard = player.getBoard();

        System.out.println("Select board for player: " + name);
    }

    private static boolean hasSelectBoard() {

        if(selectBoard == null || selectPlayer == null) {
            System.out.println("No board selected. Use SELECT BOARD first.");
            return false;
        }

        return true;
    }

    private static int readInt(Scanner scanner) {
        String line = scanner.nextLine().trim();
        return Integer.parseInt(line);
    }

    private static Coordinate readCoordinate(Scanner scanner) {
        String line = scanner.nextLine().trim();
        String[] parts = line.split(" ");

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        return new Coordinate(x,y);
    }

    private static void addShip(Scanner scanner) {
        if(!hasSelectBoard()) {
            return;
        }

        System.out.println("Enter ship size: ");
        int size = readInt(scanner);

        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            System.out.println("Enter coordinate " + (i+1) + " as x y: ");
            Coordinate coordinate = readCoordinate(scanner);
            coordinates.add(coordinate);
        }

        Ship ship = new Ship(size, coordinates);

        selectBoard.addShip(ship);
        selectPlayer.addShip(ship);

        System.out.println("Ship added. ");

    }

    private static void addEffect(Scanner scanner) {

        if (!hasSelectBoard()) {
            return;
        }

        System.out.println("Enter effect type: ");

        System.out.println("(DOUBLE_DAMAGE, SHIELD, HEAL, RADAR)");

        String effectText = scanner.nextLine().trim().toUpperCase();

        Effect effect;

        try {
            effect = Effect.valueOf(effectText);
        } catch (IllegalArgumentException iae) {
            System.out.println("Invalid effect type. ");
            return;
        }

        System.out.println("Enter coordinate as x y: ");
        Coordinate coordinate = readCoordinate(scanner);

        BoardEffect boardEffect = new BoardEffect(effect, coordinate);
        selectBoard.addEffect(boardEffect);

        System.out.println("Effect added. ");
    }
    private static boolean hasEffectAt(Board board, Coordinate coordinate) {

        for (BoardEffect effect : board.getEffects()) {
            if (effect.getCoordinate().getX() == coordinate.getX() && effect.getCoordinate().getY() == coordinate.getY()) {
                return true;
            }
        }

        return false;
    }

    private static void printBoard(Board board) {
        System.out.println("Legend: ");
        System.out.println(". = empty location");
        System.out.println("< = ship location");
        System.out.println("* = effect");
        System.out.println();

        for (int y = 0; y < board.getYSize(); y++) {
            for (int x = 0; x < board.getXSize(); x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Cell cell = board.getCell(coordinate);

                if (hasEffectAt(board, coordinate)) {
                    System.out.print("* ");
                } else if (cell.containShip()) {
                    System.out.print("< ");
                } else {
                    System.out.print(". ");
                }
            }

            System.out.println();
        }
    }

    private static void printEffects(Board board) {
        if (board.getEffects().isEmpty()) {
            System.out.println("No effects.");
            return;
        }

        int index = 0;

        for (BoardEffect effect : board.getEffects()) {
            Coordinate coordinate = effect.getCoordinate();

            System.out.println("[" + index + "]" + effect.getEffect() +
                    " at " + coordinate.getX() + " " + coordinate.getY());

            index++;
        }
    }
    private static void showGame() {
        if(!hasGameRun()) {
            return;
        }

        System.out.println("=*=*= Game Information =*=*=");

        GamePrinter.printPlayer(game.getPlayer1());
        printBoard(game.getPlayer1().getBoard());

        System.out.println();

        GamePrinter.printPlayer(game.getPlayer2());
        printBoard(game.getPlayer2().getBoard());
    }

    private static boolean hasGameRun() {
        if (!Game.hasGame() || game == null) {
            System.out.println("No game exist. Use ADD GAME first.");
            return false;
        }

        return true;
    }

    private static void showShips() {
        if (!hasGameRun()) {
            return;
        }

        System.out.println("=+=+= Ship for Player 1=+=+=");
        GamePrinter.printShips(game.getPlayer1().getBoard());

        System.out.println("=+=+= Ship for Player 2=+=+=");
        GamePrinter.printShips(game.getPlayer2().getBoard());
    }

    private static void showEffects() {
        if(!hasGameRun()) {
            return;
        }

        System.out.println("=+=+= Effects for Player 1 =+=+=");
        printEffects(game.getPlayer1().getBoard());

        System.out.println("=+=+= Effects for Player 2 =+=+=");
        printEffects(game.getPlayer2().getBoard());
    }

    private static void moveShip(Scanner scanner) {
        if (!hasSelectBoard()) {
            return;
        }

        if (selectBoard.getShips().isEmpty()) {
            System.out.println("No ships on selected board.");
            return;
        }

        GamePrinter.printShips(selectBoard);

        System.out.println("Enter ship id: ");
        int index = readInt(scanner);

        if (index < 0 || index >= selectBoard.getShips().size()) {
            System.out.println("Invalid ship id.");
            return;
        }

        Ship ship = selectBoard.getShips().get(index);

        List<Coordinate> newCoordinates = new ArrayList<>();

        for (int i = 0; i < ship.getSize(); i++) {
            System.out.print("Enter new coordinate: " + (i+1) + " as x y: ");
            Coordinate coordinate = readCoordinate(scanner);
            newCoordinates.add(coordinate);
        }

        selectBoard.removeShip(ship);
        ship.move(newCoordinates);
        selectBoard.addShip(ship);

        System.out.println("Ship moved.");
    }

    private static void removeShip(Scanner scanner) {
        if (!hasSelectBoard()) {
            return;
        }

        if (selectBoard.getShips().isEmpty()) {
            System.out.println("No ships on selected board.");
            return;
        }

        GamePrinter.printShips(selectBoard);

        System.out.print("Enter ship id: ");
        int index = readInt(scanner);

        if (index < 0 || index >= selectBoard.getShips().size()) {
            System.out.println("Invalid ship id.");
            return;
        }

        Ship ship = selectBoard.getShips().get(index);

        selectBoard.removeShip(ship);
        selectPlayer.getShips().remove(ship);

        System.out.println("Ship removed.");
    }
}
