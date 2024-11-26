package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.exception.usecase.CommentIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.CommentNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UpdateCommentUsecase {

    private final CommentRepository commentRepository;

    public UpdateCommentUsecase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentEntity execute(UUID id, CommentEntity newComment) {
        CommentEntity commentDB = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        if (newComment.getMessage() == commentDB.getMessage()) {
            throw new CommentIllegalArgumentException();
        }
        commentDB.setMessage(newComment.getMessage());
        commentDB.setUpdatedAt(Instant.now());

        return commentRepository.save(commentDB);
    }
}
