package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a topic has an illegal argument.
 */
public class TopicIllegalArgumentException extends RuntimeException {

    /**
     * Constructs a new TopicIllegalArgumentException with the specified message.
     *
     * @param message the detail message
     */
    public TopicIllegalArgumentException(String message) {
        super(message);
    }
}