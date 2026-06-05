package comp2450.logic;

import com.google.common.base.Preconditions;
import comp2450.model.Game;
import comp2450.model.Player;
import comp2450.model.Turn;

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
    }

    public Player getCurrentPlayer() {
        Preconditions.checkNotNull(this.game, "game cannot be null");

        return this.game.getCurrentTurn() == Turn.PLAYER_ONE ? this.game.getPlayer1() : this.game.getPlayer2();
    }

    public Player getEnemyPlayer() {
        Preconditions.checkNotNull(this.game, "game cannot be null");

        return this.game.getCurrentTurn() == Turn.PLAYER_ONE ? this.game.getPlayer2() : this.game.getPlayer1();
    }

    public boolean hasWinnier() {
        Player player1 = this.game.getPlayer1();
        Player player2 = this.game.getPlayer2();

        return player1.getShips().isEmpty() || player2.getShips().isEmpty();
    }

    public Player getWinner() {
        Player player1 = this.game.getPlayer1();
        Player player2 = this.game.getPlayer2();

        if (!hasWinnier()) {
            return null;
        }
        return player1.getShips().isEmpty() ? player2 : player1;
    }

    public void switchTurn() {
        this.game.switchTurn();
    }

    public Movement getMovement() {
        return this.movement;
    }

    public Attack getAttackGame() {
        return this.attackGame;
    }

    public UsingEffect getUsingEffect() {
        return this.usingEffect;
    }
}
