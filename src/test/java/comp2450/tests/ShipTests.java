package comp2450.tests;

import comp2450.model.Coordinate;
import comp2450.model.Ship;
import comp2450.model.ShipType;

import java.util.ArrayList;

public class ShipTests implements TestSuite {

    private TestResults results;

    public String suiteName() {
        return "Ship Tests";
    }

    public TestResults runTests() {
        this.results = new TestResults();

        testShipStoresSizeHealthAndOneCoordinate();
        testShipRejectsZeroSize();
        testShipStoresOneCoordinate();
        testShipStartsNotSunk();
        testShipTakeDamage();
        testShipIsSunkAfterDamageEqualsSize();
        testShipMoveChangesOnlyCoordinate();
        testDoubleDamageWorksOnce();
        testShieldBlocksOneDamage();
        testHealIncreasesHealthButNotAboveMax();

        return this.results;
    }

    private void check(boolean condition, String testName) {
        if (condition) {
            this.results.recordSuccess();
            System.out.println("[PASS] " + testName);
        } else {
            this.results.recordFailure();
            System.out.println("[FAIL] " + testName);
        }
    }

    private Ship createSizeTwoNormalShip() {
        Coordinate coordinate = new Coordinate(1,1);

        return new Ship(2, coordinate, ShipType.NORMAL);
    }

    private void testShipStoresSizeHealthAndOneCoordinate() {
        Ship ship = createSizeTwoNormalShip();

        boolean correctSize = ship.getSize() == 2;
        boolean correctHealth = ship.getHealth() == 2;
        boolean correctCurrentHealth = ship.getCurrentHealth() == 2;
        boolean correctCoordinate = ship.getCoordinate().equals(new Coordinate(1, 1));
        boolean correctType = ship.getShipType() == ShipType.NORMAL;

        check(correctSize && correctHealth && correctCurrentHealth && correctCoordinate && correctType, "Ship stores size, health, one coordinate, and type");

    }

    private void testShipStoresOneCoordinate() {
        Ship ship = new Ship(2, new Coordinate(1, 1), ShipType.NORMAL);

        boolean correctCoordinate = ship.getCoordinate().equals(new Coordinate(1, 1));
        boolean correctSize = ship.getSize() == 2;

        check(correctCoordinate && correctSize,
                "Ship stores one coordinate and size separately");
    }

    private void testShipRejectsZeroSize() {
        boolean exceptionThrown = false;

        try {
            Ship ship = new Ship(0, new Coordinate(1, 1), ShipType.NORMAL);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        check(exceptionThrown,
                "Ship rejects zero size");
    }

    private void testShipStartsNotSunk() {
        Ship ship = createSizeTwoNormalShip();

        check(!ship.isSunk(),
                "Ship starts not sunk");
    }

    private void testShipTakeDamage() {
        Ship ship = createSizeTwoNormalShip();

        ship.takeDamage();

        check(ship.getCurrentHealth() == 1,
                "Ship health decreases after taking damage");
    }

    private void testShipIsSunkAfterDamageEqualsSize() {
        Ship ship = createSizeTwoNormalShip();

        ship.takeDamage();
        ship.takeDamage();

        check(ship.isSunk(),
                "Ship is sunk after damage equals ship size");
    }

    private void testShipMoveChangesOnlyCoordinate() {
        Ship ship = createSizeTwoNormalShip();

        Coordinate newCoordinate = new Coordinate(2, 2);
        ship.move(newCoordinate);

        boolean newCoordinateCorrect = ship.getCoordinate().equals(new Coordinate(2, 2));
        boolean healthDidNotChange = ship.getCurrentHealth() == 2;
        boolean sizeDidNotChange = ship.getSize() == 2;

        check(sizeDidNotChange && newCoordinateCorrect && healthDidNotChange,
                "Ship move changes only coordinate and does not change health");
    }

    private void testDoubleDamageWorksOnce() {
        Ship ship = createSizeTwoNormalShip();

        ship.activateDoubleDamage();

        int firstDamage = ship.useAttackDamage();
        int secondDamage = ship.useAttackDamage();

        boolean firstIsDouble = firstDamage == 2;
        boolean secondIsNormal = secondDamage == 1;
        boolean doubleDamageTurnedOff = !ship.hasDoubleDamage();

        check(firstIsDouble && secondIsNormal && doubleDamageTurnedOff,
                "Double damage works once and then turns off");
    }

    private void testShieldBlocksOneDamage() {
        Ship ship = createSizeTwoNormalShip();

        ship.activateShield();
        ship.takeDamage();

        boolean healthStayedSame = ship.getCurrentHealth() == 2;
        boolean shieldTurnedOff = !ship.hasShield();

        check(healthStayedSame && shieldTurnedOff,
                "Shield blocks one damage and then turns off");
    }

    private void testHealIncreasesHealthButNotAboveMax() {
        Ship ship = createSizeTwoNormalShip();

        ship.takeDamage();
        ship.heal();
        ship.heal();

        check(ship.getCurrentHealth() == 2,
                "Heal increases health but not above max");
    }
}
