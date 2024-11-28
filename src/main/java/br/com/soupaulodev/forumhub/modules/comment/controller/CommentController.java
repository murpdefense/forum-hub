package br.com.soupaulodev.forumhub.modules.comment.controller;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentUpdateRequestDTO;
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
                                                            CommentCreateRequestDTO requestDTO) {

        return ResponseEntity.ok(createCommentUsecase.execute(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@Valid @PathVariable UUID id,
                                                            @RequestBody CommentUpdateRequestDTO requestDTO) {

        return ResponseEntity.ok(updateCommentUsecase.execute(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {

        deleteCommentUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
