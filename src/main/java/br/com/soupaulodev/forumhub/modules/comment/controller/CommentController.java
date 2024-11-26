package br.com.soupaulodev.forumhub.modules.comment.controller;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.mapper.CommentMapper;
import br.com.soupaulodev.forumhub.modules.comment.usecase.CreateCommentUsecase;
import br.com.soupaulodev.forumhub.modules.comment.usecase.DeleteCommentUsecase;
import br.com.soupaulodev.forumhub.modules.comment.usecase.UpdateCommentUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CreateCommentUsecase createCommentUsecase;
    private final UpdateCommentUsecase updateCommentUsecase;
    private final DeleteCommentUsecase deleteCommentUsecase;

    public CommentController(CreateCommentUsecase createCommentUsecase,
                             UpdateCommentUsecase updateCommentUsecase,
                             DeleteCommentUsecase deleteCommentUsecase) {
        this.createCommentUsecase = createCommentUsecase;
        this.updateCommentUsecase = updateCommentUsecase;
        this.deleteCommentUsecase = deleteCommentUsecase;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@Valid
                                                            @RequestBody
                                                            CommentRequestDTO commentRequestDTO) {
        CommentEntity commentCreated = createCommentUsecase.execute(CommentMapper.toEntity(commentRequestDTO));
        return ResponseEntity.ok(CommentMapper.toResponseDTO(commentCreated));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@Valid @PathVariable UUID id,
                                                            @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentEntity commentUpdated = updateCommentUsecase.execute(id, CommentMapper.toEntity(commentRequestDTO));
        return ResponseEntity.ok(CommentMapper.toResponseDTO(commentUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        deleteCommentUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
