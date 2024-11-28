package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use case for searching topics based on a query.
 */
@Service
public class SearchTopicsUsecase {

    private final TopicRepository topicRepository;

    /**
     * Constructs a new SearchTopicsUsecase with the specified repository.
     *
     * @param topicRepository the repository for managing topics
     */
    public SearchTopicsUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to search for topics based on a query.
     *
     * @param query the search query to match against the title or content of topics
     * @param page the page number to retrieve
     * @return a list of TopicResponseDTO containing the search results
     */
    public List<TopicResponseDTO> execute(String query, int page) {

        List<TopicEntity> entities = topicRepository
                .findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(
                        query,
                        query,
                        Pageable.ofSize(10).withPage(page));

        return entities
                .stream().map(TopicMapper::toResponseDTO).toList();
    }
}
