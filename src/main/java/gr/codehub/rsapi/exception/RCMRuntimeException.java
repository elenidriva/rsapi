package gr.codehub.rsapi.exception;

import java.util.List;

import static gr.codehub.rsapi.commons.Constants.RCM_SYSTEM_ERROR;
import static gr.codehub.rsapi.commons.Constants.UNKNOWN_CODE;
import static gr.codehub.rsapi.exception.RCMError.toErrors;

public class RCMRuntimeException extends RuntimeException {
    private final List<RCMError> errors;

    public RCMRuntimeException() {
        this(RCM_SYSTEM_ERROR);
    }

    public RCMRuntimeException(String message) {
        super(message);
        errors = toErrors(UNKNOWN_CODE, message);
    }

    public RCMRuntimeException(String code, String message) {
        super(message);
        this.errors = toErrors(UNKNOWN_CODE, message);
    }

    public List<RCMError> getErrors() {
        return this.errors;
    }
}
