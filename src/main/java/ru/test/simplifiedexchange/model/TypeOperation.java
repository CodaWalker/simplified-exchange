package ru.test.simplifiedexchange.model;

import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * @author Konstantin Kasyanov
 */
@AllArgsConstructor
public enum TypeOperation {
  SALE("s"), BAY("b");

  private String primitiveName;

  public static Optional<TypeOperation> getTypeOperationByPrimitiveName(String primitiveName) {
    Optional<TypeOperation> typeOperation = Optional.empty();
    if (primitiveName.equals("s")) {
      typeOperation = Optional.of(TypeOperation.SALE);
    } else if (primitiveName.equals("b")) {
      typeOperation = Optional.of(TypeOperation.BAY);
    }
    return typeOperation;
  }
}
