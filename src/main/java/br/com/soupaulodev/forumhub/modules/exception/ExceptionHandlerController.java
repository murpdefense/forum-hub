package br.com.soupaulodev.forumhub.modules.exception;

import br.com.soupaulodev.forumhub.modules.exception.usecase.*;
import org.apache.coyote.BadRequestException;
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
     * Handles CommentIllegalArgumentException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(CommentIllegalArgumentException.class)
    public ResponseEntity<?> handleCommentIllegalArgumentException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles CommentNotFoundException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<?> handleCommentNotFoundException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles ForumAlreadyExistsException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a conflict status and the exception message
     */
    @ExceptionHandler(ForumAlreadyExistsException.class)
    public ResponseEntity<?> handleForumAlreadyExistsException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles ForumIllegalArgumentException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(ForumIllegalArgumentException.class)
    public ResponseEntity<?> handleForumIllegalArgumentException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles ForumNotFoundException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(ForumNotFoundException.class)
    public ResponseEntity<?> handleForumNotFoundException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles LikeAlreadyExistsException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a conflict status and the exception message
     */
    @ExceptionHandler(LikeAlreadyExistsException.class)
    public ResponseEntity<?> handleLikeAlreadyExistsException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles LikeNotFoundException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<?> handleLikeNotFoundException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TokenExpiredCustomException.class)
    public ResponseEntity<?> handleTokenExpiredCustomException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Handles TopicIllegalArgumentException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(TopicIllegalArgumentException.class)
    public ResponseEntity<?> handleTopicIllegalArgumentException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles TopicNotFoundException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<?> handleTopicNotFoundException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles UserAlreadyExistsException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a conflict status and the exception message
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles UserIllegalArgumentException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(UserIllegalArgumentException.class)
    public ResponseEntity<?> handleUserIllegalArgumentException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles UserNotFoundException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles BadRequestException.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
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
