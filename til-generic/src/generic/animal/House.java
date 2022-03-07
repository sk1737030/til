package generic.animal;

import java.util.ArrayList;
import java.util.List;

public class House<T> {

    List<T> lists = new ArrayList<>();

    public void add(T t) {
        lists.add(t);
    }

    public T get(int i) {
        return lists.get(i);
    }
}
