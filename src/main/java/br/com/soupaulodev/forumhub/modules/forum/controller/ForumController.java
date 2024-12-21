package br.com.soupaulodev.forumhub.modules.forum.controller;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Controller for handling forum-related operations.
 * This class provides endpoints for creating, retrieving by ID, listing, updating, and deleting forums.
 * The forum-related operations are managed by interacting with the use cases for creating, retrieving by ID, listing,
 * updating, and deleting forums.
 *
 * <p>
 * The {@link ForumController} is responsible for:
 *     <ul>
 *         <li>Handling forum creation requests.</li>
 *         <li>Handling forum retrieval requests by ID.</li>
 *         <li>Handling forum listing requests.</li>
 *         <li>Handling forum update requests.</li>
 *         <li>Handling forum deletion requests.</li>
 *     </ul>
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@RestController
@RequestMapping("/api/v1/forums")
@Tag(name = "Forum", description = "Operations related to forums")
public class ForumController {

    private final CreateForumUseCase createForumUseCase;
    private final ListForumsUseCase listForumsUseCase;
    private final GetForumUseCase getForumUseCase;
    private final UpdateForumUseCase updateForumUseCase;
    private final DeleteForumUseCase deleteForumUseCase;
    private final HighForumUseCase highForumUseCase;
    private final UnHighForumUseCase unHighForumUseCase;

    /**
     * Constructs a new {@link ForumController} with the specified use cases.
     *
     * @param createForumUseCase {@link CreateForumUseCase} the use case for creating forums
     * @param listForumsUseCase  {@link ListForumsUseCase} the use case for listing forums with pagination
     * @param getForumUseCase    {@link GetForumUseCase} the use case for getting forum details
     * @param updateForumUseCase {@link UpdateForumUseCase} the use case for updating forums
     * @param deleteForumUseCase {@link DeleteForumUseCase} the use case for deleting forums
     * @param highForumUseCase {@link HighForumUseCase} the use case for high forums
     * @param unHighForumUseCase {@link UnHighForumUseCase} the use case for unhigh forums
     */
    public ForumController(CreateForumUseCase createForumUseCase,
                           ListForumsUseCase listForumsUseCase,
                           GetForumUseCase getForumUseCase,
                           UpdateForumUseCase updateForumUseCase,
                           DeleteForumUseCase deleteForumUseCase,
                           HighForumUseCase highForumUseCase,
                           UnHighForumUseCase unHighForumUseCase) {
        this.createForumUseCase = createForumUseCase;
        this.listForumsUseCase = listForumsUseCase;
        this.getForumUseCase = getForumUseCase;
        this.updateForumUseCase = updateForumUseCase;
        this.deleteForumUseCase = deleteForumUseCase;
        this.highForumUseCase = highForumUseCase;
        this.unHighForumUseCase = unHighForumUseCase;
    }

    /**
     * Endpoint for handling forum creation.
     * This method creates a forum and returns the created forum data.
     *
     * @param requestDTO {@link ForumCreateRequestDTO} the data transfer object containing the forum creation data
     * @return a {@link ResponseEntity} of {@link ForumResponseDTO} with status 201 (Created) and the created forum data
     */
    @PostMapping
    @Operation(summary = "Create a new forum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Forum created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Forum already exists")
    })
    public ResponseEntity<ForumResponseDTO> createForum(@Valid @RequestBody ForumCreateRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        ForumResponseDTO responseDTO = createForumUseCase.execute(requestDTO, authenticatedUserId);

        URI uri = URI.create("/forums/" + responseDTO.id());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    /**
     * Endpoint for handling listing of forums with pagination support.
     * This method lists forums with pagination support and returns the list of forums.
     *
     * @param page {@link Integer} the page number to retrieve
     * @param size {@link Integer} the number of forums to retrieve per page
     * @return a {@link ResponseEntity} of {@link List} of {@link ForumResponseDTO} with status 200 (OK) and the list of forums
     */
    @GetMapping("/all")
    @Operation(summary = "List all forums")
    @ApiResponse(responseCode = "200", description = "Forums listed")
    public ResponseEntity<List<ForumResponseDTO>> listForums(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(listForumsUseCase.execute(page, size));
    }

    /**
     * Endpoint for handling retrieval of a forum by its unique identifier.
     * This method retrieves a forum by its unique identifier and returns the forum data.
     *
     * @param id the forum's unique identifier of type {@link UUID}
     * @return a {@link ResponseEntity} of {@link ForumResponseDTO} with status 200 (OK) and the forum data
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get forum by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forum retrieved"),
            @ApiResponse(responseCode = "404", description = "Forum not found")
    })
    public ResponseEntity<ForumResponseDTO> getForum(@PathVariable
                                                     UUID id) {

        return ResponseEntity.ok(getForumUseCase.execute(id));
    }

    /**
     * Endpoint for handling forum update operations.
     * This method updates a forum by its unique identifier, using the data provided in the request DTO
     *
     * @param id              the forum's unique identifier to be updated
     * @param forumRequestDTO {@link ForumUpdateRequestDTO} the data transfer object containing the forum update data
     * @return a {@link ResponseEntity} of {@link ForumResponseDTO} with status 200 (OK) and the updated forum data
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update forum by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forum updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Forum not found")
    })
    public ResponseEntity<ForumResponseDTO> updateForum(@Valid
                                                        @PathVariable
                                                        @org.hibernate.validator.constraints.UUID
                                                        String id,
                                                        @Valid
                                                        @RequestBody
                                                        ForumUpdateRequestDTO forumRequestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        return ResponseEntity.ok(updateForumUseCase.execute(UUID.fromString(id), forumRequestDTO, authenticatedUserId));
    }

    /**
     * Endpoint for handling forum deletion operations.
     * This method deletes a forum by its unique identifier.
     *
     * @param id the forum's unique identifier to be deleted
     * @return a response entity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete forum by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Forum deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Forum not found")
    })
    public ResponseEntity<Void> deleteForum(@Valid
                                            @PathVariable
                                            @org.hibernate.validator.constraints.UUID
                                            String id) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        deleteForumUseCase.execute(UUID.fromString(id), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for handling forum high operations.
     * This method high a forum by its unique identifier.
     *
     * @param id the forum's unique identifier to be high
     * @return a response entity with status 204 (No Content)
     */
    @Operation(summary = "High forum by ID", description = "High a forum by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Forum high"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Forum not found")
    })
    @PostMapping("/high/{id}")
    public ResponseEntity<Void> highForum(@Valid @PathVariable("id")
                                          @org.hibernate.validator.constraints.UUID String id) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        highForumUseCase.execute(UUID.fromString(id), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for handling forum unhigh operations.
     * This method unhigh a forum by its unique identifier.
     *
     * @param id the forum's unique identifier to be unhigh
     * @return a response entity with status 204 (No Content)
     */
    @Operation(summary = "Unhigh forum by ID", description = "Unhigh a forum by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Forum unhigh"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Forum not found")
    })
    @DeleteMapping("/unhigh/{id}")
    public ResponseEntity<Void> unHighForum(@Valid @PathVariable("id")
                                          @org.hibernate.validator.constraints.UUID String id) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        unHighForumUseCase.execute(UUID.fromString(id), authenticatedUserId);
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
