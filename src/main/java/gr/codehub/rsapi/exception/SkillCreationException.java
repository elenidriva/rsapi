package gr.codehub.rsapi.exception;

/**
 * This exception is thrown when the user is trying to create a skill and the skill is already exists
 */
public class SkillCreationException extends BusinessException{
    public SkillCreationException (String message){

        super(message);
    }
}
