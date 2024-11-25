package br.com.soupaulodev.forumhub.modules.forum.mapper;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;

public class ForumMapper {

    public static ForumEntity toEntity(ForumRequestDTO forumRequestDTO) {
        return new ForumEntity(forumRequestDTO.getName(),
                forumRequestDTO.getDescription(),
                UserMapper.toEntity(forumRequestDTO.getOwner()));
    }

    public static ForumResponseDTO toResponseDTO(ForumEntity forumEntity) {
        return new ForumResponseDTO(forumEntity.getId(),
                forumEntity.getName(),
                forumEntity.getDescription(),
                UserMapper.toResponseDTO(forumEntity.getOwner()),
                forumEntity.getTopics().stream().map(TopicMapper::toResponseDTO).toList());
    }
}
