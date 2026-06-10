package comp2450.tests;

public class TestHarness {

    public static void main(String[] args) {
        System.out.println("Running Battleship++ Phase 5 tests...\n");

        TestSuite[] suites = {
                new CoordinateTests(),
                new CellTests(),
                new ShipTests(),
                new BoardTest()
        };

        int totalTests = 0;
        int totalSuccesses = 0;
        int totalFailures = 0;

        for (int i = 0; i < suites.length; i++) {
            System.out.println(suites[i].suiteName());
            System.out.println("----------------");

            TestResults results = suites[i].runTests();

            System.out.println("Suite tests: " + results.getTotalTests());
            System.out.println("Suite successes: " + results.getSuccesses());
            System.out.println("Suite failures: " + results.getFailures());
            System.out.println();

            totalTests = totalTests + results.getTotalTests();
            totalSuccesses = totalSuccesses + results.getSuccesses();
            totalFailures = totalFailures + results.getFailures();
        }

        System.out.println("==============================");
        System.out.println("Overall Results");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Successes: " + totalSuccesses);
        System.out.println("Failures: " + totalFailures);
        System.out.println("==============================");
    }
}