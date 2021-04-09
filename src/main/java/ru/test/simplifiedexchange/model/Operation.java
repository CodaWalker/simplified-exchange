package ru.test.simplifiedexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Konstantin Kasyanov
 */
@AllArgsConstructor
@Getter
public class Operation {
  private TypeOperation typeOperation;
  private Security security;
  private Integer count;
}
