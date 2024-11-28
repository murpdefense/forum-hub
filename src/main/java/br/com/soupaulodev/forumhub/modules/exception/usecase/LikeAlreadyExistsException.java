package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a like already exists.
 */
public class LikeAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new LikeAlreadyExistsException with a default message.
     */
    public LikeAlreadyExistsException() {
        super("Like already exists");
    }
}