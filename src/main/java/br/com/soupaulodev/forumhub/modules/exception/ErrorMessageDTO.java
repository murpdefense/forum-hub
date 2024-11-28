package br.com.soupaulodev.forumhub.modules.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Data Transfer Object for error messages.
 */
@Data
@AllArgsConstructor
public class ErrorMessageDTO {

    /**
     * The error message.
     */
    private String message;

    /**
     * The field associated with the error.
     */
    private String field;
}