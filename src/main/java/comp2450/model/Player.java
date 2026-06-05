package comp2450.model;

import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Preconditions;

public class Player {
    private String playerName;
    private List<Ship> ships;
    private int successfulHits;
    private int missedAttacks;
    private Board board;

    public Player(String playerName, Board board) {

        Preconditions.checkNotNull(playerName,"player name cannot be null");
        Preconditions.checkState(!playerName.isBlank(),"player name cannot be empty");
        Preconditions.checkNotNull(board,"board can not be null");

        this.playerName = playerName;
        this.board = board;
        this.ships = new ArrayList<>();
        this.successfulHits = 0;
        this.missedAttacks = 0;

        checkPlayer();
    }

    public void addShip(Ship ship) {
        checkPlayer();

        Preconditions.checkNotNull(ship, "ship cannot be null");
        ships.add(ship);

        checkPlayer();
    }

    public void moveShip(Ship ship, List<Coordinate> newCoordinates) {
        checkPlayer();
        Preconditions.checkNotNull(ship);
        Preconditions.checkState(ships.contains(ship),"player does not own the ship");
        Preconditions.checkNotNull(newCoordinates, "new coordinates cannot be null");

        ship.move(newCoordinates);
        checkPlayer();
    }

    public void setSuccessfulHits() {

        checkPlayer();
        successfulHits++;
        checkPlayer();
    }

    public void missedAttack() {
        checkPlayer();
        missedAttacks++;
        checkPlayer();
    }

    public String getPlayerName() {
        checkPlayer();
        return playerName;
    }

    public List<Ship> getShips() {
        checkPlayer();
        return ships;
    }

    public Board getBoard() {
        checkPlayer();
        return board;
    }

    public int getSuccessfulHits() {
        checkPlayer();
        return successfulHits;
    }

    public int getMissedAttacks() {

        checkPlayer();
        return missedAttacks;
    }

    public void placeShip(Ship ship) {
        checkPlayer();
        Preconditions.checkNotNull(ship);
        addShip(ship);
        checkPlayer();
    }

    public void removeShip(Ship ship) {
        checkPlayer();
        Preconditions.checkNotNull(ship, "ship can no be null");

        this.ships.remove(ship);
        
        checkPlayer();
    }
    private void checkPlayer() {
        Preconditions.checkState(this.playerName != null, "player name cannot be null");
        Preconditions.checkState(!this.playerName.isBlank(), "player name cannot be blank");
        Preconditions.checkState(this.ships != null, "ships cannot be null");
        Preconditions.checkState(this.successfulHits >= 0, "successful hits cannot be negative");
        Preconditions.checkState(this.missedAttacks >= 0, "missed attacks cannot be negative");
        Preconditions.checkState(this.board != null, "board cannot be null");
    }
}
