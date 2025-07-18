package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        String fruit = transaction.getFruit();
        int currentQuantity = Storage.fruits.getOrDefault(fruit, 0);

        if (currentQuantity < transaction.getQuantity()) {
            throw new IllegalArgumentException("Not enough " + fruit + " in stock!");
        }
        Storage.fruits.put(fruit, currentQuantity - transaction.getQuantity());
    }
}
