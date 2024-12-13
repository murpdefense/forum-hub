package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use case for listing forums with pagination support.
 */
@Service
public class ListForumsUseCase {

    private final ForumRepository forumRepository;

    /**
     * Constructs a new ListForumsPageableUsecase with the specified repository.
     *
     * @param forumRepository the repository for managing forums
     */
    public ListForumsUseCase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Executes the use case to list forums with pagination support.
     *
     * @param page the page number to retrieve
     * @return a list of ForumResponseDTO containing the forum data
     */
    public List<ForumResponseDTO> execute(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ForumEntity> entities = forumRepository.findAll(pageable);

        return entities.getContent().stream()
                .map(ForumMapper::toResponseDTO)
                .toList();
    }
}
