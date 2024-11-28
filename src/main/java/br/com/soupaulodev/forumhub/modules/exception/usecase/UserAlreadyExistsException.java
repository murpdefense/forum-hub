package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
