package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.mapper.CommentMapper;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.exception.usecase.*;
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
public class UpdateCommentUsecase {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    /**
     * Constructs a new UpdateCommentUsecase with the specified repository.
     *
     * @param commentRepository the repository for managing comments
     * @param userRepository the repository for managing users
     * @param topicRepository the repository for managing topics
     */
    public UpdateCommentUsecase(CommentRepository commentRepository,
                                UserRepository userRepository,
                                TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to update a comment.
     *
     * @param id the UUID of the comment to be updated
     * @param requestDTO the DTO containing the updated comment data
     * @return the response DTO with the updated comment data
     * @throws CommentNotFoundException if the comment is not found
     * @throws CommentIllegalArgumentException if the new content is the same as the old content or if the content is empty
     * @throws UserNotFoundException if the user or topic is not found
     * @throws UnauthorizedException if the user is not allowed to update a comment for another user or topic
     * @throws TopicNotFoundException if the topic is not found
     */
    public CommentResponseDTO execute(UUID id, CommentRequestDTO requestDTO, UUID getAuthenticatedUserId) {

        CommentEntity commentFound = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if (requestDTO.content().equals(commentFound.getContent())) {
            throw new CommentIllegalArgumentException();
        }

        if (requestDTO.content().isEmpty()) {
            throw new CommentIllegalArgumentException("Comment content cannot be empty");
        }

        UserEntity user = userRepository.findById(commentFound.getUser().getId())
                .orElseThrow(UserNotFoundException::new);
        if (!user.getId().equals(getAuthenticatedUserId)) {
            throw new UnauthorizedException("You are not allowed to update a comment for another user");
        }

        TopicEntity topic = topicRepository.findById(commentFound.getTopic().getId())
                .orElseThrow(TopicNotFoundException::new);

        if (user.participateInForum(topic.getForum())) {
            throw new UnauthorizedException("You are not allowed to update a comment for this topic");
        }

        commentFound.setContent(requestDTO.content());
        commentFound.setUpdatedAt(Instant.now());

        CommentEntity commentUpdated = commentRepository.save(commentFound);
        return CommentMapper.toResponseDTO(commentUpdated);
    }
}