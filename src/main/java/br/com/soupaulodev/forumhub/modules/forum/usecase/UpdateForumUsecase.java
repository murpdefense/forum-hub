package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
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
public class UpdateForumUsecase {

    private final ForumRepository forumRepository;

    /**
     * Constructs a new UpdateForumUsecase with the specified repository.
     *
     * @param forumRepository the repository for managing forums
     */
    public UpdateForumUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Executes the use case to update a forum.
     *
     * @param id the unique identifier of the forum to be updated
     * @param requestDTO the data transfer object containing the forum update data
     * @return the response data transfer object containing the updated forum data
     * @throws ForumNotFoundException if the forum with the specified ID is not found
     * @throws ForumIllegalArgumentException if the provided data is invalid
     */
    public ForumResponseDTO execute(UUID id, ForumUpdateRequestDTO requestDTO) {
        ForumEntity forumFound = forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);

        if(requestDTO.getName().equals(forumFound.getName())
                && requestDTO.getDescription().equals(forumFound.getDescription())) {
            throw new ForumIllegalArgumentException("Data is the same");
        }

        if((requestDTO.getName().isEmpty()) || requestDTO.getDescription().isEmpty()) {
            throw new ForumIllegalArgumentException("Name and description are empty");
        }

        forumFound.setName(requestDTO.getName());
        forumFound.setDescription(requestDTO.getDescription());
        forumFound.setUpdatedAt(Instant.now());

        ForumEntity forumUpdated = forumRepository.save(forumFound);
        return ForumMapper.toResponseDTO(forumUpdated);
    }
}
