package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use case for retrieving recent topics.
 */
@Service
public class GetRecentTopicsUsecase {

    private final TopicRepository topicRepository;

    /**
     * Constructs a new GetRecentTopicsUsecase with the specified repository.
     *
     * @param topicRepository the repository for managing topics
     */
    public GetRecentTopicsUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to retrieve recent topics.
     *
     * @param page the page number to retrieve
     * @return a list of TopicResponseDTO containing the recent topics
     */
    public List<TopicResponseDTO> execute(int page) {

        List<TopicEntity> entities = topicRepository
                .findAllByOrderByCreatedAtDesc(Pageable.ofSize(10)
                .withPage(page));

        return entities
                .stream().map(TopicMapper::toResponseDTO).toList();
    }
}
