package br.com.soupaulodev.forumhub.modules.comment.usecase;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.mapper.CommentMapper;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for listing comments.
 *
 * @author <a href="https://soupaulodev.com.br">soupauldev</a>
 */
@Service
public class ListCommentsUseCase {

    private final CommentRepository commentRepository;

    /**
     * Constructs a new ListCommentsUseCase with the specified repository.
     *
     * @param commentRepository the repository for managing comments
     */
    public ListCommentsUseCase(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Executes the use case to list all comments.
     *
     * @return the list of comments
     */
    public List<CommentResponseDTO> execute(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CommentEntity> entities = commentRepository.findAll(pageable);
        return entities.getContent().stream().filter(comment -> comment.getParentComment() == null)
                .map(CommentMapper::toResponseDTO)
                .toList();
    }
}
