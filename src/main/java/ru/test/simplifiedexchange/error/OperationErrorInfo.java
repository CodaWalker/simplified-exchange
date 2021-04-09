package ru.test.simplifiedexchange.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Konstantin Kasyanov
 */
@Getter
@AllArgsConstructor
public enum OperationErrorInfo implements ErrorInfo {

    UNKNOWN_OPERATION("Unknown operation");

    private final String message;
    private final int code = 1000;

}
