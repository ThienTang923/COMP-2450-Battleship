package comp2450.model;

import java.util.List;
import com.google.common.base.Preconditions;

public class Ship {

    private int size;
    private List<Coordinate> coordinates;
    private int health;
    private int currentHealth;
    private ShipType shipType;
    private boolean doubleDamage;
    private boolean shielded;

    public Ship(int size, List<Coordinate> coordinates, ShipType shipType) {

        Preconditions.checkArgument(size >0, "size must be positive");
        Preconditions.checkNotNull(coordinates, "coordinates cannot be null");
        Preconditions.checkArgument(coordinates.size() == size, "coordinates must match ship size");
        Preconditions.checkNotNull(shipType, "ship type cannot be null");

        this.size = size;
        this.health = size;
        this.currentHealth = size;
        this.coordinates = coordinates;
        this.shipType = shipType;
        this.doubleDamage = false;
        this.shielded = false;
    }

    public ShipType getShipType() {
        checkShip();
        return this.shipType;
    }

    public void move(List<Coordinate> newCoordinates) {

        checkShip();
        Preconditions.checkNotNull(newCoordinates);
        Preconditions.checkArgument(newCoordinates.size() == size, "new coordinates must match ship size");

        this.coordinates = newCoordinates;
        checkShip();
    }

    public boolean isSunk() {
        checkShip();
        return currentHealth ==0;
    }

    public int getSize() {
        checkShip();
        return this.size;
    }

    public int getCurrentHealth() {
        checkShip();
        return this.currentHealth;
    }

    public List<Coordinate> getCoordinates() {
        checkShip();
        return this.coordinates;
    }

    private void checkShip() {
        Preconditions.checkArgument(this.size > 0, "size must be positive");
        Preconditions.checkNotNull(this.coordinates, "coordinates cannot be null");
        Preconditions.checkArgument(this.coordinates.size() == this.size, "coordinates size must match ship size");
        Preconditions.checkNotNull(this.shipType, "ship type cannot be null");
        Preconditions.checkArgument(this.currentHealth >= 0, "current health cannot be negative");
        Preconditions.checkArgument(this.currentHealth <= this.health, "current health cannot be greater than health");
    }

    public void activateDoubleDamage() {
        checkShip();

        this.doubleDamage = true;

        checkShip();
    }

    public boolean hasDoubleDamage() {
        checkShip();

        return this.doubleDamage;
    }

    public int useAttackDamage() {
        checkShip();

        int damage;

        if (this.doubleDamage) {
            damage = 2;
            this.doubleDamage = false;
        } else {
            damage = 1;
        }

        checkShip();
        return damage;
    }

    public void activateShield() {
        checkShip();

        this.shielded = true;
    }

    public boolean hasShield() {
        checkShip();

        return this.shielded;
    }

    public void takeDamage() {

        checkShip();

        if (this.shielded) {
            this.shielded = false;
        } else {
            Preconditions.checkState(this.currentHealth > 0, "ship is already destroyed");
            this.currentHealth--;
        }

        checkShip();
    }

    public void heal() {
        checkShip();

        if (this.currentHealth < this.health) {
            this.currentHealth++;
        }

        checkShip();
    }
}
