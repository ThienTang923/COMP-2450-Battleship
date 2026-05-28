package comp2450.model;

public class Cell {

    private Coordinate coordinate;
    private Ship ship;
    private Status attackedStatus;

    public Cell(Coordinate coordinate) {

        Precondition.checkNotNull(coordinate, "coordinate cannot be null");

        this.coordinate = coordinate;
        this.ship = null;
        this.attacked = false;
    }

    public void placeShip(Ship ship) {
        Precondition.checkNotNull(ship, "Ship cannot be null");
        Precondition.checkState(this.ship == null, "Cell already has a ship");

        this.ship = ship;
    }
    
    public boolean containShip() {
        return this.ship != null;
    }

    public void removeShip() {

        ship = null;
    }

    public void markAttacked() {
        this.attack = true;
    }

    public boolean isAttacked() {
        return this.attack;
    }

    public Ship getShip() {
        return this.ship;
    }

    public Coordinate getCoordinate() {
        return this.cooridnate;
    }
}
