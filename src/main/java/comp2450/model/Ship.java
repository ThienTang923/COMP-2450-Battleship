package comp2450.model;

import com.google.common.base.Preconditions;

public class Ship {

    private int size;
    private Coordinate coordinate;
    private int health;
    private int currentHealth;
    private ShipType shipType;
    private boolean doubleDamage;
    private boolean shielded;

    public Ship(int size,Coordinate coordinate, ShipType shipType) {

        Preconditions.checkArgument(size >0, "size must be positive");
        Preconditions.checkNotNull(coordinate, "coordinates cannot be null");
        Preconditions.checkNotNull(shipType, "ship type cannot be null");

        this.size = size;
        this.health = size;
        this.currentHealth = size;
        this.coordinate = coordinate;
        this.shipType = shipType;
        this.doubleDamage = false;
        this.shielded = false;
    }

    public ShipType getShipType() {
        checkShip();
        return this.shipType;
    }

    public void move(Coordinate newCoordinate) {

        checkShip();
        Preconditions.checkNotNull(newCoordinate);

        this.coordinate = newCoordinate;
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

    public int getHealth() {
        checkShip();
        return this.health;
    }

    public int getCurrentHealth() {
        checkShip();
        return this.currentHealth;
    }

    public Coordinate getCoordinate() {
        checkShip();
        return this.coordinate;
    }

    private void checkShip() {
        Preconditions.checkArgument(this.size > 0, "size must be positive");
        Preconditions.checkNotNull(this.coordinate, "coordinates cannot be null");
        Preconditions.checkArgument(this.health == this.size, "health must match ship size");
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

        checkShip();
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
