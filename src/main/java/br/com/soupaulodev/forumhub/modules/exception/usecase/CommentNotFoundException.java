package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class CommentNotFoundException extends RuntimeException{

    public CommentNotFoundException() {
        super("Comment not found");
    }
}
