package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a forum is not found.
 */
public class ForumNotFoundException extends RuntimeException {

    /**
     * Constructs a new ForumNotFoundException with a default message.
     */
    public ForumNotFoundException() {
        super("Forum not found");
    }
}