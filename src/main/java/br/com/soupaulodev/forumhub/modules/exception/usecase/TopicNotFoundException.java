package br.com.soupaulodev.forumhub.modules.exception.usecase;

/**
 * Exception thrown when a topic is not found.
 */
public class TopicNotFoundException extends RuntimeException {

    /**
     * Constructs a new TopicNotFoundException with a default message.
     */
    public TopicNotFoundException() {
        super("Topic not found");
    }
}