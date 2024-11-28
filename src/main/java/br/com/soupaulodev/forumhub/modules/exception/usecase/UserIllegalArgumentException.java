package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a user has an illegal argument.
 */
public class UserIllegalArgumentException extends RuntimeException {

    /**
     * Constructs a new UserIllegalArgumentException with the specified message.
     *
     * @param message the detail message
     */
    public UserIllegalArgumentException(String message) {
        super(message);
    }
}