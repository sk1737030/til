import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MyCompareAndSwapForMap {


    public static <V, K> void compareAndSwap(List<List<MyConcurrentHashMap.Entry<K, V>>> entries, int hash) {
        AtomicLong version = new AtomicLong();

        boolean successful = false;

        while (!successful) {
            entries.add(hash, new ArrayList<>());
            // successful = version.compareAndSet();
        }

    }
}
