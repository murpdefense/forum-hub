package br.com.soupaulodev.forumhub.modules.comment.controller;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing comments.
 */
@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comments", description = "Operations related to comments")
public class CommentController {

    private final CreateCommentUseCase createCommentUseCase;
    private final ListCommentsUseCase listCommentsUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final HighCommentUseCase highCommentUseCase;
    private final UnHighCommentUseCase unHighCommentUseCase;

    /**
     * Constructs a new CommentController with the specified use cases.
     *
     * @param createCommentUseCase the use case for creating comments
     * @param listCommentsUseCase  the use case for listing comments
     * @param updateCommentUseCase the use case for updating comments
     * @param deleteCommentUseCase the use case for deleting comments
     * @param highCommentUseCase   the use case for highing comments
     * @param unHighCommentUseCase the use case for unhighing comments
     */
    public CommentController(CreateCommentUseCase createCommentUseCase,
                             ListCommentsUseCase listCommentsUseCase,
                             UpdateCommentUseCase updateCommentUseCase,
                             DeleteCommentUseCase deleteCommentUseCase,
                             HighCommentUseCase highCommentUseCase,
                             UnHighCommentUseCase unHighCommentUseCase) {
        this.createCommentUseCase = createCommentUseCase;
        this.listCommentsUseCase = listCommentsUseCase;
        this.updateCommentUseCase = updateCommentUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.highCommentUseCase = highCommentUseCase;
        this.unHighCommentUseCase = unHighCommentUseCase;
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
                                                            CommentCreateRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        return ResponseEntity.ok(createCommentUseCase.execute(requestDTO, authenticatedUserId));
    }

    /**
     * Endpoint for handling comment listing operations.
     * This method lists all comments.
     *
     * @return the response entity of CommentResponseDTO with status 200 (OK) and the list of comments
     */
    @GetMapping("/all")
    @Operation(summary = "List all comments")
    @ApiResponse(responseCode = "200", description = "Comments listed successfully")
    public ResponseEntity<List<CommentResponseDTO>> listComments(@Valid @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                 @Valid @RequestParam(defaultValue = "5") @Min(5) int size) {
        return ResponseEntity.ok(listCommentsUseCase.execute(page, size));
    }

    /**
     * Endpoint for handling comment updates.
     * This method updates a comment by its unique identifier, using the data provided in the request DTO.
     *
     * @param id         the comment's unique identifier to be updated
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
                                                            @RequestBody CommentUpdateRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        return ResponseEntity.ok(updateCommentUseCase.execute(UUID.fromString(id), requestDTO, authenticatedUserId));
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
        deleteCommentUseCase.execute(UUID.fromString(id), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for handling comment high operations.
     * This method high a comment by its unique identifier.
     *
     * @param commentId the comment's unique identifier to be highed
     * @return a response entity with status 204 (No Content)
     */
    @Operation(summary = "High a comment", description = "High a comment by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment highed"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PostMapping("/high/{id}")
    public ResponseEntity<Void> highComment(@Valid @PathVariable("id")
                                                          @org.hibernate.validator.constraints.UUID String commentId) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        highCommentUseCase.execute(UUID.fromString(commentId), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for handling comment unhigh operations.
     * This method unhigh a comment by its unique identifier.
     *
     * @param commentId the comment's unique identifier to be unhighed
     * @return a response entity with status 204 (No Content)
     */
    @Operation(summary = "Unhigh a comment", description = "Unhigh a comment by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment unhighed"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @DeleteMapping("/high/{id}")
    public ResponseEntity<Void> unHighComment(@Valid @PathVariable("id")
                                             @org.hibernate.validator.constraints.UUID String commentId) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        unHighCommentUseCase.execute(UUID.fromString(commentId), authenticatedUserId);
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