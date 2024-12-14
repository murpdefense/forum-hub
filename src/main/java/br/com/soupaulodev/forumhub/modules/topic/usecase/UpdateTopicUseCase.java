package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Use case for updating an existing topic.
 */
@Service
public class UpdateTopicUseCase {

    private final TopicRepository topicRepository;

    /**
     * Constructs a new UpdateTopicUsecase with the specified repository.
     *
     * @param topicRepository the repository for managing topics
     */
    public UpdateTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to update an existing topic.
     *
     * @param id the unique identifier of the topic to be updated
     * @param requestDTO the data transfer object containing the topic update data
     * @return the response data transfer object containing the updated topic data
     * @throws TopicNotFoundException if the topic specified by the id does not exist
     * @throws TopicIllegalArgumentException if neither title nor content is provided for update
     */
    public TopicResponseDTO execute(UUID id, TopicUpdateRequestDTO requestDTO) {

        TopicEntity topicFound = topicRepository.findById(id)
                .orElseThrow(TopicNotFoundException::new);

        if (requestDTO == null
                || (requestDTO.title() == null
                && requestDTO.content() == null)) {

            throw new TopicIllegalArgumentException("""
                You must provide at least one field to update:
                - title
                - content
                """);
        }

        topicFound.setTitle(requestDTO.title() != null ? requestDTO.title() : topicFound.getTitle());
        topicFound.setContent(requestDTO.content() != null ? requestDTO.content() : topicFound.getContent());
        topicFound.setUpdatedAt(Instant.now());

        return TopicMapper.toResponseDTO( topicRepository.save(topicFound));
    }
}
