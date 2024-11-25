package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class ForumAlreadyExistsException extends RuntimeException {

    public ForumAlreadyExistsException() {
        super("Forum already exists");
    }

    public ForumAlreadyExistsException(String message) {
        super(message);
    }
}
