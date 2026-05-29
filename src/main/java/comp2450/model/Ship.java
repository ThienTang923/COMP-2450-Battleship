package comp2450.model;

import java.util.List;
import com.google.common.base.Preconditions;

public class Ship {

    private int size;
    private List<Coordinate> coordinates;
    private int health;
    private int currentHealth;

    public Ship(int size, List<Coordinate> coordinates) {

        Preconditions.checkArgument(size >0, "size must be positive");
        Preconditions.checkNotNull(coordinates, "coordinates cannot be null");
        Preconditions.checkArgument(coordinates.size() == size, "cooridnate must match ship size");

        this.size = size;
        this.health = health;
        this.currentHealth = size;
        this.coordinates = coordinates;
    }

    public void move(List<Coordinate> newCoordinates) {

        Preconditions.checkNotNull(newCoordinates);
        Preconditions.checkArgument(newCoordinates.size() == size, "new cooridnates must match ship size");

        this.coordinates = newCoordinates;
    }

    public void takeDamage() {
        Preconditions.checkState(currentHealth > 0, "ship is already destroyed");

        currentHealth--;
    }

    public boolean isSunk() {
        return currentHealth ==0;
    }

    public int getSize() {
        return this.size;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public List<Coordinate> getCoordinates() {
        return this.coordinates;
    }

}
