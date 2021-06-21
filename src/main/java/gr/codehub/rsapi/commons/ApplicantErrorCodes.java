package gr.codehub.rsapi.commons;

import lombok.Getter;

@Getter
public enum ApplicantErrorCodes {

    APPLICANT_CREATION_EXCEPTION("0001", "Error while adding applicant because of insufficient data."),
    APPLICANT_NOT_FOUND_EXCEPTION("0002", "Applicant with id %s was not found."),
    APPLICANT_UPDATE_EXCEPTION("0003", "Failed to update Applicant with id %s, because the Applicant is inactive"),
    APPLICANT_CREATION_INVALID_SKILL_EXCEPTION("0004", "Please insert a skill that exists in the DB. Your applicant profile was not created.");

    private static final String APPLICANT_ERRORS_PREFIX = "APPL";
    private final String code;
    private final String message;

    ApplicantErrorCodes(String code, String message) {
        this.code = APPLICANT_ERRORS_PREFIX + code;
        this.message = message;

    }
}
