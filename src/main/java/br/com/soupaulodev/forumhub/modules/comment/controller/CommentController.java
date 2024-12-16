package br.com.soupaulodev.forumhub.modules.comment.controller;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.usecase.CreateCommentUsecase;
import br.com.soupaulodev.forumhub.modules.comment.usecase.DeleteCommentUsecase;
import br.com.soupaulodev.forumhub.modules.comment.usecase.UpdateCommentUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing comments.
 */
@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comments", description = "Operations related to comments")
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
     * Endpoint for handling comment creation.
     * This method creates a comment and returns the created comment data.
     *
     * @param requestDTO the DTO containing the comment data
     * @return the response entity of CommentResponseDTO with status 200 (OK) and the created comment data
     */
    @PostMapping
    @Operation(summary = "Create a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Comment already exists")
    })
    public ResponseEntity<CommentResponseDTO> createComment(@Valid
                                                            @RequestBody
                                                            CommentRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        return ResponseEntity.ok(createCommentUsecase.execute(requestDTO, authenticatedUserId));
    }

    /**
     * Endpoint for handling comment updates.
     * This method updates a comment by its unique identifier, using the data provided in the request DTO.
     *
     * @param id the comment's unique identifier to be updated
     * @param requestDTO the DTO containing the comment data
     * @return the response entity of CommentResponseDTO with status 200 (OK) and the updated comment data
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<CommentResponseDTO> updateComment(@Valid @PathVariable
                                                            @org.hibernate.validator.constraints.UUID String id,
                                                            @RequestBody CommentRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        return ResponseEntity.ok(updateCommentUsecase.execute(UUID.fromString(id), requestDTO, authenticatedUserId));
    }

    /**
     * Endpoint for handling comment deletion operations.
     * This method deletes a comment by its unique identifier.
     *
     * @param id the comment's unique identifier to be deleted
     * @return a response entity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete comment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<Void> deleteComment(@Valid @PathVariable @org.hibernate.validator.constraints.UUID String id) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        deleteCommentUsecase.execute(UUID.fromString(id), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the authenticated user's unique identifier.
     *
     * @return the authenticated user's unique identifier
     */
    private UUID getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UUID) {
            return (UUID) principal;
        } else if (principal instanceof String) {
            return UUID.fromString((String) principal);
        }

        throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    }
}