package comp2450.model;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;


public class Board {

    private int xSize;
    private int ySize;
    private Cell[][] cells;
    private List<Ship> ships;
    private List<BoardEffect> effects;

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
        checkBoard();
    }

    public void addShip(Ship ship) {
        checkBoard();
        Preconditions.checkNotNull(ship, "ship cannot be null");
        ships.add(ship);

        for (Coordinate coordinate : ship.getCoordinates()) {
            Preconditions.checkArgument(isInsideBoard(coordinate), "Ship coordinate should be inside board.");
            Preconditions.checkArgument(!getCell(coordinate).containShip(), "Ship should not overlap another ship.");
        }

        for (Coordinate coordinate : ship.getCoordinates()) {
            Cell cell = getCell(coordinate);
            cell.placeShip(ship);
        }
        checkBoard();
    }

    public void removeShip(Ship ship) {
        checkBoard();

        Preconditions.checkNotNull(ship, "ship cannot be null");
        Preconditions.checkArgument(ships.contains(ship), "ship should exist on this board");
        ships.remove(ship);

        for (Coordinate coordinate : ship.getCoordinates()) {
            getCell(coordinate).removeShip();
        }
        checkBoard();
    }

    public Cell getCell(Coordinate coordinate) {
        checkBoard();
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");
        Preconditions.checkArgument(isInsideBoard(coordinate), "coordinate is outside board");

        return cells[coordinate.getX()][coordinate.getY()];
    }

    public boolean isInsideBoard(Coordinate coordinate) {
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");

        return coordinate.getX() >= 0
                && coordinate.getX() < xSize
                && coordinate.getY() >= 0
                && coordinate.getY() < ySize;
    }

    public List<Ship> getShips() {

        checkBoard();
        return ships;
    }

    public List<BoardEffect> getEffects() {
        checkBoard();
        return effects;
    }

    public int getXSize() {
        checkBoard();
        return xSize;
    }

    public int getYSize() {

        checkBoard();
        return ySize;
    }

    public void addEffect(BoardEffect effect) {
        Preconditions.checkNotNull(effect, "effect cannot be null");
        Preconditions.checkArgument(isInsideBoard(effect.getCoordinate()), "effect coordinate is outside board");

        effects.add(effect);
        checkBoard();
    }

    public void removeEffect(BoardEffect effect) {
        checkBoard();
        Preconditions.checkNotNull(effect, "effect cannot be null");
        Preconditions.checkArgument(effects.contains(effect),"Effect should exist on this board");
        effects.remove(effect);
        checkBoard();
    }

    public BoardEffect getEffect(int index) {
        checkBoard();
        Preconditions.checkArgument(index >= 0 && index < effects.size(),"effect index is invalid");

        return effects.get(index);
    }

    private void checkBoard() {
        Preconditions.checkState(xSize > 0, "xSize should be positive.");
        Preconditions.checkState(ySize > 0, "ySize should be positive.");
        Preconditions.checkNotNull(cells, "Cells should not be null.");
        Preconditions.checkNotNull(ships, "Ships should not be null.");
        Preconditions.checkNotNull(effects, "Effects should not be null.");
        Preconditions.checkState(cells.length == xSize, "Cells width should match xSize.");

        for (int x = 0; x < xSize; x++) {
            Preconditions.checkNotNull(cells[x], "Cell column should not be null.");
            Preconditions.checkState(cells[x].length == ySize, "Cells height should match ySize.");

            for (int y = 0; y < ySize; y++) {
                Preconditions.checkNotNull(cells[x][y], "Every cell should not be null.");
            }
        }
    }
}
