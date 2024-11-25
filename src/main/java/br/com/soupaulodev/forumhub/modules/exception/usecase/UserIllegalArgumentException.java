package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class UserIllegalArgumentException extends RuntimeException {

    public UserIllegalArgumentException(String message) {
        super(message);
    }
}
