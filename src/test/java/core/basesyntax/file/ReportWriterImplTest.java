package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String testFile = "src/main/resources/testFile.csv";
    private ReportWriter writer;
    private List<String> listOfData;
    private String data;

    @BeforeEach
    void setUp() {
        writer = new ReportWriterImpl();
        listOfData = List.of("fruit,quantity",
                "banana,150", "apple,90", "pineapple,215", "cucumber,50");
        data = String.join(System.lineSeparator(), listOfData);
    }

    @Test
    void write_FileExist_Ok() {
        File file = new File(testFile);
        writer.write(data, testFile);
        assertTrue(file.exists());
    }

    @Test
    void write_WriteDataToFile_Ok() {
        writer = new ReportWriterImpl();
        String directoryPath = "src/main/resources";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writer.write(data, directoryPath));
        assertEquals("Error writing to file: " + directoryPath,
                exception.getMessage());
    }
}
