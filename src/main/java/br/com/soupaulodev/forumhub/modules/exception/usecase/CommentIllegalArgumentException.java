package br.com.soupaulodev.forumhub.modules.exception.usecase;


/**
 * Exception thrown when a comment has an illegal argument.
 */
public class CommentIllegalArgumentException extends RuntimeException {

    /**
     * Constructs a new CommentIllegalArgumentException with a default message.
     */
    public CommentIllegalArgumentException() {
        super("The message is the same as the previous one");
    }

    /**
     * The field associated with the error.
     */
    public CommentIllegalArgumentException(String message) {
        super(message);
    }
}
