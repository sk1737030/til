import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Linear tables
 * @param <K>
 * @param <V>
 */
public class MyConcurrentHashMap<K, V> {

    public static final int INIT_BUCKET_SIZE = 16;
    private final List<List<Entry<K, V>>> entries;
    private int size = 0;

    public MyConcurrentHashMap() {
        entries = new ArrayList<>();
    }

    public static class Entry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry entry = (Entry) o;
            return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    public int size() {
        return this.size;
    }


    public V get(Object key) {
        return null;
    }

    public V put(K key, V value) {
        int hash = key.hashCode() % INIT_BUCKET_SIZE;

        List<Entry<K, V>> getEntry = entries.get(hash);
        if (getEntry == null) {
            MyCompareAndSwapForMap.compareAndSwap(entries, hash);
        }

        return null;
    }

    public V remove(Object key) {
        return null;
    }


}
