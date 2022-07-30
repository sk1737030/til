import org.junit.jupiter.api.Test;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class MapPerformanceTest {

    private static final Map<Integer, Integer> testHashTable = new Hashtable<>();
    private static final Map<Integer, Integer> testConcurrentHashMap = new ConcurrentHashMap<>();

    @Test
    void test() throws Exception {
        long hashTableAvgTime = measure_GetPut(testHashTable);
        long concurrentHashMapAvgTime = measure_GetPut(testConcurrentHashMap);

        System.out.println("hashTableAvgTime = " + hashTableAvgTime);
        System.out.println("concurrentHashMapAvgTime = " + concurrentHashMapAvgTime);

    }

    private static long measure_GetPut(Map<Integer, Integer> map) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        long startTime = System.nanoTime();

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 1_000_000; j++) {
                    int value = ThreadLocalRandom
                        .current()
                        .nextInt(100_000);
                    map.put(value, value);
                    map.get(value);
                }
            });
        }
        executor.shutdown();

        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000;
    }

}
