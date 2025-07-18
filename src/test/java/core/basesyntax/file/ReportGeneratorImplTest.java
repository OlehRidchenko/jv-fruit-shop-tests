package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        reportGenerator = new ReportGeneratorImpl();
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
