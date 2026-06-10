package comp2450.tests;

import comp2450.model.*;

import java.util.ArrayList;

public class BoardTest implements TestSuite {

    private TestResults results;

    public String suiteName() {
        return "Board Tests";
    }

    public TestResults runTests() {
        this.results = new TestResults();

        testBoardStoreSize();
        testCoordinateInsideBoard();
        testCoordinateOutsideBoard();
        testBoardAddShip();
        testBoardRejectsShipOutsideBoard();
        testBoardRejectShipOverlap();
        testBoardRemoveShip();
        testBoardRemoveEffect();
        testBoardAddEffect();

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

    private Ship createSizeTwoShipAt(int x, int y) {

        Coordinate coordinate = new Coordinate(x,y);

        return new Ship(2, coordinate, ShipType.NORMAL);
    }

    private void testBoardStoreSize() {
        Board board = new Board(10,10);

        boolean inside = board.isInsideBoard(new Coordinate(5,5));

        check(inside, "Board identifies coordinate inside board. ");
    }

    private void testCoordinateInsideBoard() {
        Board board = new Board(10,10);

        boolean inside = board.isInsideBoard(new Coordinate(5,5));

        check(inside, "Board identifies coordinate inside board. ");
    }

    private void testCoordinateOutsideBoard() {
        Board board = new Board(10,10);

        boolean outside = !board.isInsideBoard(new Coordinate(10,5));

        check(outside, "Board identifies coordinate outside board");
    }

    private void testBoardAddShip() {
        Board board = new Board(10,10);

        Ship ship = createSizeTwoShipAt(2,3);
        board.addShip(ship);

        boolean boardHasShip = board.getShips().contains(ship);
        boolean cellHasShip = board.getCell(new Coordinate(2,3)).getShip() == ship;

        check(boardHasShip && cellHasShip, "Board can add ship to one coordinate");
    }

    private void testBoardRejectsShipOutsideBoard() {
        boolean exceptionThrown = false;

        try {
            Board board = new Board(10,10);
            Ship ship = createSizeTwoShipAt(10,3);

            board.addShip(ship);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        check (exceptionThrown, "Board rejects ship outside board");
    }

    private void testBoardRejectShipOverlap() {

        boolean exceptionThrown = false;

        try {
            Board board = new Board(10,10);
            Ship first = createSizeTwoShipAt(2,3);
            Ship second = createSizeTwoShipAt(2,3);

            board.addShip(first);
            board.addShip(second);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        check(exceptionThrown, "Board rejects overlapping ships");
    }

    private void testBoardRemoveShip() {
        Board board = new Board(10,10);
        Ship ship = createSizeTwoShipAt(3,3);

        board.addShip(ship);
        board.removeShip(ship);

        boolean boardDoesNotContainShip = !board.getShips().contains(ship);
        boolean cellDoesNotContainShip = board.getCell(new Coordinate(2,3)).getShip() == null;

        check(boardDoesNotContainShip && cellDoesNotContainShip, "Board can remove ship");
    }

    private void testBoardAddEffect() {
        Board board = new Board(10,10);
        BoardEffect boardEffect = new BoardEffect(Effect.DOUBLE_DAMAGE, new Coordinate(4,4));

        board.addEffect(boardEffect);

        boolean boardHasEffect = board.getEffects().contains(boardEffect);

        check(boardHasEffect, "Board can add effect");
    }

    private void testBoardRemoveEffect() {
        Board board = new Board(10,10);
        BoardEffect boardEffect = new BoardEffect(Effect.DOUBLE_DAMAGE, new Coordinate(4,4));

        board.addEffect(boardEffect);
        board.removeEffect(boardEffect);

        boolean boardDoesNotContainEffect = !board.getEffects().contains(boardEffect);

        check(boardDoesNotContainEffect, "Board can remove effect");
    }
}
