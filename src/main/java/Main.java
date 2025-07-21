import core.basesyntax.file.DataConverter;
import core.basesyntax.file.DataConverterImpl;
import core.basesyntax.file.ReadFromFile;
import core.basesyntax.file.ReadFromFileImpl;
import core.basesyntax.file.ReportGenerator;
import core.basesyntax.file.ReportGeneratorImpl;
import core.basesyntax.file.ReportWriter;
import core.basesyntax.file.ReportWriterImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operations.BalanceOperationHandler;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseOperationHandler;
import core.basesyntax.operations.ReturnOperationHandler;
import core.basesyntax.operations.SupplyOperationHandler;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "src/main/resources/inputValue.csv";
    private static final String OUTPUT_FILE = "src/main/resources/finalReport.csv";

    public static void main(String[] args) {

        ReadFromFile fileReader = new ReadFromFileImpl();
        List<String> inputReport = fileReader.read(INPUT_FILE);

        DataConverter dataConverter = new DataConverterImpl();
        final List<FruitTransaction> transactions =
                dataConverter.convertToTransactions(inputReport);

        ShopService shopService = getShopService();
        shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport();

        ReportWriter fileWriter = new ReportWriterImpl();
        fileWriter.write(resultingReport, OUTPUT_FILE);
    }

    private static ShopService getShopService() {
        Map<Operation, OperationHandler> operationHandlers = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        return new ShopServiceImpl(operationStrategy);
    }
}
