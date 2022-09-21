import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Linear tables
 *
 * @param <K>
 * @param <V>
 */
public class MyConcurrentHashMap<K, V> {
    private final Unsafe U;
    private final List<List<Entry<K, V>>> entries;
    public static final int INIT_BUCKET_SIZE = 16;
    private int size = 0;

    public MyConcurrentHashMap() {
        entries = new ArrayList<>();
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            U = (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
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

        for (Entry<K, V> kvEntry : getEntry) {
            if (getEntry == null) {
                if (compareAndSwap(kvEntry, hash, key, value))
                    break;
            }

        }


        return null;
    }

    private boolean compareAndSwap(Entry<K, V> kvEntry, int hash, K key, V value) {
        return U.compareAndSwapObject(kvEntry, 0, null, new MyConcurrentHashMap.Entry<>(hash, key, value));
    }

    public V remove(Object key) {
        return null;
    }


    public static class Entry<K, V> implements Map.Entry<K, V> {

        final int hash;
        private final K key;
        private V value;

        public Entry(int hash, K key, V value) {
            this.hash = hash;
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return hash == entry.hash && Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hash, key, value);
        }
    }
}
