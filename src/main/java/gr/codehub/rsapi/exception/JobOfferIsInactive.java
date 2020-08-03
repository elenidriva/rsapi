package gr.codehub.rsapi.exception;


/**
 * This exception is thrown when the user is trying to update a job offer and the job offer is already inactive
 */
public class JobOfferIsInactive extends BusinessException {
    public JobOfferIsInactive(String message) {

        super(message);
    }
}
