package ru.test.simplifiedexchange.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.test.simplifiedexchange.error.NotFoundException;
import ru.test.simplifiedexchange.model.Operation;
import ru.test.simplifiedexchange.model.Portfolio;
import ru.test.simplifiedexchange.model.Security;
import ru.test.simplifiedexchange.model.TypeOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static ru.test.simplifiedexchange.error.OperationErrorInfo.UNKNOWN_OPERATION;

/**
 * @author Konstantin Kasyanov
 */
@Service
public class GeneralServiceImpl implements GeneralService {
  private static final Logger logger = LogManager.getLogger(GeneralServiceImpl.class);
  private TreeMap<String, Portfolio> portfolios;
  private List<String> operations;

  @Value("${input.file.clients}")
  private String inputFileClients;

  @Value("${input.file.orders}")
  private String inputFileOrders;

  @Value("${output.file.clients}")
  private String outputFile;

  @Value("${file.delimiter}")
  private String delimiter;

  @Override
  public void readData() throws IOException {
    logger.info("Read data started...");
    portfolios = Files.lines(Paths.get(inputFileClients))
      .filter(x -> x.split(delimiter).length == 6)
      .collect(toMap((x) -> x.split(delimiter)[0], Portfolio::new, Portfolio::merge, TreeMap::new));
    operations = Files.lines(Paths.get(inputFileOrders))
      .filter(x -> x.split(delimiter).length == 5)
      .collect(toList());
    logger.info("Read data finished... Received " + portfolios.size() + " clients!");
  }

  @Override
  public void matchingData() throws NotFoundException {
    logger.info("Matching data started...");
    for (String x : operations) {
      String[] arrayByLine = x.split(delimiter);
      String clientName = arrayByLine[0];
      String stringOperation = arrayByLine[1];
      String securityName = arrayByLine[2];
      int priceOfOneSecurity = Integer.parseInt(arrayByLine[3]);
      int countSecurity = Integer.parseInt(arrayByLine[4]);
      TypeOperation typeOperation = TypeOperation.getTypeOperationByPrimitiveName(stringOperation).orElseThrow(NotFoundException.from(UNKNOWN_OPERATION));
      Operation operation = new Operation(typeOperation, new Security(securityName, priceOfOneSecurity), countSecurity);
      Portfolio portfolio = portfolios.get(clientName);
      portfolios.put(clientName, updatePortfolio(portfolio, operation));
    }
    logger.info("Matching data finished... Processed " + operations.size() + " operations!");
  }

  private Portfolio updatePortfolio(Portfolio portfolio, Operation operation) {
    int count = operation.getCount();
    Security security = operation.getSecurity();
    TypeOperation typeOperation = operation.getTypeOperation();
    int priceOfOneSecurity = security.getPriceOfOne();
    String nameSecurity = operation.getSecurity().getName();
    Integer countSecureForOperation = portfolio.getCountSecurityByNameSecurity(nameSecurity);

    if (typeOperation == TypeOperation.SALE) {
      if (countSecureForOperation >= count) {
      int newCount = countSecureForOperation - count;
      portfolio.setCountSecurity(nameSecurity, newCount);
      } else {
      logger.warn("Not enough security!");
      }
    } else if (typeOperation == TypeOperation.BAY) {
      int needMoney = count * priceOfOneSecurity;
      if (portfolio.getBalance() >= needMoney) {
      int newCount = countSecureForOperation + count;
      int restOfMoneyBalance = portfolio.getBalance() - needMoney;
      portfolio.setCountSecurityAndBalance(nameSecurity, newCount, restOfMoneyBalance);
      } else {
        logger.warn("Not enough money!");
      }
    } else {
      logger.warn("Not found operation!");
    }
    return portfolio;
  }

  @Override
  public void writeReport() throws IOException {
    logger.info("Write report started...");
    Files.write(Paths.get(outputFile), () -> portfolios.entrySet().stream()
      .<CharSequence>map(e -> e.getKey() + delimiter + e.getValue().toPrintFormat(delimiter))
      .iterator());
    logger.info("Write report finished...!");
  }
}
