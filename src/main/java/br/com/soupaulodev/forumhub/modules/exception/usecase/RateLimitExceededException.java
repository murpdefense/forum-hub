package br.com.soupaulodev.forumhub.modules.exception.usecase;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException() {
        super("Rate limit exceeded. Try again later.");
    }
}
