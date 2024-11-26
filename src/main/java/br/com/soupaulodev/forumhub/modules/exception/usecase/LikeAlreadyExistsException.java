package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class LikeAlreadyExistsException extends RuntimeException {

    public LikeAlreadyExistsException() {
        super("Like already exists");
    }
}
