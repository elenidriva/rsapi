package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to add a job offer without the required fields
 */
public class JobOfferCreationException extends BusinessException {
    public JobOfferCreationException(String message) {

        super(message);
    }
}
