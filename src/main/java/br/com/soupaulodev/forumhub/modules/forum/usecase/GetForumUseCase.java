package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case for retrieving the details of a forum.
 */
@Service
public class GetForumUseCase {

    private final ForumRepository forumRepository;
    private final ForumMapper forumMapper;

    public GetForumUseCase(ForumRepository forumRepository,
                           ForumMapper forumMapper) {
        this.forumRepository = forumRepository;
        this.forumMapper = forumMapper;
    }

    /**
     * Executes the use case to retrieve the details of a forum by its unique identifier.
     *
     * @param id the unique identifier of the forum
     * @return the response data transfer object containing the forum details
     * @throws ResourceNotFoundException if the forum with the specified ID is not found
     */
    public ForumResponseDTO execute(UUID id) {

        ForumEntity forumFound = forumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forum not found."));

        return forumMapper.toResponseDTO(forumFound);
    }
}
