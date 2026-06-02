package comp2450.model;

import com.google.common.base.Preconditions;

public class Cell {

    private Coordinate coordinate;
    private Ship ship;
    private boolean attacked;

    public Cell(Coordinate coordinate) {

        Preconditions.checkNotNull(coordinate, "coordinate cannot be null");

        this.coordinate = coordinate;
        this.ship = null;
        this.attacked = false;
    }

    public void placeShip(Ship ship) {
        Preconditions.checkNotNull(ship, "Ship cannot be null");
        Preconditions.checkState(this.ship == null, "Cell already has a ship");

        this.ship = ship;
    }
    
    public boolean containsShip() {
        return this.ship != null;
    }

    public void removeShip() {

        ship = null;
    }

    public void markAttacked() {
        this.attacked = true;
    }

    public boolean isAttacked() {
        return this.attacked;
    }

    public Ship getShip() {
        return this.ship;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }
}
