package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use case for listing forums by name with pagination support.
 */
@Service
public class ListForumsByNamePageableUsecase {

    private final ForumRepository forumRepository;

    /**
     * Constructs a new ListForumsByNamePageableUsecase with the specified repository.
     *
     * @param forumRepository the repository for managing forums
     */
    public ListForumsByNamePageableUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Executes the use case to list forums by name with pagination support.
     *
     * @param name the name to search for in forum names
     * @param page the page number to retrieve
     * @return a list of ForumResponseDTO containing the forum data
     */
    public List<ForumResponseDTO> execute(String name, int page) {

        List<ForumEntity> entities = forumRepository.findAllByNameContainingOrderByCreatedAtDesc(name, Pageable.ofSize(10).withPage(page));

        return entities
                .stream()
                .map(ForumMapper::toResponseDTO)
                .toList();
    }
}
