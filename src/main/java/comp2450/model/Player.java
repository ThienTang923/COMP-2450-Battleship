package comp2450.model;

import java.util.List;

public class Player {
    private String playerName;
    private List<Ship> ships;
    private int successfulHits;
    private int missedAttacks;
    private Board board;

    public Player(String playerName, Board board) {

        Precondition.checkNotNull(playerName,"player name cannot be null");
        Precondition.checkArgument(!playerName.isBlank(),"player name cannot be empty");
        Precondition.checkNotNull(board,"map can not be null");

        this.playerName = playerName;
        this.board = board;
        this.ship = new ArrayList<>();
        this.successfulHits = 0;
        this.missedAttacks = 0;
    }

    public voi addShip(Ship ship) {

        Precondition.checkNotNull(ship, "ship cannot be null");
        ships.add(ship);
    }

    public void moveShip(Ship ship, List<Coordinate> newCoordinates) {

        Precondition.checkNotNull(ship);
        Precondition.checkArgument(ships.contains(ship),"player does not own the ship");

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

    public Board getMap() {
        return board;
    }

    public int getSuccessfulHits() {
        return successfulHits;
    }

    public int getMissedAttacks() {
        return missedAttacks;
    }

    public void placeShip(Ship ship) {

        Precondition.checkNotNull(ship);
        ships.add(ship);
    }
}
