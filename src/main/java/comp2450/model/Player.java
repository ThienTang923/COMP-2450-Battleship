package comp2450.model;

import java.util.ArrayList;

public class Player {
    String name;
    ArrayList<Ship> ships;
    int successfulHits;
    int missedAttacks;
    Map map;

    public Player(String name) {
        if (name != null && name != "") {
            this.name = name;
        }
    }

    public void moveShip() {

    }

    public void placeShip() {

    }

    public void attack(Coordinate targetCoordinate) {

    }
}
