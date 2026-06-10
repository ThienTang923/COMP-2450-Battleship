package comp2450.tests;

import comp2450.model.Cell;
import comp2450.model.Coordinate;
import comp2450.model.Ship;
import comp2450.model.ShipType;

import java.util.ArrayList;

public class CellTests implements  TestSuite {

    private TestResults results;

    public String suiteName() {
        return "Cell Tests";
    }

    public TestResults runTests() {
        this.results = new TestResults();

        testCellStartsEmptyAndNotAttacked();
        testCellPlaceShip();
        testCellRemoveShip();
        testCellMarkAttacked();

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

    private Ship createOneCellNormalShip() {

        Coordinate coordinate = new Coordinate(1,1);

        return new Ship(1,coordinate, ShipType.NORMAL);
    }

    private void testCellStartsEmptyAndNotAttacked() {
        Cell cell = new Cell(new Coordinate(1,1));

        boolean doesNotContainShip = !cell.containShip();
        boolean isNotAttacked = !cell.isAttacked();

        check(doesNotContainShip && isNotAttacked, "Cell starts empty and not attacked.");
    }

    private void testCellPlaceShip() {
        Cell cell = new Cell(new Coordinate(1,1));
        Ship ship = createOneCellNormalShip();

        cell.placeShip(ship);

        boolean containsShip = cell.containShip();
        boolean sameShip = cell.getShip() == ship;

        check (containsShip && sameShip, "Cell can place ship. ");
    }

    private void testCellRemoveShip() {
        Cell cell = new Cell(new Coordinate(1,1));
        Ship ship = createOneCellNormalShip();

        cell.placeShip(ship);
        cell.removeShip();

        boolean doesNotContainShip = !cell.containShip();
        boolean shipIsNull = cell.getShip() == null;

        check(doesNotContainShip && shipIsNull, "Cell can remove ship. ");
    }

    private void testCellMarkAttacked() {
        Cell cell = new Cell(new Coordinate(1,1));

        cell.markAttacked();

        check(cell.isAttacked(), "Cell can be marked attacked. ");
    }
}
