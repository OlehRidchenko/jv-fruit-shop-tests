package core.basesyntax.model;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private final String code;

    Operation(String firstLetterOfOperation) {
        this.code = firstLetterOfOperation;
    }

    public static Operation fromCode(String code) {
        for (Operation operation : Operation.values()) {
            if (operation.code.equals(code)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Invalid operation code: " + code);
    }
}
