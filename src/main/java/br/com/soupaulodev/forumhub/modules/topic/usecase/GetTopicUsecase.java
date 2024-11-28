package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case for retrieving a specific topic by its unique identifier.
 */
@Service
public class GetTopicUsecase {

    private final TopicRepository topicRepository;

    /**
     * Constructs a new GetTopicUsecase with the specified repository.
     *
     * @param topicRepository the repository for managing topics
     */
    public GetTopicUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to retrieve a topic by its unique identifier.
     *
     * @param id the unique identifier of the topic to be retrieved
     * @return the response data transfer object containing the topic data
     * @throws TopicNotFoundException if the topic specified by the id does not exist
     */
    public TopicResponseDTO execute(UUID id) {

        TopicEntity topicFound = topicRepository.findById(id)
                .orElseThrow(TopicNotFoundException::new);

        return TopicMapper.toResponseDTO(topicFound);
    }
}
