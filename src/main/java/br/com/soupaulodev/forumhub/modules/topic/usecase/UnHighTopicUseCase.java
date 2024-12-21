package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.repository.TopicHighsRepository;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case to unhigh a topic
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class UnHighTopicUseCase {

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
    public UnHighTopicUseCase(TopicHighsRepository topicHighsRepository,
                            TopicRepository topicRepository,
                            UserRepository userRepository) {
        this.topicHighsRepository = topicHighsRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    /**
     * Execute the use case
     *
     * @param topicId topic id
     * @param authenticatedUserId authenticated user id
     */
    public void execute(UUID topicId, UUID authenticatedUserId) {
        topicHighsRepository.findByTopic_IdAndUser_Id(topicId, authenticatedUserId)
                .ifPresentOrElse(
                        topicHighsRepository::delete,
                    () -> {
                        throw new IllegalArgumentException("Topic not highed");
                    }
                );
    }
}
