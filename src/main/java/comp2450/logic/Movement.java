package comp2450.logic;

import com.google.common.base.Preconditions;
import comp2450.exceptions.InvalidInputException;
import comp2450.exceptions.InvalidMoveException;
import comp2450.model.*;

import java.util.ArrayList;

public class Movement {

    private PathFinder pathFinder;

    public Movement() {
        this.pathFinder = new PathFinder();
    }

    private Coordinate getCoordinateAfterMove(Coordinate coordinate, String direction) throws InvalidMoveException {
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");
        Preconditions.checkNotNull(direction, "direction cannot be null");

        int x = coordinate.getX();
        int y = coordinate.getY();

        if (direction.equalsIgnoreCase("UP")) {
            return new Coordinate(x, y-1);
        } else if (direction.equalsIgnoreCase("DOWN")) {
            return new Coordinate(x, y+1);
        } else if (direction.equalsIgnoreCase("LEFT")) {
            return new Coordinate(x-1, y);
        } else if (direction.equalsIgnoreCase("RIGHT")) {
            return new Coordinate(x+1,y);
        }

        throw new InvalidMoveException("Invalid direction. Please enter UP, DOWN, LEFT or RIGHT.");
    }
    public boolean moveNormalShip(Board board, Ship ship, String direction) throws InvalidMoveException {

        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkNotNull(direction, "direction cannot be null");
        Preconditions.checkArgument(ship.getShipType() == ShipType.NORMAL, "ship must be normal ship");

        if (ship.getShipType() != ShipType.NORMAL) {
            throw new InvalidMoveException("Only NORMAL ship can use normal movement.");
        }
        Coordinate oldCoordinate = ship.getCoordinates().get(0);
        Coordinate newCoordinate = getCoordinateAfterMove(oldCoordinate, direction);

        if (!board.isInsideBoard(newCoordinate)) {
            throw new InvalidMoveException("Ship cannot move outside the board. Choose another direction");
        }

        Cell newCell = board.getCell(newCoordinate);

        if (newCell.containShip()) {
            throw new InvalidMoveException("Ship cannot move there because another ship already ocupies that location.");
        }

        ArrayList<Coordinate> newCoordinates = new ArrayList<>();
        newCoordinates.add(newCoordinate);

        board.removeShip(ship);
        ship.move(newCoordinates);
        board.addShip(ship);

        return true;
    }

    public boolean moveSubmarine(Board board, Ship ship, Coordinate target) throws InvalidMoveException {
        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkNotNull(target, "target cannot be null");
        Preconditions.checkArgument(ship.getShipType() == ShipType.SUBMARINE,"ship must be submarine");

        if (ship.getShipType() != ShipType.SUBMARINE) {
            throw new InvalidMoveException("Only SUBMARINE ships can use submarine movement.");
        }

        if (!board.isInsideBoard(target)) {
            throw new InvalidMoveException("Submarine target is outside the board. Choose a valid coordinate.");
        }

        Cell targetCell = board.getCell(target);

        if (targetCell.isAttacked()) {
            throw new InvalidMoveException("Submarine cannot move to a coordinate that was already attacked");
        }

        if (targetCell.containShip()) {
            throw new InvalidMoveException("Submarine cannot move there because another ship already occupies that location");
        }

        Coordinate start = ship.getCoordinates().get(0);
        boolean hasPath = this.pathFinder.hasSubmarine(board, start, target);

        if (!hasPath) {
            throw new InvalidMoveException("Submarine cannot move there. The target must be connected by attacked cells.");
        }

        ArrayList<Coordinate> newCoordinates = new ArrayList<>();
        newCoordinates.add(target);

        board.removeShip(ship);
        ship.move(newCoordinates);
        board.addShip(ship);

        return true;
    }
}
