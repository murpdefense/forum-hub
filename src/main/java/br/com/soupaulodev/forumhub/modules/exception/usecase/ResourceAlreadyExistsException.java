package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
