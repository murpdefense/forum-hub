package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use case for listing forums with pagination support.
 */
@Service
public class ListForumsPageableUsecase {

    private final ForumRepository forumRepository;

    /**
     * Constructs a new ListForumsPageableUsecase with the specified repository.
     *
     * @param forumRepository the repository for managing forums
     */
    public ListForumsPageableUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Executes the use case to list forums with pagination support.
     *
     * @param page the page number to retrieve
     * @return a list of ForumResponseDTO containing the forum data
     */
    public List<ForumResponseDTO> execute(int page) {

        List<ForumEntity> entities = forumRepository.findAllBy(Pageable.ofSize(10).withPage(page));

        return entities
                .stream()
                .map(ForumMapper::toResponseDTO)
                .toList();
    }
}
