package comp2450.ui;

import comp2450.exceptions.InvalidMoveException;
import comp2450.logic.Movement;
import comp2450.logic.UsingEffect;
import comp2450.model.Game;
import comp2450.model.*;
import comp2450.output.GamePrinter;
import comp2450.exceptions.InvalidInputException;
import comp2450.input.InputReader;
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
    private static InputReader inputReader;
    private static UsingEffect usingEffect;

    public static void main(String[] args) {

        inputReader = new InputReader();
        boolean running = true;

        System.out.println("Welcome to Battleship++");
        System.out.println("Type HELP to see available commands.");

        while (running) {
            try {
                String command = inputReader.readCommand("> ");

                switch (command) {

                    case "HELP":
                        GamePrinter.printHelp();
                        break;

                    case "ADD PLAYER":
                        addPlayer();
                        break;

                    case "ADD GAME":
                        addGame();
                        break;

                    case "SELECT BOARD":
                        selectBoard();
                        break;

                    case "ADD SHIP":
                        addShip();
                        break;

                    case "ADD EFFECT":
                        addEffect();
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
                        moveShip();
                        break;

                    case "REMOVE SHIP":
                        removeShip();
                        break;

                    case "REMOVE EFFECT":
                        removeEffect();
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
            } catch (InvalidInputException iie) {
                System.out.println("Invalid input: " + iie.getMessage());
            }
        }
    }

    private static void addPlayer() {

        try {
            String name = inputReader.readString("Enter player game: ").trim();

            if (players.containsKey(name)) {
                System.out.println("Player already exists.");
                return;
            }

            Board board = new Board(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE);
            Player player = new Player(name, board);

            players.put(name, player);

            System.out.println("Player added: " + name);
        } catch (InvalidInputException iie) {
            System.out.println("Invalid input: " + iie.getMessage());
        }
    }

    public static void addGame() {

        if (game != null) {
            System.out.println(" A game already exists. Use REMOVE GAME first");
            return;
        }

        try {
            String playerName1 = inputReader.readString("Enter player 1 name: ").trim();
            String playerName2 = inputReader.readString("Enter player 2 name: ").trim();

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
            game.startGame();

            System.out.println("Game created with " + playerName1 + " and " + playerName2 + ".");
        } catch (InvalidInputException iie) {
            System.out.println("Input error: " + iie.getMessage());
        }
    }


    private static void selectBoard() {
        if (!hasGameRun()) {
            return;
        }
        try {

            String name = inputReader.readString("Enter player name").trim();

            Player player = players.get(name);

            if (player == null) {
                throw new InvalidInputException("Player does not exist");
            }

            selectPlayer = player;
            selectBoard = player.getBoard();

            System.out.println("Select board for player: " + name + "\nUse ADD SHIP to add ships into board for this player.");

        } catch (InvalidInputException iie) {
            System.out.println("Input error: " + iie.getMessage());
        }
    }

    private static boolean hasSelectBoard() {

        if(selectBoard == null || selectPlayer == null) {
            System.out.println("No board selected. Use SELECT BOARD first.");
            return false;
        }

        return true;
    }

    private static boolean alreadyHasSubmarine(Player player) {

        for (Ship ship: player.getShips()) {
            if(ship.getShipType() == ShipType.SUBMARINE) {
                return true;
            }
        }
        return false;
    }

    private static void addShip() {
        if (!hasSelectBoard()) {
            return;
        }

        boolean valid = false;

        while (!valid) {
            try {
                int size = inputReader.readPositiveInt("Enter ship size: ");

                if (size > selectBoard.getXSize() * selectBoard.getYSize()) {
                    throw new InvalidInputException("Ship size is too large. It cannot be bigger than the total number of board coordinate.");
                }

                int typeChoice = inputReader.readInt("Enter ship type: 1 for NORMAL, 2 for SUBMARINE");

                ShipType shipType;

                if (typeChoice == 2) {
                    if (alreadyHasSubmarine(selectPlayer)) {
                        throw new InvalidInputException("This player already has one submarine. \n" +
                                "Please choose NORMAL ship instead." );
                    }
                    shipType = ShipType.SUBMARINE;

                } else if (typeChoice == 1) {
                    shipType = ShipType.NORMAL;
                } else {
                    throw new InvalidInputException("Invalid ship type. Please enter 1 for NORMAL or 2 for SUBMARINE.");
                }

                Coordinate coordinate = inputReader.readCoordinate("Enter ship coordinate as x y: ");

                Ship ship = new Ship(size, coordinate, shipType);

                selectBoard.addShip(ship);
                selectPlayer.addShip(ship);

                System.out.println("Ship added. ");
                valid = true;

            } catch (InvalidInputException iie) {
                System.out.println("Input error: " + iie.getMessage());
                System.out.println("Please try to add ship again.");
            }
        }
    }

    private static Coordinate readValidCoordinate(String prompt, List<Coordinate> coordinates) throws InvalidInputException {

        boolean validCoordinate = false;
        Coordinate coordinate = null;

        while (!validCoordinate) {
            try {
                coordinate = readCoordinateInsideBoard(prompt);

                if (coordinates.contains(coordinate)) {
                    throw new InvalidInputException("This coordinate is already used by this ship. Please enter a different coordinate.");
                }
                validCoordinate = true;

            } catch (InvalidInputException iie) {
                System.out.println("Input error: " + iie.getMessage());
                System.out.println("Please enter the coordinate again.");
            }
        }

        return coordinate;
    }

    private static Coordinate readCoordinateInsideBoard(String prompt) throws InvalidInputException {
        Coordinate coordinate = inputReader.readCoordinate(prompt);

        if (!selectBoard.isInsideBoard(coordinate)) {
            throw new InvalidInputException("Invalid coordinate. x and y must be inside the board.\n" +
                    "For 10x10 board, use values from 0 to 9");
        }

        return coordinate;
    }

    private static void addEffect() {

        if (!hasSelectBoard()) {
            return;
        }

        boolean addingEffects = false;

        while (!addingEffects) {
            try {

                String effectText = inputReader.readString("Enter effect type: \n " +
                        "(DOUBLE_DAMAGE, SHIELD, HEAL,  or DONE to stop: ) \n").trim().toUpperCase();

                if (effectText.equals("DONE")) {
                    addingEffects = true;
                } else {
                    Effect effect;

                    try {
                        effect = Effect.valueOf(effectText);
                    } catch (IllegalArgumentException iae) {
                        throw new InvalidInputException("Invalid effect type. ");
                    }


                    usingEffect.placeChosenEffectRandomly(selectBoard, effect);

                    System.out.println(effect + " effect was added to a random empty location.");
                }
                
            } catch (InvalidInputException iie) {
                System.out.println("Invalid input: " + iie.getMessage());
            }
        }
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

    private static void moveShip() {
        if (!hasSelectBoard()) {
            return;
        }

        if (selectBoard.getShips().isEmpty()) {
            System.out.println("No ships on selected board.");
            return;
        }

        GamePrinter.printShips(selectBoard);

        try {

            int index = inputReader.readInt("Enter ship id: ");

            if (index < 0 || index >= selectBoard.getShips().size()) {
                throw new InvalidInputException("Invalid ship id.");
            }

            Ship ship = selectBoard.getShips().get(index);

            Movement movement = new Movement();

            if (ship.getShipType() == ShipType.NORMAL) {
                String direction = inputReader.readString("Enter direction UP, DOWN, LEFT or RIGHT: ");
                movement.moveNormalShip(selectBoard, ship, direction);
            } else if (ship.getShipType() == ShipType.SUBMARINE) {
                Coordinate target = inputReader.readCoordinate("Enter submarine target coordinate as x y: ");
                movement.moveSubmarine(selectBoard, ship, target);
            } else {
                throw new InvalidInputException("Invalid ship type. Choose a NORMAL ship or SUBMARINE.");
            }

            System.out.println("Ship moved.");

        } catch (InvalidInputException iie) {
            System.out.println("Input error: " + iie.getMessage());
        } catch (InvalidMoveException ime) {
            System.out.println("Input error" + ime.getMessage());
        }
    }

    private static void removeShip() {
        if (!hasSelectBoard()) {
            return;
        }

        if (selectBoard.getShips().isEmpty()) {
            System.out.println("No ships on selected board.");
            return;
        }

        GamePrinter.printShips(selectBoard);

        try {

            int index = inputReader.readInt("Enter ship id: ");

            if (index < 0 || index >= selectBoard.getShips().size()) {
                throw new InvalidInputException("Invalid ship id.");
            }

            Ship ship = selectBoard.getShips().get(index);

            selectBoard.removeShip(ship);
            selectPlayer.getShips().remove(ship);

            System.out.println("Ship removed.");

        } catch (InvalidInputException iie) {
            System.out.println("Input error: " + iie.getMessage());
        }
    }

    private static void removeEffect() {
        if (!hasSelectBoard()) {
            return;
        }

        if (selectBoard.getEffects().isEmpty()) {
            System.out.println("No effects on selected board.");
            return;
        }

        printEffects(selectBoard);
        try {

            int index = inputReader.readInt("Enter effect id: ");

            if (index < 0 || index >= selectBoard.getEffects().size()) {
                throw new InvalidInputException("Invalid effect id.");
            }

            BoardEffect effect = selectBoard.getEffect(index);
            selectBoard.removeEffect(effect);

            System.out.println("Effect removed.");
        } catch (InvalidInputException iie) {
            System.out.println("Input error: " + iie.getMessage());
        }
    }

    private static void removeGame() {
        if (!Game.hasGame()) {
            System.out.println("No game exists.");
            return;
        }

        Game.removeGame();

        game = null;
        selectPlayer = null;
        selectBoard = null;

        System.out.println("Game removed.");
    }

}
