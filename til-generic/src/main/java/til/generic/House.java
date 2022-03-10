package til.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class House<T> implements Iterable{

    List<T> lists = new ArrayList<>();

    public void add(T t) {
        lists.add(t);
    }

    public T get(int i) {
        return lists.get(i);
    }

    @Override
    public Iterator iterator() {
        return lists.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator spliterator() {
        return Iterable.super.spliterator();
    }

    public List<T> getAll() {
        return null;
    }
}
