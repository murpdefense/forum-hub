package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String username;
    private String email;
}
