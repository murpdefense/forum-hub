package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case for deleting a topic.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class DeleteTopicUseCase {

    private final TopicRepository topicRepository;

    /**
     * Constructs a new DeleteTopicUsecase with the specified repository.
     *
     * @param topicRepository the repository for managing topics
     */
    public DeleteTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to delete a topic.
     *
     * @param id the unique identifier of the topic to be deleted
     * @throws TopicNotFoundException if the topic specified by the id does not exist
     */
    public void execute(UUID id) {

        TopicEntity topicDB = topicRepository.findById(id)
                .orElseThrow(TopicNotFoundException::new);

        topicRepository.delete(topicDB);
    }
}
