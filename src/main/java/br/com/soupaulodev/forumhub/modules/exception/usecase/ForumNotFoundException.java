package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class ForumNotFoundException extends RuntimeException {

    public ForumNotFoundException() {
        super("Forum not found");
    }
}
