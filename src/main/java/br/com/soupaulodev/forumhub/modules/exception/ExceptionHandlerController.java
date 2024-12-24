package br.com.soupaulodev.forumhub.modules.exception;

import br.com.soupaulodev.forumhub.modules.exception.usecase.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for the application.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private final MessageSource messageSource;

    /**
     * Constructs a new ExceptionHandlerController with the specified message source.
     *
     * @param messageSource the message source for retrieving localized messages
     */
    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handles ForbiddenException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a forbidden status and the exception message
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    /**
     * Handles BadRequestException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadRequestException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Handles IllegalArgumentException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Handles RateLimitExceededException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a too many requests status and the exception message
     */
    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<?> handleRateLimitExceededException(RateLimitExceededException e) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(e.getMessage());
    }

    /**
     * Handles ResourceAlreadyExistsException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a conflict status and the exception message
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles ResourceNotFoundException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a not found status and the exception message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles UnauthorizedException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with an unauthorized status and the exception message
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Handles generic exceptions.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with an internal server error status and the exception message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<ErrorMessageDTO> dto = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            dto.add(error);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic exceptions.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with an internal server error status and the exception message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
