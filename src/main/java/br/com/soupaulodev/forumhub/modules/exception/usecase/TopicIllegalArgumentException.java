package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class TopicIllegalArgumentException extends RuntimeException {

    public TopicIllegalArgumentException(String message) {
        super(message);
    }
}
