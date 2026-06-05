package comp2450.model;

import com.google.common.base.Preconditions;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {

        Preconditions.checkArgument(x >= 0, "x coordinate must be non negative");
        Preconditions.checkArgument(y >= 0, "y coordinate must be non negative");

        this.x = x;
        this.y = y;
        checkCoordinate();
    }

    public int getX() {
        checkCoordinate();
        return this.x;
    }

    public int getY()
    {
        checkCoordinate();
        return this.y;
    }

    @Override
    public boolean equals(Object other) {
        if( this == other) {
            return true;
        }

        if (!(other instanceof Coordinate)) {
            return false;
        }

        Coordinate otherCoordinate = (Coordinate) other;

        return this.x == otherCoordinate.x && this.y == otherCoordinate.y;
    }

    private void checkCoordinate() {
        Preconditions.checkState(x >= 0, "x coordinate should be non-negative.");
        Preconditions.checkState(y >= 0, "y coordinate should be non-negative.");
    }
}
