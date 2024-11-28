package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a forum already exists.
 */
public class ForumAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new ForumAlreadyExistsException with a default message.
     */
    public ForumAlreadyExistsException() {
        super("Forum already exists");
    }
}