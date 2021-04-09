package ru.test.simplifiedexchange.service;

import ru.test.simplifiedexchange.error.NotFoundException;

import java.io.IOException;

/**
 * @author Konstantin Kasyanov
 */
public interface GeneralService {
  void readData() throws IOException;

  void matchingData() throws NotFoundException;

  void writeReport() throws IOException;
}
