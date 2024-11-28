package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a comment is not found.
 */
public class CommentNotFoundException extends RuntimeException{

    /**
     * Constructs a new CommentNotFoundException with a default message.
     */
    public CommentNotFoundException() {
        super("Comment not found");
    }
}
