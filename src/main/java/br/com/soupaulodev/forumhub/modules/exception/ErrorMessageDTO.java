package br.com.soupaulodev.forumhub.modules.exception;

/**
 * Data Transfer Object for error messages.
 */
public class ErrorMessageDTO {

    /**
     * The error message.
     */
    private String message;

    /**
     * The field associated with the error.
     */
    private String field;

    public ErrorMessageDTO(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}