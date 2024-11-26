package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.exception.usecase.CommentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCommentUsecase {

    public final CommentRepository commentRepository;

    public DeleteCommentUsecase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void execute(UUID id) {

        CommentEntity commentDB = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(commentDB);
    }
}
