package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private final OperationHandler handler = new SupplyOperationHandler();
    private FruitTransaction fruitTransaction = new FruitTransaction();

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void apply_AddQuntity_Ok() {
        fruitTransaction = new FruitTransaction(Operation.SUPPLY,"apple", 60);
        handler.apply(fruitTransaction);
        assertEquals(60, Storage.fruits.get("apple"));
    }

    @Test
    void apply_NegativeQuntity_NotOk() {
        fruitTransaction = new FruitTransaction(Operation.SUPPLY,"banana", -60);
        assertThrows(IllegalArgumentException.class, () -> handler.apply(fruitTransaction));
    }

}
