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

        checkCell();
    }

    public void placeShip(Ship ship) {
        checkCell();

        Preconditions.checkNotNull(ship, "Ship cannot be null");
        Preconditions.checkState(this.ship == null, "Cell already has a ship");

        this.ship = ship;
        checkCell();
    }
    
    public boolean containShip() {
        checkCell();
        return this.ship != null;
    }

    public void removeShip() {
        checkCell();

        ship = null;
        checkCell();
    }

    public void markAttacked() {

        checkCell();
        this.attacked = true;
    }

    public boolean isAttacked() {
        checkCell();
        return this.attacked;
    }

    public Ship getShip() {
        checkCell();
        return this.ship;
    }

    public Coordinate getCoordinate() {
        checkCell();
        return this.coordinate;
    }

    private void checkCell() {
        Preconditions.checkNotNull(coordinate, "Coordinate should not be null");
    }
}
