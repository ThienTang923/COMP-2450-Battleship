package comp2450.model;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;


public class Board {

    private int xSize;
    private int ySize;
    private Cell[][] cells;
    private List<Ship> ships;
    private List<Effect> effects;

    public Board(int xSize, int ySize) {

        Preconditions.checkArgument(xSize>0, "xSize must be positive");
        Preconditions.checkArgument(ySize>0, "ySize must be positive");

        this.xSize = xSize;
        this.ySize = ySize;
        this.cells = new Cell[xSize][ySize];
        this.ships = new ArrayList<>();
        this.effects = new ArrayList<>();

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                cells[x][y] =  new Cell(new Coordinate(x,y));
            }
        }
    }

    public void addShip(Ship ship) {
        Preconditions.checkNotNull(ship, "ship cannot be null");
        ships.add(ship);

        for (Coordinate coordinate : ship.getCoordinates()) {
            Cell cell = getCell(coordinate);
            cell.placeShip(ship);
        }
    }

    public void removeShip(Ship ship) {
        Preconditions.checkNotNull(ship, "ship cannot be null");
        ships.remove(ship);

        for (Coordinate coordinate : ship.getCoordinates()) {
            getCell(coordinate).removeShip();
        }
    }

    public Cell getCell(Coordinate coordinate) {
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");
        Preconditions.checkArgument(isInsideBoard(coordinate),
                "coordinate is outside board");

        return cells[coordinate.getX()][coordinate.getY()];
    }

    public boolean isInsideBoard(Coordinate coordinate) {
        return coordinate.getX() >= 0
                && coordinate.getX() < xSize
                && coordinate.getY() >= 0
                && coordinate.getY() < ySize;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }
}
