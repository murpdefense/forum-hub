package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Use case for updating a forum.
 */
@Service
public class UpdateForumUseCase {

    private final ForumRepository forumRepository;
    private final JwtUtil jwtUtil;

    /**
     * Constructs a new UpdateForumUsecase with the specified repository.
     *
     * @param forumRepository the repository for managing forums
     */
    public UpdateForumUseCase(ForumRepository forumRepository,
                              JwtUtil jwtUtil) {
        this.forumRepository = forumRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Executes the use case to update a forum.
     *
     * @param id the unique identifier of the forum to be updated
     * @param requestDTO the data transfer object containing the forum update data
     * @param authenticatedUserId the unique identifier of the authenticated user
     * @return the response data transfer object containing the updated forum data
     * @throws ForumNotFoundException if the forum with the specified ID is not found
     * @throws ForumIllegalArgumentException if the provided data is invalid
     * @throws UnauthorizedException if the authenticated user is not the owner of the forum
     */
    public ForumResponseDTO execute(UUID id, ForumUpdateRequestDTO requestDTO, UUID authenticatedUserId) {
        ForumEntity forumFound = forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);

        if (!forumFound.getOwner().getId().equals(authenticatedUserId)) {
            throw new UnauthorizedException("You are not allowed to update this forum.");
        }

        if (requestDTO == null
                || (requestDTO.name() == null
                && requestDTO.description() == null)) {

            throw new ForumIllegalArgumentException("""
                You must provide at least one field to update:
                - name
                - description
                """);
        }

        forumFound.setName(requestDTO.name() != null ? requestDTO.name() : forumFound.getName());
        forumFound.setDescription(requestDTO.description() != null ? requestDTO.description() : forumFound.getDescription());
        forumFound.setUpdatedAt(Instant.now());

        return ForumMapper.toResponseDTO(forumRepository.save(forumFound));
    }
}
