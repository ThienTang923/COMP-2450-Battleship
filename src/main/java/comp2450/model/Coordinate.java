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
    }

    public int getX() {

        return this.x;
    }

    public int getY()
    {
        return this.y;
    }
}
