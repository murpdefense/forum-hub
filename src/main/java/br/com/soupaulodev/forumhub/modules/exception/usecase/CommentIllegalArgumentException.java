package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class CommentIllegalArgumentException extends RuntimeException {
    public CommentIllegalArgumentException() {
        super("The message is the same as the previous one");
    }

    public CommentIllegalArgumentException(String message) {
        super(message);
    }
}
