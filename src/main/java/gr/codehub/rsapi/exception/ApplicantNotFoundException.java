package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to add an applicant that does not exist
 */
public class ApplicantNotFoundException extends BusinessException {
    public ApplicantNotFoundException(String message) {

        super(message);
    }
}
