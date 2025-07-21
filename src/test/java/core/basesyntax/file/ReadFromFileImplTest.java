package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadFromFileImplTest {
    private static final String dataEmpty = "src/main/resources/emptyFile.csv";
    private static final String dataNonExist = "src/main/resources/nonExistFile.csv";
    private ReadFromFile fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new ReadFromFileImpl();
    }

    @Test
    void read_ReadingDataFromValidFile_Ok() throws IOException {
        Path path = Files.createTempFile("test", ".csv");
        Files.write(path, List.of("type,fruit,quantity", "banana,152", "apple,90"));
        List<String> result = fileReader.read(path.toString());

        assertEquals(List.of("banana,152", "apple,90"), result);

        Files.deleteIfExists(path);
    }

    @Test
    void read_FileDoesNotExist_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(dataNonExist));
    }

    @Test
    void read_FileIsEmpty_Ok() {
        List<String> data = fileReader.read(dataEmpty);
        assertTrue(data.isEmpty());
    }

    @Test
    void read_FileIsNotNull_Ok() {
        List<String> data = fileReader.read(dataEmpty);
        assertNotNull(data);
    }
}
