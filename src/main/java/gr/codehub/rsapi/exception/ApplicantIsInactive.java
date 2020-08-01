package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to cretae or
 * update an applicant and the applicant is already inactive
 */
public class ApplicantIsInactive extends BusinessException {
    public ApplicantIsInactive(String message) {

        super(message);
    }
}
