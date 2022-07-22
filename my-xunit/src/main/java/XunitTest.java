public class XunitTest {

    public static void main(String[] args) {
        WasRun wasRun = new WasRun();
        System.out.println(wasRun.wasRun); // false
        wasRun.testMethod();
        System.out.println(wasRun.wasRun); // true

    }

}
