package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.mapper.CommentMapper;
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
 * Service for handling the creation of comments in a forum.
 * Ensures that the user is allowed to comment, validates the existence of the topic,
 * and verifies the parent comment (if provided) belongs to the correct topic.
 * The user must be a participant of the forum associated with the topic, and any parent comment must be part of the same topic.
 *
 * @author <a href="https://soupaulodev.com.br">souapulodev</a>
 */
@Service
public class CreateCommentUseCase {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public CreateCommentUseCase(CommentRepository commentRepository,
                                UserRepository userRepository,
                                TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * Creates a new comment for a specified topic.
     * <p>
     * Validates user permission, checks for the existence of the topic and parent comment (if applicable),
     * and ensures the comment is associated with the correct topic.
     * </p>
     *
     * @param requestDTO the data for the new comment
     * @param authenticatedUserId the UUID of the user creating the comment
     * @return a DTO containing the newly created comment's data
     * @throws ResourceNotFoundException if the user, topic, or parent comment is not found
     * @throws ForbiddenException if the user is not authorized to comment on the topic
     * @throws IllegalArgumentException if the parent comment is not from the specified topic
     */
    public CommentResponseDTO execute(CommentCreateRequestDTO requestDTO, UUID authenticatedUserId) {

        UserEntity user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        TopicEntity topic = topicRepository.findById(UUID.fromString(requestDTO.topicId()))
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found."));

        if (!user.participatingInForum(topic.getForum())) {
            throw new ForbiddenException("You are not allowed to create a comment for this topic.");
        }

        CommentEntity parentComment = null;
        if (requestDTO.parentCommentId() != null) {
            parentComment = commentRepository.findById(UUID.fromString(requestDTO.parentCommentId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found."));

            if (!parentComment.getTopic().equals(topic)) {
                throw new IllegalArgumentException("Parent comment does not belong to the specified topic.");
            }
        }

        CommentEntity newComment = new CommentEntity(requestDTO.content(), user, topic, parentComment);
        commentRepository.save(newComment);

        topic.incrementComments();
        topicRepository.save(topic);

        return CommentMapper.toResponseDTO(newComment);
    }
}
