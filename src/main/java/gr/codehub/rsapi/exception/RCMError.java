package gr.codehub.rsapi.exception;

import java.util.Collections;
import java.util.List;

public final class RCMError implements Coded {

    private final String code;
    private final String description;
    private final ErrorType errorType;

    public RCMError() {
        this.code = null;
        this.description = null;
        this.errorType = defaultErrorType;
    }

    public RCMError(String code, String description) {
        this.code = code;
        this.description = description;
        this.errorType = defaultErrorType;
    }

    public RCMError(String code, String description, ErrorType errorType) {
        this.code = code;
        this.description = description;
        this.errorType = errorType;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public ErrorType getErrorType() {
        return this.errorType;
    }

    public static RCMError error(String code, String description) {
        return new RCMError(code, description);
    }

    public static List<RCMError> toErrors(String code, String description) {
        return Collections.singletonList(error(code, description));
    }

    @Override
    public String toString() {
        return "RCMError(" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", errorType=" + errorType +
                ')';
    }
}
