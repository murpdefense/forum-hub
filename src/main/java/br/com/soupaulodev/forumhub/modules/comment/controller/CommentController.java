package br.com.soupaulodev.forumhub.modules.comment.controller;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.usecase.CreateCommentUsecase;
import br.com.soupaulodev.forumhub.modules.comment.usecase.DeleteCommentUsecase;
import br.com.soupaulodev.forumhub.modules.comment.usecase.UpdateCommentUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing comments.
 */
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CreateCommentUsecase createCommentUsecase;
    private final UpdateCommentUsecase updateCommentUsecase;
    private final DeleteCommentUsecase deleteCommentUsecase;

    /**
     * Constructs a new CommentController with the specified use cases.
     *
     * @param createCommentUsecase the use case for creating comments
     * @param updateCommentUsecase the use case for updating comments
     * @param deleteCommentUsecase the use case for deleting comments
     */
    public CommentController(CreateCommentUsecase createCommentUsecase,
                             UpdateCommentUsecase updateCommentUsecase,
                             DeleteCommentUsecase deleteCommentUsecase) {
        this.createCommentUsecase = createCommentUsecase;
        this.updateCommentUsecase = updateCommentUsecase;
        this.deleteCommentUsecase = deleteCommentUsecase;
    }

    /**
     * Creates a new comment.
     *
     * @param requestDTO the DTO containing the comment data
     * @return the response entity with the created comment data
     */
    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@Valid
                                                            @RequestBody
                                                            CommentCreateRequestDTO requestDTO) {

        return ResponseEntity.ok(createCommentUsecase.execute(requestDTO));
    }

    /**
     * Updates an existing comment.
     *
     * @param id the UUID of the comment to be updated
     * @param requestDTO the DTO containing the updated comment data
     * @return the response entity with the updated comment data
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@Valid @PathVariable UUID id,
                                                            @RequestBody CommentUpdateRequestDTO requestDTO) {

        return ResponseEntity.ok(updateCommentUsecase.execute(id, requestDTO));
    }

    /**
     * Deletes a comment.
     *
     * @param id the UUID of the comment to be deleted
     * @return the response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {

        deleteCommentUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}