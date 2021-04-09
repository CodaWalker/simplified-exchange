package ru.test.simplifiedexchange.error;

import java.util.function.Supplier;

public class NotFoundException extends CommonException {

    public static Supplier<NotFoundException> from(ErrorInfo errorInfo) {
        return () ->  new NotFoundException(errorInfo);
    }

    public static Supplier<NotFoundException> from(String message, int code) {
        return () -> new NotFoundException(message, code);
    }

    public NotFoundException(String message, int code) {
        super(message, code);
    }

    public NotFoundException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
