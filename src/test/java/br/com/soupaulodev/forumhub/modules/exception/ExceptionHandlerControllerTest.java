package br.com.soupaulodev.forumhub.modules.exception;

import br.com.soupaulodev.forumhub.modules.exception.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class ExceptionHandlerControllerTest {

    private ExceptionHandlerController exceptionHandlerController;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exceptionHandlerController = new ExceptionHandlerController(messageSource);
    }

    @Test
    void shouldHandleForbiddenException() {
        ForbiddenException exception = new ForbiddenException("Access denied");

        ResponseEntity<?> response = exceptionHandlerController.handleForbiddenException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied", response.getBody());
    }

    @Test
    void shouldHandleBadRequestException() {
        BadCredentialsException exception = new BadCredentialsException("Invalid credentials");

        ResponseEntity<?> response = exceptionHandlerController.handleBadRequestException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ResponseEntity<?> response = exceptionHandlerController.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody());
    }

    @Test
    void shouldHandleRateLimitExceededException() {
        RateLimitExceededException exception = new RateLimitExceededException("Too many requests");

        ResponseEntity<?> response = exceptionHandlerController.handleRateLimitExceededException(exception);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());
        assertEquals("Too many requests", response.getBody());
    }

    @Test
    void shouldHandleResourceAlreadyExistsException() {
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException("Resource already exists");

        ResponseEntity<?> response = exceptionHandlerController.handleResourceAlreadyExistsException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Resource already exists", response.getBody());
    }

    @Test
    void shouldHandleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        ResponseEntity<?> response = exceptionHandlerController.handleResourceNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody());
    }

    @Test
    void shouldHandleUnauthorizedException() {
        UnauthorizedException exception = new UnauthorizedException("Unauthorized access");

        ResponseEntity<?> response = exceptionHandlerController.handleUnauthorizedException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized access", response.getBody());
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);

        FieldError fieldError = new FieldError("yourDto", "fieldName", "Invalid field value");
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(fieldError);

        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        when(messageSource.getMessage(eq(fieldError), eq(Locale.getDefault()))).thenReturn("Invalid field value");

        ResponseEntity<?> response = exceptionHandlerController.handlerMethodArgumentNotValidException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        List<ErrorMessageDTO> responseBody = (List<ErrorMessageDTO>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
        assertEquals("Invalid field value", responseBody.getFirst().getMessage());
        assertEquals("fieldName", responseBody.getFirst().getField());
    }

    @Test
    void shouldHandleGenericException() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<?> response = exceptionHandlerController.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }
}
