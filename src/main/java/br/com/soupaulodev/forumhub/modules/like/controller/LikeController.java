package br.com.soupaulodev.forumhub.modules.like.controller;

import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeRequestDTO;
import br.com.soupaulodev.forumhub.modules.like.usecase.LikeResourceUsecase;
import br.com.soupaulodev.forumhub.modules.like.usecase.DislikeResourceUsecase;
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
 * Controller for handling like-related operations.
 * This class provides endpoints for liking and unliking topics.
 * The like-related operations are managed by interacting with the use cases for liking and unliking topics.
 *
 * <p>
 *     The {@link LikeController} is responsible for:
 *     <ul>
 *         <li>Handling like requests.</li>
 *         <li>Handling unlike requests.</li>
 *     </ul>
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@RestController
@RequestMapping("/api/v1/likes")
@Tag(name = "Like", description = "Operations related to likes")
public class LikeController {

    private final LikeResourceUsecase likeResourceUsecase;
    private final DislikeResourceUsecase dislikeResourceUsecase;

    /**
     * Constructs a new LikeController with the specified use cases.
     *
     * @param likeResourceUsecase the use case for liking a topic
     * @param dislikeResourceUsecase the use case for disliking a topic
     */
    public LikeController(LikeResourceUsecase likeResourceUsecase, DislikeResourceUsecase dislikeResourceUsecase) {
        this.likeResourceUsecase = likeResourceUsecase;
        this.dislikeResourceUsecase = dislikeResourceUsecase;
    }

    /**
     * Endpoint for liking a resource.
     *
     * @param requestDTO the request data for liking a resource.
     * @return a ResponseEntity with HTTP status 200 (OK)
     */
    @PostMapping()
    @Operation(summary = "Like a resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "{ResourceType} liked successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "{ResourceType} not found")
    })
    public ResponseEntity<Void> likeResource(@Valid @RequestBody LikeRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        likeResourceUsecase.execute(requestDTO, authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for disliking a resource.
     *
     * @param requestDTO the request data for unliking a resource.
     * @return a ResponseEntity with HTTP status 200 (OK)
     */
    @DeleteMapping()
    @Operation(summary = "Unlike a resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "{ResourceType} unliked successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "{ResourceType} not found")
    })
    public ResponseEntity<Void> dislike(@Valid @RequestBody LikeRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        dislikeResourceUsecase.execute(requestDTO, authenticatedUserId);
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
