package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for deleting comments.
 *
 * @author <a href="https://soupaulodev.com.br">soupauldev</a>
 */
@Service
public class DeleteCommentUseCase {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    /**
     * Constructs a new DeleteCommentUsecase with the specified repository.
     *
     * @param commentRepository the repository for managing comments
     * @param userRepository    the repository for managing users
     * @param topicRepository   the repository for managing topics
     */
    public DeleteCommentUseCase(CommentRepository commentRepository,
                                UserRepository userRepository,
                                TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to delete a comment by its ID.
     *
     * @param id the UUID of the comment to be deleted
     * @throws ResourceNotFoundException if the comment, user or topic is not found
     * @throws ForbiddenException        if the user is not allowed to delete the comment
     */
    public void execute(UUID id, UUID getAuthenticatedUserId) {

        CommentEntity commentFound = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));

        if (!commentFound.getUser().getId().equals(getAuthenticatedUserId)) {
            throw new ForbiddenException("You are not allowed to delete a comment for another user");
        }

        UserEntity user = userRepository.findById(getAuthenticatedUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        TopicEntity topic = topicRepository.findById(commentFound.getTopic().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found."));
        if (!user.participatingInForum(topic.getForum())) {
            throw new ForbiddenException("You are not allowed to update a comment in a topic you do not participate in");
        }

        commentRepository.delete(commentFound);
        topic.decrementComments();
        topicRepository.save(topic);
    }
}