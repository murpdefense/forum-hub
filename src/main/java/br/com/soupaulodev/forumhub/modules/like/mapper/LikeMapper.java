package br.com.soupaulodev.forumhub.modules.like.mapper;

import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeRequestDTO;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;

import java.util.UUID;

/**
 * Mapper class for converting between LikeEntity and LikeResponseDTO.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public class LikeMapper {

    /**
     * Converts a LikeResponseDTO to a LikeEntity.
     *
     * @param dto  The LikeResponseDTO to be converted.
     * @param user The UserEntity that liked the resource.
     */
    public static LikeEntity toEntity(LikeRequestDTO dto, UserEntity user) {
        return new LikeEntity(
                dto.resourceType(),
                UUID.fromString(dto.resourceId()),
                user
        );
    }
}
