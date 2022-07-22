public class TestCaseTest extends TestCase {

    WasRun wasRun;

    public TestCaseTest(String name) {
        super(name);
        setUp();
    }

    @Override
    public void setUp() {
        wasRun = new WasRun("testMethod");
    }

    public void testRunning() {
        Assert.assertEquals(false, wasRun.wasRun);
        wasRun.run();
        Assert.assertEquals(true, wasRun.wasRun);
    }

    public void testSetUp() {
        Assert.assertEquals(false, wasRun.wasSetup);
        wasRun.run();
        Assert.assertEquals(true, wasRun.wasSetup);
    }
}
