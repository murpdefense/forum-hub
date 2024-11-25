package br.com.soupaulodev.forumhub.modules.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorMessageDTO {
    private String message;
    private String field;
}