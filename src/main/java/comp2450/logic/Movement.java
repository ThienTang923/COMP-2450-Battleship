package comp2450.logic;

import com.google.common.base.Preconditions;
import comp2450.exceptions.InvalidMoveException;
import comp2450.model.*;

import java.util.ArrayList;

public class Movement {

    private PathFinder pathFinder;

    public Movement() {
        this.pathFinder = new PathFinder();
    }

    private Coordinate getCoordinateAfterMove(Board board, Coordinate coordinate, String direction) throws InvalidMoveException {

        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");
        Preconditions.checkNotNull(direction, "direction cannot be null");

        int x = coordinate.getX();
        int y = coordinate.getY();

        int newX = x;
        int newY = y;

        if (direction.equalsIgnoreCase("UP")) {
            newY = y - 1;
        } else if (direction.equalsIgnoreCase("DOWN")) {
            newY = y + 1;
        } else if (direction.equalsIgnoreCase("LEFT")) {
            newX = x - 1;
        } else if (direction.equalsIgnoreCase("RIGHT")) {
            newX = x + 1;
        } else {
            throw new InvalidMoveException("Invalid direction. Please enter UP, DOWN, LEFT, or RIGHT.");
        }

        if (newX < 0 || newY < 0 || newX >= board.getXSize() || newY >= board.getYSize()) {
            throw new InvalidMoveException("Ship cannot move outside the board. Choose another direction.");
        }

        return new Coordinate(newX, newY);

    }
    public boolean moveNormalShip(Board board, Ship ship, String direction) throws InvalidMoveException {

        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkNotNull(direction, "direction cannot be null");

        if (ship.getShipType() != ShipType.NORMAL) {
            throw new InvalidMoveException("Only NORMAL ship can use normal movement.");
        }

        Coordinate oldCoordinate = ship.getCoordinate();
        Coordinate newCoordinate = getCoordinateAfterMove(board, oldCoordinate, direction);

        Cell newCell = board.getCell(newCoordinate);

        if (newCell.containShip()) {
            throw new InvalidMoveException("Ship cannot move there because another ship already ocupies that location.");
        }

        board.removeShip(ship);
        ship.move(newCoordinate);
        board.addShip(ship);

        return true;
    }

    public boolean moveSubmarine(Board board, Ship ship, Coordinate target) throws InvalidMoveException {

        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkNotNull(target, "target cannot be null");

        if (ship.getShipType() != ShipType.SUBMARINE) {
            throw new InvalidMoveException("Only SUBMARINE ships can use submarine movement.");
        }

        if (!board.isInsideBoard(target)) {
            throw new InvalidMoveException("Submarine cannot move to a coordinate that was already attacked. ");
        }

        Cell targetCell = board.getCell(target);

        if (targetCell.isAttacked()) {
            throw new InvalidMoveException("Submarine cannot move to a coordinate that was already attacked");
        }

        if (targetCell.containShip()) {
            throw new InvalidMoveException("Submarine cannot move there because another ship already occupies that location");
        }

        Coordinate start = ship.getCoordinate();
        boolean hasPath = this.pathFinder.hasSubmarine(board, start, target);

        if (!hasPath) {
            throw new InvalidMoveException("Submarine cannot move there. The target must be connected by attacked cells.");
        }


        board.removeShip(ship);
        ship.move(target);
        board.addShip(ship);

        return true;
    }
}
