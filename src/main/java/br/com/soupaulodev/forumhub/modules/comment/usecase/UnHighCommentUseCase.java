package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.repository.CommentHighsRepository;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case to unhigh a Comment
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class UnHighCommentUseCase {

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
    public UnHighCommentUseCase(CommentHighsRepository commentHighsRepository,
                                CommentRepository commentRepository,
                                UserRepository userRepository) {
        this.commentHighsRepository = commentHighsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Execute the use case
     *
     * @param commentId comment id
     * @param authenticatedUserId authenticated user id
     */
    public void execute(UUID commentId, UUID authenticatedUserId) {
        commentHighsRepository.findByComment_IdAndUser_Id(commentId, authenticatedUserId)
                .ifPresentOrElse(
                        commentHighsRepository::delete,
                    () -> {
                        throw new IllegalArgumentException("Comment not highed");
                    }
                );
    }
}
