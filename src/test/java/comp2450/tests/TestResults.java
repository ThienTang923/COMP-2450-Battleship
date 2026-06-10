package comp2450.tests;

public class TestResults {

    private int totalTests;
    private int successes;
    private int failures;

    public TestResults() {
        this.totalTests =0;
        this.successes =0;
        this.failures = 0;
    }

    public void recordSuccess() {
        this.totalTests++;
        this.successes++;
    }

    public void recordFailure() {
        this.totalTests++;
        this.failures++;
    }

    public int getTotalTests() {
        return this.totalTests;
    }

    public int getSuccesses() {
        return this.successes;
    }

    public int getFailures() {
        return this.failures;
    }
}
