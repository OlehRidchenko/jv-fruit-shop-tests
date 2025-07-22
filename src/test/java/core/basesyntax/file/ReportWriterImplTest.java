package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String testFile = "src/main/resourcesTest/testFile.csv";
    private ReportWriter writer;
    private String data;

    @BeforeEach
    void setUp() {
        writer = new ReportWriterImpl();
        List<String> listOfData = List.of("fruit,quantity",
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
    void write_directoryPathInsteadOfFile_NotOk() {
        String directoryPath = "src/main/resourcesTest";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writer.write(data, directoryPath));
        String expected = "Error writing to file: ";
        assertTrue(exception.getMessage().contains(expected));
    }

    @Test
    void write_InvalidFilePath_NotOk() {
        String invalidDirectoryPath = "?:/invalid/file.csv";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> writer.write(data, invalidDirectoryPath));
        String expected = "Error writing to file: ";
        assertTrue(exception.getMessage().contains(expected));
    }
}
