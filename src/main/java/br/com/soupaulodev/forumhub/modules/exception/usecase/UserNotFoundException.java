package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found");
    }
}
