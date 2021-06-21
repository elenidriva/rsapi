package gr.codehub.rsapi.exception;

import lombok.NoArgsConstructor;

public interface Coded {

    Coded.ErrorType defaultErrorType = Coded.ErrorType.SERVER;

    String getCode();

    default String getDescription() {
        return this instanceof Exception ? ((Exception) this).getMessage() : this.toString();
    }

    default Coded.ErrorType getErrorType() {
        return defaultErrorType;
    }

    @NoArgsConstructor
    public enum ErrorType {
        CLIENT,
        SERVER,
        HEURISTIC; // authorization required
    }
}
