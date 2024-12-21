package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
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
    private final ForumRepository forumRepository;

    /**
     * Constructs a new DeleteTopicUsecase with the specified repository.
     *
     * @param topicRepository the repository for managing topics
     * @param forumRepository the repository for managing forums
     */
    public DeleteTopicUseCase(TopicRepository topicRepository,
                              ForumRepository forumRepository) {
        this.topicRepository = topicRepository;
        this.forumRepository = forumRepository;
    }

    /**
     * Executes the use case to delete a topic.
     *
     * @param id                  the unique identifier of the topic to be deleted
     * @param authenticatedUserId the authenticated user's unique identifier
     * @throws ResourceNotFoundException if the topic specified by the id does not exist
     * @throws ForbiddenException        if the authenticated user is not the creator of the topic
     */
    public void execute(UUID id, UUID authenticatedUserId) {
        TopicEntity topicDB = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found."));

        if (!topicDB.getCreator().getId().equals(authenticatedUserId)) {
            throw new ForbiddenException("You are not allowed to delete this topic.");
        }

        ForumEntity forumFound = forumRepository.findById(topicDB.getForum().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Forum not found."));
        forumFound.decrementTopicsCount();

        topicRepository.delete(topicDB);
        forumRepository.save(forumFound);
    }
}
