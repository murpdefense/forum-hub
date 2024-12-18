package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
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
 * This class provides the business logic for updating an existing topic.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
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
     * @param id         the unique identifier of the topic to be updated
     * @param requestDTO the data transfer object containing the topic update data
     * @return the response data transfer object containing the updated topic data
     * @throws ResourceNotFoundException if the topic specified by the id does not exist
     * @throws IllegalArgumentException  if neither title nor content is provided for update
     * @throws ForbiddenException        if the authenticated user is not the creator of the topic
     */
    public TopicResponseDTO execute(UUID id, TopicUpdateRequestDTO requestDTO, UUID autheticatedUserId) {

        TopicEntity topicFound = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found."));

        if (!topicFound.getCreator().getId().equals(autheticatedUserId)) {
            throw new ForbiddenException("You are not allowed to update this topic.");
        }

        if (requestDTO == null
                || (requestDTO.title() == null
                && requestDTO.content() == null)) {

            throw new IllegalArgumentException("""
                    You must provide at least one field to update:
                    - title
                    - content
                    """);
        }

        topicFound.setTitle(requestDTO.title() != null ? requestDTO.title() : topicFound.getTitle());
        topicFound.setContent(requestDTO.content() != null ? requestDTO.content() : topicFound.getContent());
        topicFound.setUpdatedAt(Instant.now());

        return TopicMapper.toResponseDTO(topicRepository.save(topicFound));
    }
}
