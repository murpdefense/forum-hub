package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
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

    /**
     * Constructs a new GetForumDetailsUsecase with the specified repository.
     *
     * @param forumRepository the repository for managing forums
     */
    public GetForumUseCase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Executes the use case to retrieve the details of a forum by its unique identifier.
     *
     * @param id the unique identifier of the forum
     * @return the response data transfer object containing the forum details
     * @throws ForumNotFoundException if the forum with the specified ID is not found
     */
    public ForumResponseDTO execute(UUID id) {

        ForumEntity forumFound = forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);

        return ForumMapper.toResponseDTO(forumFound);
    }
}
