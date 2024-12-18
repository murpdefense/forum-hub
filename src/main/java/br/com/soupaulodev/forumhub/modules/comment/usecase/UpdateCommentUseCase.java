package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentUpdateRequestDTO;
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

import java.time.Instant;
import java.util.UUID;

/**
 * Service class for updating comments.
 */
@Service
public class UpdateCommentUseCase {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    /**
     * Constructs a new UpdateCommentUsecase with the specified repository.
     *
     * @param commentRepository the repository for managing comments
     * @param userRepository    the repository for managing users
     * @param topicRepository   the repository for managing topics
     */
    public UpdateCommentUseCase(CommentRepository commentRepository,
                                UserRepository userRepository,
                                TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to update a comment.
     *
     * @param id         the UUID of the comment to be updated
     * @param requestDTO the DTO containing the updated comment data
     * @return the response DTO with the updated comment data
     * @throws ResourceNotFoundException if the comment, user or topic is not found
     * @throws IllegalArgumentException  if the new content is the same as the old content or if the content is empty
     * @throws ForbiddenException        if the user is not allowed to update the comment
     */
    public CommentResponseDTO execute(UUID id, CommentUpdateRequestDTO requestDTO, UUID authenticatedUserId) {

        CommentEntity commentFound = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));

        if (requestDTO.content().equals(commentFound.getContent())) {
            throw new IllegalArgumentException("New content must be different from the old content");
        }
        if (requestDTO.content().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }

        if (!commentFound.getUser().getId().equals(authenticatedUserId)) {
            throw new ForbiddenException("You are not allowed to update a comment for another user");
        }

        UserEntity user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        TopicEntity topic = topicRepository.findById(commentFound.getTopic().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found."));
        if (user.participatingInForum(topic.getForum())) {
            throw new ForbiddenException("You are not allowed to update a comment for this topic");
        }

        commentFound.setContent(requestDTO.content());
        commentFound.setUpdatedAt(Instant.now());

        commentRepository.save(commentFound);
        return CommentMapper.toResponseDTO(commentFound);
    }
}