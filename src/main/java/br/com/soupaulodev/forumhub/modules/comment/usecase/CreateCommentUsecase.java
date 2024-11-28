package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.mapper.CommentMapper;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for creating comments.
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
     */
    public CommentResponseDTO execute(CommentCreateRequestDTO requestDTO) {

        UserEntity user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(UserNotFoundException::new);
        TopicEntity topic = topicRepository.findById(requestDTO.getTopicId())
                .orElseThrow(UserNotFoundException::new);

        CommentEntity entity = commentRepository.save(CommentMapper.toEntity(requestDTO, user, topic));
        return CommentMapper.toResponseDTO(entity);
    }
}