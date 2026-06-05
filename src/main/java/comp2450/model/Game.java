package comp2450.model;

import com.google.common.base.Preconditions;

public class Game {

    private static Game instance;
    private Player player1;
    private Player player2;
    private Turn currentTurn;
    private Status gameStatus;
    private Board selectBoard;


    private Game(Player player1, Player player2) {

        Preconditions.checkNotNull(player1,"player1 cannot be null");
        Preconditions.checkNotNull(player2,"player2 cannot be null");
        Preconditions.checkArgument(player1 != player2, "players must be different");

        this.player1 = player1;
        this.player2 = player2;
        this.currentTurn = Turn.PLAYER_ONE;
        this.gameStatus = Status.NOT_STARTED;

        checkGame();
    }

    public static Game getInstance(Player player1, Player player2) {

        Preconditions.checkNotNull(player1, "player1 cannot be null");
        Preconditions.checkNotNull(player2, "player2 cannot be null");
        Preconditions.checkArgument(player1 != player2, "players must be different");

        if (instance == null) {
            instance = new Game(player1, player2);
        }

        return instance;
    }

    public static boolean hasGame() {
        return instance != null;
    }

    public static void removeGame() {
        instance = null;
    }

    public Player getPlayer1() {

        checkGame();
        return this.player1;
    }

    public Player getPlayer2() {
        checkGame();
        return this.player2;
    }

    public Turn getCurrentTurn() {
        checkGame();
        return currentTurn;
    }

    public static Game getCurrentGame() {

        Preconditions.checkState(instance != null, "no game currently exists");
        return instance;
    }

    public void startGame() {
        checkGame();
        Preconditions.checkState(gameStatus == Status.NOT_STARTED, "Game has already started");

        gameStatus = Status.RUNNING;
        checkGame();
    }

    public Status getGameStatus() {

        checkGame();
        return gameStatus;
    }

    public void switchTurn() {
        checkGame();
        Preconditions.checkState(gameStatus == Status.RUNNING, "game must be running to switch turns");

        if (currentTurn == Turn.PLAYER_ONE) {
            currentTurn = Turn.PLAYER_TWO;
        } else {
            currentTurn = Turn.PLAYER_ONE;
        }
        checkGame();
    }

    private void checkGame() {
        Preconditions.checkNotNull(player1, "player1 should not be null.");
        Preconditions.checkNotNull(player2, "player2 should not be null.");
        Preconditions.checkState(player1 != player2, "players should be different.");
        Preconditions.checkNotNull(currentTurn, "currentTurn should not be null.");
        Preconditions.checkNotNull(gameStatus, "gameStatus should not be null.");
    }

}
