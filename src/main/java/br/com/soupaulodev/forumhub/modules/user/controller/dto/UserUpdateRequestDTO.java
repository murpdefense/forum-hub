package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDTO (@Size(max = 50)
                                    String name,

                                    @Size(max = 50)
                                    String username,

                                    @Email
                                    String email,

                                    @Size(min = 8, max = 50)
                                    String password) {}
