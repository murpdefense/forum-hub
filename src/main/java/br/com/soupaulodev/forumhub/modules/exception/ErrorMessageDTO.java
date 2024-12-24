package br.com.soupaulodev.forumhub.modules.exception;

/**
 * Data Transfer Object representing an error message.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public class ErrorMessageDTO {

    private String message;
    private final String field;

    /**
     * Constructs an ErrorMessageDTO with the specified message and field.
     *
     * @param message the error message
     * @param field   the field associated with the error
     */
    public ErrorMessageDTO(String message, String field) {
        this.message = message;
        this.field = field;
    }

    /**
     * Retrieves the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the field associated with the error.
     *
     * @return the name of the field
     */
    public String getField() {
        return field;
    }

    @Override
    public String toString() {
        return "ErrorMessageDTO{" +
                "message='" + message + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}
