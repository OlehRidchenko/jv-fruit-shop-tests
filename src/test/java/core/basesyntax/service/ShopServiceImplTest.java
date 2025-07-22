package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operations.BalanceOperationHandler;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseOperationHandler;
import core.basesyntax.operations.ReturnOperationHandler;
import core.basesyntax.operations.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandler());

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void init() {
        Storage.fruits.clear();
    }

    @Test
    void process_multipleValidTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 30)
        );

        shopService.process(transactions);
        assertEquals(80, Storage.fruits.get("apple"));
    }

    @Test
    void process_transactionWithNegativeQuantity_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "orange", -10)
        );

        assertThrows(IllegalArgumentException.class, () -> shopService.process(transactions));
    }

    @Test
    void process_handlerIsNull_NotOk() {
        Map<Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());

        OperationStrategy strategyWithMissingHandler = new OperationStrategyImpl(handlerMap);
        ShopService service = new ShopServiceImpl(strategyWithMissingHandler);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.PURCHASE, "apple", 10)
        );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.process(transactions));

        assertTrue(exception.getMessage().contains("No handler found"));
    }

}
