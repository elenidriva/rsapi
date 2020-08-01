package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to update an applicant and the applicant is inactive
 */
public class ApplicantUpdateException extends BusinessException {
    public ApplicantUpdateException(String message) {

        super(message);
    }
}
