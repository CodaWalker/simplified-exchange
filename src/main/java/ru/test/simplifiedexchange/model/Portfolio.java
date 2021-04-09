package ru.test.simplifiedexchange.model;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Konstantin Kasyanov
 */
@Getter
public class Portfolio {
  private static final Logger logger = LogManager.getLogger(Portfolio.class);

  private Integer balance;
  private Integer countSecurityA;
  private Integer countSecurityB;
  private Integer countSecurityC;
  private Integer countSecurityD;

  private Portfolio(Integer balance, Integer countSecurityA, Integer countSecurityB, Integer countSecurityC, Integer countSecurityD) {
    this.balance = balance;
    this.countSecurityA = countSecurityA;
    this.countSecurityB = countSecurityB;
    this.countSecurityC = countSecurityC;
    this.countSecurityD = countSecurityD;
  }

  public Portfolio(String line) {
    String[] lineSplitArray = line.split("\t");
    setPortfolioByArray(lineSplitArray);
  }

  public Portfolio(String line, String delimiter) {
    String[] lineSplitArray = line.split(delimiter);
    setPortfolioByArray(lineSplitArray);
  }

  private void setPortfolioByArray(String[] lineSplitArray) {
    balance = Integer.parseInt(lineSplitArray[1]);
    countSecurityA = Integer.parseInt(lineSplitArray[2]);
    countSecurityB = Integer.parseInt(lineSplitArray[3]);
    countSecurityC = Integer.parseInt(lineSplitArray[4]);
    countSecurityD = Integer.parseInt(lineSplitArray[5]);
  }

  public Portfolio merge(Portfolio portfolio) {
    int balance = this.balance + portfolio.balance;
    int countSecurityA = this.countSecurityA + portfolio.countSecurityA;
    int countSecurityB = this.countSecurityB + portfolio.countSecurityB;
    int countSecurityC = this.countSecurityC + portfolio.countSecurityC;
    int countSecurityD = this.countSecurityD + portfolio.countSecurityD;
    return new Portfolio(balance, countSecurityA, countSecurityB, countSecurityC, countSecurityD);
  }

  public void setCountSecurityAndBalance(String nameSecurity, int newCount, int newBalance) {
    setCountSecurity(nameSecurity, newCount);
    balance = newBalance;
  }

  public void setCountSecurity(String nameSecurity, int newCount) {
    switch (nameSecurity) {
      case "A":
        countSecurityA = newCount;
      case "B":
        countSecurityB = newCount;
      case "C":
        countSecurityC = newCount;
      case "D":
        countSecurityD = newCount;
    }
  }

  public Integer getCountSecurityByNameSecurity(String nameSecurity) {
    switch (nameSecurity) {
      case "A":
        return countSecurityA;
      case "B":
        return countSecurityB;
      case "C":
        return countSecurityC;
      case "D":
        return countSecurityD;
    }
    return 0;
  }

  public String toPrintFormat(String delimiter) {
    return balance + delimiter + countSecurityA + delimiter + countSecurityB + delimiter + countSecurityC + delimiter + countSecurityD;
  }
}
