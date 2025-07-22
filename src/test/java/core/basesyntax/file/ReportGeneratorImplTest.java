package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void init() {
        Storage.fruits.clear();
    }

    @Test
    void getReport_EmptyStorageReturnsHeaderOnly_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_OneEntryToStorage_Ok() {
        Storage.fruits.put("banana", 40);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,40" + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
