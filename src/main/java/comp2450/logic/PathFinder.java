package comp2450.logic;

import comp2450.model.Board;
import comp2450.model.Cell;
import comp2450.model.Coordinate;
import com.google.common.base.Preconditions;
import comp2450.stack.LinkedPathStack;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    public boolean hasSubmarine(Board board, Coordinate start, Coordinate target) {

        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(start, "start cannot be null");
        Preconditions.checkNotNull(target, "target cannot be null");
        Preconditions.checkArgument(board.isInsideBoard(start), "start must be inside board");
        Preconditions.checkArgument(board.isInsideBoard(target), "target must be inside board");

        Cell targetCell = board.getCell(target);

        if (targetCell.isAttacked()) {
            return false;
        }

        LinkedPathStack<Coordinate> stack = new LinkedPathStack<>();
        ArrayList<Coordinate> visited = new ArrayList<>();

        stack.push(start);

        while (!stack.isEmpty()) {

            Coordinate current = stack.pop();

            if (!containsCoordinate(visited,current)) {

                visited.add(current);

                ArrayList<Coordinate> neighbours = getNeighbours(board, current);

                for (Coordinate next : neighbours) {
                    if (next.equals(target)) {
                        return true;
                    }

                    Cell nextCell = board.getCell(next);

                    if (nextCell.isAttacked() && !containsCoordinate(visited,next)) {
                        stack.push(next);
                    }
                }
            }

        }
        return false;
    }

    private ArrayList<Coordinate> getNeighbours(Board board, Coordinate coordinate) {
        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");

        ArrayList<Coordinate> neighbours = new ArrayList<>();

        int x = coordinate.getX();
        int y = coordinate.getY();

        addIfInsideBoard(board, neighbours, x+1,y);
        addIfInsideBoard(board, neighbours, x-1,y);
        addIfInsideBoard(board, neighbours, x,y+1);
        addIfInsideBoard(board, neighbours, x,y-1);

        return neighbours;
    }

    private void addIfInsideBoard(Board board, ArrayList<Coordinate> coordinates, int x, int y) {
        Preconditions.checkNotNull(board, "board cannot be null");
        Preconditions.checkNotNull(coordinates, "coordinates cannot be null");

        if (x >= 0 && y>=0 && x < board.getXSize() && y < board.getYSize()) {
            coordinates.add(new Coordinate(x, y));
        }
    }

    private boolean containsCoordinate(ArrayList<Coordinate> coordinates, Coordinate target) {
        Preconditions.checkNotNull(coordinates, "coordinates cannot be null");
        Preconditions.checkNotNull(target, "target cannot be null");

        for (Coordinate coordinate : coordinates) {
            if(coordinate.equals(target)) {
                return true;
            }
        }

        return false;
    }
}
