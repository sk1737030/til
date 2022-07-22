public class WasRun extends TestCase {

    public boolean wasRun;
    public boolean wasSetup;

    @Override
    public void setUp() {
        wasSetup = true;
    }

    public WasRun(String name) {
        super(name);
    }

    public void testMethod() {
        wasRun = true;
    }

}
