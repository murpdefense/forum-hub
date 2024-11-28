package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a forum has an illegal argument.
 */
public class ForumIllegalArgumentException extends RuntimeException {

    /**
     * Constructs a new ForumIllegalArgumentException with the specified message.
     *
     * @param message the detail message
     */
    public ForumIllegalArgumentException(String message) {
        super(message);
    }
}