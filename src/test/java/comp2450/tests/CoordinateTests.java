package comp2450.tests;

import comp2450.model.Coordinate;

public class CoordinateTests implements  TestSuite {
    private TestResults results;

    public String suiteName() {
        return "Coordinate Tests";
    }

    public TestResults runTests() {
        this.results = new TestResults();

        testCoordinateValid();
        testCoordinateNegativeX();
        testCoordinateNegativeY();
        testCoordinateEquals();
        testCoordinateNotEquals();

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

    private void testCoordinateValid() {
        Coordinate coordinate = new Coordinate(2, 3);

        boolean correctX = coordinate.getX() == 2;
        boolean correctY = coordinate.getY() == 3;

        check(correctX && correctY, "Coordinate stores valid x and y");
    }

    private void testCoordinateNegativeX() {
        boolean exceptionThrown = false;

        try {
            Coordinate coordinate = new Coordinate(-1, 3);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        check(exceptionThrown, "Coordinate rejects negative x");
    }

    private void testCoordinateNegativeY() {
        boolean exceptionThrown = false;

        try {
            Coordinate coordinate = new Coordinate(1, -3);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        check(exceptionThrown, "Coordinate rejects negative y");
    }

    private void testCoordinateEquals() {
        Coordinate first = new Coordinate(2, 3);
        Coordinate second = new Coordinate(2, 3);

        check(first.equals(second),
                "Coordinate equals returns true for same x and y");
    }

    private void testCoordinateNotEquals() {
        Coordinate first = new Coordinate(2, 3);
        Coordinate second = new Coordinate(3, 2);

        check(!first.equals(second),
                "Coordinate equals returns false for different x and y");
    }
}
