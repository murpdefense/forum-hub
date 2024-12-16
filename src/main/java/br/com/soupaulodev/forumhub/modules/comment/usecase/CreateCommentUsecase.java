package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.mapper.CommentMapper;
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
 * Service class for creating comments.
 *
 * @author <a href="https://soupaulodev.com.br">soupauldev</a>
 */
@Service
public class CreateCommentUsecase {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    /**
     * Constructs a new CreateCommentUsecase with the specified repositories.
     *
     * @param commentRepository the repository for managing comments
     * @param userRepository the repository for managing users
     * @param topicRepository the repository for managing topics
     */
    public CreateCommentUsecase(CommentRepository commentRepository,
                                UserRepository userRepository,
                                TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to create a new comment.
     *
     * @param requestDTO the DTO containing the data for the new comment
     * @return the response DTO with the created comment data
     * @throws UserNotFoundException if the user or topic is not found
     * @throws UnauthorizedException if the user is not allowed to create a comment for another user or topic
     */
    public CommentResponseDTO execute(CommentCreateRequestDTO requestDTO, UUID authenticatedUserId) {

        UserEntity user = userRepository.findById(UUID.fromString(requestDTO.userId()))
                .orElseThrow(UserNotFoundException::new);
        if (!user.getId().equals(authenticatedUserId)) {
            throw new UnauthorizedException("You are not allowed to create a comment for another user");
        }

        TopicEntity topic = topicRepository.findById(UUID.fromString(requestDTO.topicId()))
                .orElseThrow(TopicNotFoundException::new);
        if (!user.participateInForum(topic.getForum())) {
            throw new UnauthorizedException("You are not allowed to create a comment for this topic");
        }

        CommentEntity parentComment = null;
        if (requestDTO.parentCommentId() != null) {
            parentComment = commentRepository.findById(UUID.fromString(requestDTO.parentCommentId()))
                    .orElseThrow(() -> new CommentNotFoundException("Parent comment not found with ID: " + requestDTO.parentCommentId()));
        }

        CommentEntity entity = new CommentEntity(requestDTO.content(), user, topic, parentComment);
        commentRepository.save(entity);

        return CommentMapper.toResponseDTO(entity);
    }
}