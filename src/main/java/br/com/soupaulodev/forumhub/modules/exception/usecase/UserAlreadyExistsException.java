package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a user already exists.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExistsException with a default message.
     */
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}