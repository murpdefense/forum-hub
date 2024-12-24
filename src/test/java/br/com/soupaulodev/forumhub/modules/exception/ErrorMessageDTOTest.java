package br.com.soupaulodev.forumhub.modules.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public class ErrorMessageDTOTest {

    @Test
    public void testConstructorAndGetters() {
        String message = "Field cannot be empty";
        String field = "username";

        ErrorMessageDTO errorMessage = new ErrorMessageDTO(message, field);

        assertEquals(message, errorMessage.getMessage(), "The error message should match");
        assertEquals(field, errorMessage.getField(), "The field should match");
    }

    @Test
    public void testSetMessage() {
        String initialMessage = "Field cannot be empty";
        String updatedMessage = "Username is required";

        ErrorMessageDTO errorMessage = new ErrorMessageDTO(initialMessage, "username");

        errorMessage.setMessage(updatedMessage);

        assertEquals(updatedMessage, errorMessage.getMessage(), "The error message should be updated");
    }

    @Test
    public void testToString() {
        String message = "Field cannot be empty";
        String field = "username";

        ErrorMessageDTO errorMessage = new ErrorMessageDTO(message, field);

        String expectedToString = "ErrorMessageDTO{message='Field cannot be empty', field='username'}";
        assertEquals(expectedToString, errorMessage.toString(), "The toString method should match the expected format");
    }

    @Test
    public void testFieldImmutable() {
        String field = "username";
        ErrorMessageDTO errorMessage = new ErrorMessageDTO("Field cannot be empty", field);

        assertEquals(field, errorMessage.getField(), "The field should remain unchanged");
    }
}
