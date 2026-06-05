package comp2450.logic;

import com.google.common.base.Preconditions;
import comp2450.model.*;

import java.util.ArrayList;

public class Movement {

    private PathFinder pathFinder;

    public Movement() {
        this.pathFinder = new PathFinder();
    }

    private Coordinate getCoordinateAfterMove(Coordinate coordinate, String direction) {
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

        return coordinate;
    }
    public boolean moveNormalShip(Board board, Ship ship, String direction) {

        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkNotNull(direction, "direction cannot be null");
        Preconditions.checkArgument(ship.getShipType() == ShipType.NORMAL, "ship must be normal ship");

        Coordinate oldCoordinate = ship.getCoordinates().get(0);
        Coordinate newCoordinate = getCoordinateAfterMove(oldCoordinate, direction);

        if (!board.isInsideBoard(newCoordinate)) {
            return false;
        }

        Cell newCell = board.getCell(newCoordinate);

        if (newCell.containShip()) {
            return false;
        }

        ArrayList<Coordinate> newCoordinates = new ArrayList<>();
        newCoordinates.add(newCoordinate);

        board.removeShip(ship);
        ship.move(newCoordinates);
        board.addShip(ship);

        return true;
    }

    public boolean moveSubmarine(Board board, Ship ship, Coordinate target) {
        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkNotNull(target, "target cannot be null");
        Preconditions.checkArgument(ship.getShipType() == ShipType.SUBMARINE,"ship must be submarine");

        Coordinate start = ship.getCoordinates().get(0);

        if (!board.isInsideBoard(target)) {
            return false;
        }

        Cell targetCell = board.getCell(target);

        if (targetCell.containShip()) {
            return false;
        }

        boolean hasPath = this.pathFinder.hasSubmarine(board, start, target);

        if (!hasPath) {
            return false;
        }

        ArrayList<Coordinate> newCoordinates = new ArrayList<>();
        newCoordinates.add(target);

        board.removeShip(ship);
        ship.move(newCoordinates);
        board.addShip(ship);

        return true;
    }
}
