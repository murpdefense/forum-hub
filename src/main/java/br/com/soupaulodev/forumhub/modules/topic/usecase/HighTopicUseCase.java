package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicHighsEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicHighsRepository;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case to high a Topic
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class HighTopicUseCase {

    private final TopicHighsRepository topicHighsRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    /**
     * Constructor
     *
     * @param topicHighsRepository topic highs repository
     * @param topicRepository topic repository
     * @param userRepository user repository
     */
    public HighTopicUseCase(TopicHighsRepository topicHighsRepository,
                            TopicRepository topicRepository,
                            UserRepository userRepository) {
        this.topicHighsRepository = topicHighsRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    /**
     * Use case to high a Topic
     *
     * @param topicId topic id
     * @param authenticatedUserId authenticated user id
     */
    public void execute(UUID topicId, UUID authenticatedUserId) {
        topicHighsRepository.findByTopic_IdAndUser_Id(topicId, authenticatedUserId)
                .ifPresent(userHighsEntity -> {
                    throw new IllegalArgumentException("Topic already highed");
                });

        TopicEntity topicFound = topicRepository.findById(topicId).orElseThrow(
                () -> new IllegalArgumentException("Topic not found")
        );

        UserEntity authenticatedUserEntity = userRepository.findById(authenticatedUserId).orElseThrow(
                () -> new IllegalArgumentException("Topic not found")
        );

        topicHighsRepository.save(new TopicHighsEntity(topicFound, authenticatedUserEntity));
    }
}
