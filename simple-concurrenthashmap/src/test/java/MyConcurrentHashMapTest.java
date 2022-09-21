import org.junit.jupiter.api.Test;

class MyConcurrentHashMapTest {

    @Test
    void givenConcurrentHashMap_WhenPutWithKeyValue_Then() {
        MyConcurrentHashMap<String, Integer> map = new MyConcurrentHashMap<>();
        map.put("test", 1);
    }

}