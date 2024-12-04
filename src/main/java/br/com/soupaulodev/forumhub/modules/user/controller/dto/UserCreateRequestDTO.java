package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating a new user.
 */
public record UserCreateRequestDTO (@NotBlank
                                    @Size(max = 50)
                                    String name,

                                    @NotBlank
                                    @Size(max = 50)
                                    String username,

                                    @NotBlank
                                    @Email
                                    String email,

                                    @NotBlank
                                    @Size(min = 8, max = 50)
                                    String password) {}