package comp2450.logic;

import com.google.common.base.Preconditions;
import comp2450.exceptions.InvalidMoveException;
import comp2450.model.*;

public class GamePlay {

    private Game game;
    private Movement movement;
    private Attack attackGame;
    private UsingEffect usingEffect;

    public GamePlay(Game game) {

        Preconditions.checkNotNull(game, "game cannot be null");

        this.game = game;
        this.movement = new Movement();
        this.attackGame = new Attack();
        this.usingEffect = new UsingEffect();

        checkGamePlay();
    }

    public Player getCurrentPlayer() {
        checkGamePlay();
        Preconditions.checkNotNull(this.game, "game cannot be null");

        return this.game.getCurrentTurn() == Turn.PLAYER_ONE ? this.game.getPlayer1() : this.game.getPlayer2();
    }

    public Player getEnemyPlayer() {
        checkGamePlay();
        Preconditions.checkNotNull(this.game, "game cannot be null");

        return this.game.getCurrentTurn() == Turn.PLAYER_ONE ? this.game.getPlayer2() : this.game.getPlayer1();
    }

    public boolean hasWinner() {
        checkGamePlay();
        Player player1 = this.game.getPlayer1();
        Player player2 = this.game.getPlayer2();

        return player1.getShips().isEmpty() || player2.getShips().isEmpty();
    }

    public Player getWinner() {
        checkGamePlay();
        Player player1 = this.game.getPlayer1();
        Player player2 = this.game.getPlayer2();

        if (!hasWinner()) {
            return null;
        }
        return player1.getShips().isEmpty() ? player2 : player1;
    }

    public void switchTurn() {

        checkGamePlay();
        this.game.switchTurn();
    }

    public Movement getMovement() {

        checkGamePlay();
        return this.movement;
    }

    public Attack getAttackGame() {
        checkGamePlay();
        return this.attackGame;
    }

    public UsingEffect getUsingEffect() {
        checkGamePlay();
        return this.usingEffect;
    }

    private void checkGamePlay() {

        Preconditions.checkNotNull(this.game, "game cannot be null");
        Preconditions.checkNotNull(this.movement, "movement cannot be null");
        Preconditions.checkNotNull(this.attackGame, "attack game cannot be null");
        Preconditions.checkNotNull(this.usingEffect, "using effect cannot be null");
    }
}
