package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter converter;
    private List<String> resultReaderList;
    private List<FruitTransaction> fruitTransactionsList;

    @BeforeEach
    public void setUp() {
        converter = new DataConverterImpl();
        resultReaderList = List.of(
                "b,banana,20"
        );
        fruitTransactionsList = converter.convertToTransactions(resultReaderList);
    }

    @Test
    void convertToTransactions_ConvertorParsingValue_Ok() {
        List<FruitTransaction> result = converter.convertToTransactions(resultReaderList);
        assertEquals(fruitTransactionsList, result);
    }

    @Test
    void convertToTransactions_IllegalArgumentOperation_NotOk() {
        assertThrows(UnsupportedOperationException.class,
                () -> resultReaderList.add("x,banana, 10"));
    }

    @Test
    void convertToTransactions_IllegalArgumentQuantity_NotOk() {
        List<String> invalidInput = List.of("b,banana, abc");
        assertThrows(NumberFormatException.class,
                () -> converter.convertToTransactions(invalidInput));
    }

    @Test
    void convertToTransactions_IllegalStringFormat_NotOk() {
        List<String> invalidInput = List.of("b,apple");
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> converter.convertToTransactions(invalidInput));
    }

    @Test
    void convertToTransactions_EmptyList_Ok() {
        List<String> emptyList = new ArrayList<>();
        List<FruitTransaction> result = converter.convertToTransactions(emptyList);
        assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_NotNullList_Ok() {
        List<String> emptyList = new ArrayList<>();
        List<FruitTransaction> result = converter.convertToTransactions(emptyList);
        assertNotNull(result);
    }

}
