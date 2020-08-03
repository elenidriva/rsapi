package gr.codehub.rsapi.exception;


/**
 * This exception is thrown when the user is trying to add a job offer that does not exist
 */
public class JobOfferNotFoundException extends BusinessException {
    public JobOfferNotFoundException(String message) {

        super(message);
    }
}
