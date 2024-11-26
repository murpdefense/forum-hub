package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCommentUsecase {

    private final CommentRepository commentRepository;

    public CreateCommentUsecase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentEntity execute(CommentEntity commentEntity) {
        return commentRepository.save(commentEntity);
    }
}
