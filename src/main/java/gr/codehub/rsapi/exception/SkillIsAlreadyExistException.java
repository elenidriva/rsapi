package gr.codehub.rsapi.exception;


/**
 * This exception is thrown when the user is trying to add a skill and the skill does not exist
 */
public class SkillIsAlreadyExistException extends BusinessException {
    public SkillIsAlreadyExistException(String message) {

        super(message);
    }
}
