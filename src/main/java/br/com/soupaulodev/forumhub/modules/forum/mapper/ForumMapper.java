package br.com.soupaulodev.forumhub.modules.forum.mapper;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForumMapper {

    public static ForumEntity toEntity(ForumCreateRequestDTO dto, UserEntity owner) {
        return new ForumEntity(dto.getName(),
                dto.getDescription(),
                owner);
    }

    public static ForumEntity toEntity(ForumUpdateRequestDTO dto, UserEntity owner) {
        return new ForumEntity(dto.getName(),
                dto.getDescription(),
                owner);
    }

    public static ForumResponseDTO toResponseDTO(ForumEntity entity) {
        UserResponseDTO owner = UserMapper.toResponseDTO(entity.getOwner());

        Set<UserResponseDTO> participants = Stream.of(entity.getParticipants())
                .map(participant -> UserMapper.toResponseDTO((UserEntity) participant))
                .collect(Collectors.toSet());

        int topicCount = entity.getTopics().size();

        return new ForumResponseDTO(entity.getId(),
                entity.getName(),
                entity.getDescription(),
                owner,
                participants,
                topicCount,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
