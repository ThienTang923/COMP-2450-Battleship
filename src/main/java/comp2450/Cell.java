package comp2450;

public class Cell {
    private Coordinate coordinate;
    private Ship ship;
    private Status attackedStatus;

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.ship = null;
        this.attacked = false;
    }

    public void containShip(Ship ship) {
        if (ship != null) {
            this.ship = ship;
        }
    }

    public void removeShip() {
        ship = null;
    }

    public void markAttacked() {

    }

    public boolean containShip () {
        return ship != null;
    }
}
