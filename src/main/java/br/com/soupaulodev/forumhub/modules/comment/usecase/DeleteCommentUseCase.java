package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.exception.usecase.CommentNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
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
     * @param userRepository the repository for managing users
     * @param topicRepository the repository for managing topics
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
     * @throws CommentNotFoundException if the comment is not found
     * @throws UserNotFoundException if the user or topic is not found
     * @throws UnauthorizedException if the user is not allowed to delete a comment for another user or topic
     * @throws TopicNotFoundException if the topic is not found
     */
    public void execute(UUID id, UUID getAuthenticatedUserId) {

        CommentEntity commentFound = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if(!commentFound.getUser().getId().equals(getAuthenticatedUserId)) {
            throw new UnauthorizedException("You are not allowed to delete a comment for another user");
        }

        UserEntity user = userRepository.findById(getAuthenticatedUserId)
                .orElseThrow(UserNotFoundException::new);

        TopicEntity topic = topicRepository.findById(commentFound.getTopic().getId())
                .orElseThrow(TopicNotFoundException::new);
        if (!user.participateInForum(topic.getForum())) {
            throw new UnauthorizedException("You are not allowed to update a comment for this topic");
        }

        commentRepository.delete(commentFound);
    }
}