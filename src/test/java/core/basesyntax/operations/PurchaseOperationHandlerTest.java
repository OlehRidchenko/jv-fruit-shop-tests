package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void apply_NotEnoughInStock_NotOk() {
        Storage.fruits.put("banana", 10);

        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "banana", 20);
        assertThrows(IllegalArgumentException.class,
                () -> new PurchaseOperationHandler().apply(fruitTransaction));
    }

    @Test
    void apply_NoFruitInStorage_Ok() {
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        assertThrows(IllegalArgumentException.class,
                () -> new PurchaseOperationHandler().apply(fruitTransaction));
    }

    @Test
    void apply_RemnantFromPurchaseIsZero_Ok() {
        Storage.fruits.put("coconut", 25);
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "coconut", 25);
        new PurchaseOperationHandler().apply(fruitTransaction);
        assertEquals(0, Storage.fruits.get("coconut"));
    }

    @Test
    void apply_ValidPurchase_Ok() {
        Storage.fruits.put("orange", 30);
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "orange", 16);
        new PurchaseOperationHandler().apply(fruitTransaction);
        assertEquals(14, Storage.fruits.get("orange"));
    }

    @Test
    void apply_NegativeQuntity_NotOk() {
        Storage.fruits.put("banana", 50);
        fruitTransaction = new FruitTransaction(Operation.SUPPLY,"banana", -60);
        assertThrows(IllegalArgumentException.class,
                () -> new PurchaseOperationHandler().apply(fruitTransaction));
    }

}
