package ru.test.simplifiedexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Konstantin Kasyanov
 */
@AllArgsConstructor
@Getter
public class Security {
  private String name;
  private Integer priceOfOne;
}
