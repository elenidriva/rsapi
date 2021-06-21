package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to add an applicant without the required fields
 */
public class ApplicantException extends RCMRuntimeException {

    /*
    * public ApplicantException(String message) {
        super(UNKNOWN_CODE, message);
    }
    * */

    public ApplicantException(String code, String message) {
        super(code, message);
    }

}
