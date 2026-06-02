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
        Preconditions.checkArgument(!playerName.isBlank(),"player name cannot be empty");
        Preconditions.checkNotNull(board,"board can not be null");

        this.playerName = playerName;
        this.board = board;
        this.ships = new ArrayList<>();
        this.successfulHits = 0;
        this.missedAttacks = 0;
    }

    public void addShip(Ship ship) {

        Preconditions.checkNotNull(ship, "ship cannot be null");
        ships.add(ship);
    }

    public void moveShip(Ship ship, List<Coordinate> newCoordinates) {

        Preconditions.checkNotNull(ship);
        Preconditions.checkArgument(ships.contains(ship),"player does not own the ship");

        ship.move(newCoordinates);
    }

    public void setSuccessfulHits() {
        successfulHits++;
    }

    public void missedAttacks() {
        missedAttacks++;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Board getBoard() {
        return board;
    }

    public int getSuccessfulHits() {
        return successfulHits;
    }

    public int getMissedAttacks() {
        return missedAttacks;
    }

    public void placeShip(Ship ship) {

        Preconditions.checkNotNull(ship);
        addShip(ship);
    }
}
