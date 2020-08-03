package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to add an applicant without the required fields
 */
public class ApplicantCreationException extends BusinessException {
    public ApplicantCreationException(String message) {

        super(message);
    }
}
