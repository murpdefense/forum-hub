package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a like is not found.
 */
public class LikeNotFoundException extends RuntimeException {

    /**
     * Constructs a new LikeNotFoundException with a default message.
     */
    public LikeNotFoundException() {
        super("Like not found");
    }
}