package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.exception.usecase.CommentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for deleting comments.
 */
@Service
public class DeleteCommentUsecase {

    public final CommentRepository commentRepository;

    /**
     * Constructs a new DeleteCommentUsecase with the specified repository.
     *
     * @param commentRepository the repository for managing comments
     */
    public DeleteCommentUsecase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Executes the use case to delete a comment by its ID.
     *
     * @param id the UUID of the comment to be deleted
     * @throws CommentNotFoundException if the comment is not found
     */
    public void execute(UUID id) {

        CommentEntity commentFound = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(commentFound);
    }
}