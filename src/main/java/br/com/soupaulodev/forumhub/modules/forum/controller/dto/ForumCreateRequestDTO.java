package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;


/**
 * DTO (Data Transfer Object) representing the request body for forum creation.
 * <p>
 * This class is used to capture the forum's information, including its name, description, and owner ID,
 * when creating a new forum. It uses Jakarta Bean Validation annotations to enforce that the input is validated
 * before processing, ensuring that the forum is created correctly.
 * </p>
 *
 * @param name        the name of the forum
 * @param description the description of the forum
 * @param ownerId     the unique identifier of the forum owner
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record ForumCreateRequestDTO(@NotBlank
                                    @Length(min = 3, max = 50)
                                    String name,

                                    @NotBlank
                                    @Length(min = 3, max = 50)
                                    String description) {
}