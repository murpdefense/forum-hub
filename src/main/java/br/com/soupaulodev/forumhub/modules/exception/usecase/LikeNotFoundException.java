package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class LikeNotFoundException extends RuntimeException {

    public LikeNotFoundException() {
        super("Like not found");
    }
}
