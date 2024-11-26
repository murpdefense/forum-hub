package br.com.soupaulodev.forumhub.modules.comment.mapper;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;

public class CommentMapper {

    public static CommentEntity toEntity(CommentRequestDTO commentRequestDTO) {
        return new CommentEntity();
    }

    public static CommentResponseDTO toResponseDTO(CommentEntity commentEntity) {
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO(commentEntity.getId(),
                commentEntity.getMessage(),
                CommentMapper.toResponseDTO(commentEntity.getParentComment()));
    }
}
