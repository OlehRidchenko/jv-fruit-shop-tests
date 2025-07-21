package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageDaoImplTest {
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        Storage.fruits.put("apple", 50);
        Storage.fruits.put("banana", 30);
    }

    @AfterEach
    void init() {
        Storage.fruits.clear();
    }

    @Test
    void get_CorrectValueFromMap_Ok() {
        Map<String, Integer> actual = storageDao.get();
        assertEquals(50, actual.get("apple"));
        assertEquals(30, actual.get("banana"));
    }

    @Test
    void get_CorrectSizeOfMap_Ok() {
        Map<String, Integer> actual = storageDao.get();
        assertEquals(2, actual.size());
    }

    @Test
    void get_UnmodifiableMap_Ok() {
        Map<String, Integer> result = storageDao.get();
        assertThrows(UnsupportedOperationException.class, () -> result.put("orange", 70));
    }

    @Test
    void get_ReflectChangesInOriginalMap_Ok() {
        Storage.fruits.put("peach", 20);
        Map<String, Integer> result = storageDao.get();
        assertTrue(result.containsKey("peach"));
    }
}
