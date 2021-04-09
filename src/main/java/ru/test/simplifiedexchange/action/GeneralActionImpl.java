package ru.test.simplifiedexchange.action;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.test.simplifiedexchange.error.NotFoundException;
import ru.test.simplifiedexchange.service.GeneralService;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author Konstantin Kasyanov
 */
@Component
@AllArgsConstructor
public class GeneralActionImpl implements GeneralAction {
  private static final Logger logger = LogManager.getLogger(GeneralActionImpl.class);

  private final GeneralService generalService;

  @PostConstruct
  public void init() {
    start();
  }

  @Override
  public void start(){
    logger.info("Started app success...");
    try {
      generalService.readData();
    } catch (IOException e) {
      logger.info("Error read data operation. Description: "+ e.getMessage());
    }
    try {
      generalService.matchingData();
    } catch (NotFoundException e) {
      logger.info("Error matching data operation. Description: "+ e.getMessage());
    }
    try {
      generalService.writeReport();
    } catch (IOException e) {
      logger.info("Error write report operation. Description: "+ e.getMessage());
    }
    logger.info("Finished app success...");
  }

}
