package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Operation;
import core.basesyntax.operations.BalanceOperationHandler;
import core.basesyntax.operations.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void get_existingOperation_returnsHandler() {
        OperationHandler handler = operationStrategy.get(Operation.BALANCE);
        assertNotNull(handler);
        assertTrue(handler instanceof BalanceOperationHandler);
    }

    @Test
    void get_nonExistingOperation_returnsNull() {
        OperationHandler handler = operationStrategy.get(Operation.PURCHASE);
        assertNull(handler);
    }
}
