import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WasRun {

    public boolean wasRun;
    private final String name;

    public WasRun(String name) {
        this.name = name;
    }

    public void testMethod() {
        wasRun = true;
    }

    public void run() {
        try {
            Method method = getClass().getMethod(name);
            method.invoke(this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
