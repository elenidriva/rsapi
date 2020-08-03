package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to update a skill and the skill is already exists
 */
public class SkillNotFoundException extends BusinessException {
    public SkillNotFoundException(String message) {

        super(message);
    }
}