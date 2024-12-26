package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentHighsEntity;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentHighsRepository;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case to high a Comment
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class HighCommentUseCase {

    private final CommentHighsRepository commentHighsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    /**
     * Constructor
     *
     * @param commentHighsRepository comment highs repository
     * @param commentRepository comment repository
     * @param userRepository user repository
     */
    public HighCommentUseCase(CommentHighsRepository commentHighsRepository,
                              CommentRepository commentRepository,
                              UserRepository userRepository) {
        this.commentHighsRepository = commentHighsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Use case to high a Comment
     *
     * @param commentId comment id
     * @param authenticatedUserId authenticated user id
     */
    public void execute(UUID commentId, UUID authenticatedUserId) {
        commentHighsRepository.findByComment_IdAndUser_Id(commentId, authenticatedUserId)
                .ifPresent(commentHighsEntity -> {
                    throw new IllegalArgumentException("Comment already highed");
                });

        CommentEntity commentFound = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment not found")
        );

        UserEntity authenticatedUserEntity = userRepository.findById(authenticatedUserId).orElseThrow(
                () -> new IllegalArgumentException("Comment not found")
        );

        commentFound.incrementHighs();
        commentHighsRepository.save(new CommentHighsEntity(commentFound, authenticatedUserEntity));
    }
}
