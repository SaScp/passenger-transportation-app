import org.junit.jupiter.api.Test;
import org.service.output_port.JdbcLruIdCache;
import static org.junit.jupiter.api.Assertions.*;
public class LruCacheTest {


    @Test
    public void cacheOverflowTest() {
        JdbcLruIdCache<Integer, Integer> lruIdCache = new JdbcLruIdCache<>(3);

        lruIdCache.put(1, 2);
        lruIdCache.put(2, 2);
        lruIdCache.put(3, 2);
        lruIdCache.get(3);
        lruIdCache.put(4, 1);


        assertTrue(lruIdCache.containsKey(2));

    }

    @Test
    public void cacheContainsNullValueIsTrueTest() {
        JdbcLruIdCache<Integer, Integer> lruIdCache  = new JdbcLruIdCache<>(3);

        lruIdCache.put(null, 2);

        assertTrue(lruIdCache.containsKey(null));
    }

    @Test
    public void cacheGetNotFoundElementIsNullTest() {
        JdbcLruIdCache<Integer, Integer> lruIdCache  = new JdbcLruIdCache<>(3);

        assertNull(lruIdCache.get(1));
    }

    @Test
    public void cacheGetElementIsNotNullTest() {
        JdbcLruIdCache<Integer, Integer> lruIdCache  = new JdbcLruIdCache<>(3);

        lruIdCache.put(1, 1);

        assertNotNull(lruIdCache.get(1));
    }

}
