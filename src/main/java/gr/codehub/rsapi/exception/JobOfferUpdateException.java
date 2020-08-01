package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to update a job offer and the job offer is inactive
 */
public class JobOfferUpdateException extends BusinessException {
    public JobOfferUpdateException(String message) {

        super(message);
    }
}
