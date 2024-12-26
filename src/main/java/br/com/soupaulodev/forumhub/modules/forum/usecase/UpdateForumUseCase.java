package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Use case for updating a forum.
 */
@Service
public class UpdateForumUseCase {

    private final ForumRepository forumRepository;
    private final ForumMapper forumMapper;

    public UpdateForumUseCase(ForumRepository forumRepository,
                              ForumMapper forumMapper) {
        this.forumRepository = forumRepository;
        this.forumMapper = forumMapper;
    }

    /**
     * Executes the use case to update a forum.
     *
     * @param id                  the unique identifier of the forum to be updated
     * @param requestDTO          the data transfer object containing the forum update data
     * @param authenticatedUserId the unique identifier of the authenticated user
     * @return the response data transfer object containing the updated forum data
     * @throws ResourceNotFoundException if the forum with the specified ID is not found
     * @throws IllegalArgumentException  if the provided data is invalid
     * @throws ForbiddenException        if the authenticated user is not the owner of the forum
     */
    public ForumResponseDTO execute(UUID id, ForumUpdateRequestDTO requestDTO, UUID authenticatedUserId) {
        ForumEntity forumFound = forumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forum not found."));

        if (!forumFound.getOwner().getId().equals(authenticatedUserId)) {
            throw new ForbiddenException("You are not allowed to update this forum.");
        }

        if (requestDTO == null
                || (requestDTO.name() == null
                && requestDTO.description() == null)) {

            throw new IllegalArgumentException("""
                    You must provide at least one field to update:
                    - name
                    - description
                    """);
        }

        forumFound.setName(requestDTO.name() != null ? requestDTO.name() : forumFound.getName());
        forumFound.setDescription(requestDTO.description() != null ? requestDTO.description() : forumFound.getDescription());
        forumFound.setUpdatedAt(Instant.now());

        return forumMapper.toResponseDTO(forumRepository.save(forumFound));
    }
}
