package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
