package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class ForumIllegalArgumentException extends RuntimeException {

    public ForumIllegalArgumentException(String message) {
        super(message);
    }
}
