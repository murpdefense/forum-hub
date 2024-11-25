package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class TopicNotFoundException extends RuntimeException {

    public TopicNotFoundException() {
        super("Topic not found");
    }
}
