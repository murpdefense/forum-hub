package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case for creating a new topic.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class CreateTopicUseCase {

    private final TopicRepository topicRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new CreateTopicUsecase with the specified repositories.
     *
     * @param topicRepository the repository for managing topics
     * @param forumRepository the repository for managing forums
     * @param userRepository  the repository for managing users
     */
    public CreateTopicUseCase(TopicRepository topicRepository,
                              ForumRepository forumRepository,
                              UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to create a new topic.
     *
     * @param requestDTO the data transfer object containing the topic creation data
     * @return the response data transfer object containing the created topic data
     * @throws ResourceNotFoundException if the forum or user specified in the request does not exist
     */
    public TopicResponseDTO execute(TopicCreateRequestDTO requestDTO, UUID authenticatedUserId) {

        ForumEntity forum = forumRepository.findById(UUID.fromString(requestDTO.forumId()))
                .orElseThrow(() -> new ResourceNotFoundException("Forum not found."));
        UserEntity user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        TopicEntity topic = TopicMapper.toEntity(requestDTO, forum, user);
        topic.setCreator(user);
        topic.setForum(forum);

        TopicEntity topicSaved = topicRepository.save(topic);

        forum.incrementTopicsCount();
        forumRepository.save(forum);

        return TopicMapper.toResponseDTO(topicSaved);
    }
}
