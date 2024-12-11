package br.com.soupaulodev.forumhub.modules.forum.controller;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.usecase.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
 *     The {@link ForumController} is responsible for:
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
public class ForumController {

    private final CreateForumUsecase createForumUsecase;
    private final ListForumsPageableUsecase listForumsPageableUsecase;
    private final ListForumsByNamePageableUsecase listForumsByNamePageableUsecase;
    private final GetForumDetailsUsecase getForumDetailsUsecase;
    private final UpdateForumUsecase updateForumUsecase;
    private final DeleteForumUsecase deleteForumUsecase;

    /**
     * Constructs a new {@link ForumController} with the specified use cases.
     *
     * @param createForumUsecase {@link CreateForumUsecase} the use case for creating forums
     * @param listForumsPageableUsecase {@link ListForumsPageableUsecase} the use case for listing forums with pagination
     * @param listForumsByNamePageableUsecase {@link ListForumsByNamePageableUsecase} the use case for listing forums by name with pagination
     * @param getForumDetailsUsecase {@link GetForumDetailsUsecase} the use case for getting forum details
     * @param updateForumUsecase {@link UpdateForumUsecase} the use case for updating forums
     * @param deleteForumUsecase {@link DeleteForumUsecase} the use case for deleting forums
     */
    public ForumController(CreateForumUsecase createForumUsecase,
                           ListForumsPageableUsecase listForumsPageableUsecase,
                           ListForumsByNamePageableUsecase listForumsByNamePageableUsecase,
                           GetForumDetailsUsecase getForumDetailsUsecase,
                           UpdateForumUsecase updateForumUsecase,
                           DeleteForumUsecase deleteForumUsecase) {
        this.createForumUsecase = createForumUsecase;
        this.listForumsPageableUsecase = listForumsPageableUsecase;
        this.listForumsByNamePageableUsecase = listForumsByNamePageableUsecase;
        this.getForumDetailsUsecase = getForumDetailsUsecase;
        this.updateForumUsecase = updateForumUsecase;
        this.deleteForumUsecase = deleteForumUsecase;
    }

    /**
     * Endpoint for handling forum creation.
     * This method creates a forum and returns the created forum data.
     *
     * @param requestDTO {@link ForumCreateRequestDTO} the data transfer object containing the forum creation data
     * @return a {@link ResponseEntity} of {@link ForumResponseDTO} with status 201 (Created) and the created forum data
     */
    @PostMapping
    public ResponseEntity<ForumResponseDTO> createForum(@Valid @RequestBody ForumCreateRequestDTO requestDTO) {
        ForumResponseDTO responseDTO = createForumUsecase.execute(requestDTO);

        URI uri = URI.create("/forums/" + responseDTO.id());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    /**
     * Endpoint for handling retrieval of a forum by its unique identifier.
     * This method retrieves a forum by its unique identifier and returns the forum data.
     *
     * @param id the forum's unique identifier of type {@link UUID}
     * @return a {@link ResponseEntity} of {@link ForumResponseDTO} with status 200 (OK) and the forum data
     */
    @GetMapping("/{id}")
    public ResponseEntity<ForumResponseDTO> getForumDetails(@PathVariable
                                                            UUID id) {

        return ResponseEntity.ok(getForumDetailsUsecase.execute(id));
    }

    /**
     * Endpoint for handling listing of forums with pagination support.
     * This method lists forums with pagination support and returns the list of forums.
     *
     * @param page {@link Integer} the page number to retrieve
     * @return a {@link ResponseEntity} of {@link List} of {@link ForumResponseDTO} with status 200 (OK) and the list of forums
     */
    @GetMapping("/all/{page}")
    public ResponseEntity<List<ForumResponseDTO>> listForumsPageable(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(listForumsPageableUsecase.execute(page, size));
    }

    /**
     * Endpoint for handling listing of forums by name with pagination support.
     * This method lists forums by name with pagination support and returns the list of forums.
     *
     * @param name {@link String} the name of the forums to search for
     * @param page {@link Integer} the page number to retrieve
     * @return a {@link ResponseEntity} of {@link List} of {@link ForumResponseDTO} with status 200 (OK) and the list of forums
     */
    @GetMapping("/{name}/{page}")
    public ResponseEntity<List<ForumResponseDTO>> listForumsByNamePageable(@PathVariable String name,
                                                                           @PathVariable int page) {

        return ResponseEntity.ok(listForumsByNamePageableUsecase.execute(name, page));
    }

    /**
     * Endpoint for handling forum update operations.
     * This method updates a forum by its unique identifier, using the data provided in the request DTO
     *
     * @param id the forum's unique identifier of type {@link UUID} to be updated
     * @param forumRequestDTO {@link ForumUpdateRequestDTO} the data transfer object containing the forum update data
     * @return a {@link ResponseEntity} of {@link ForumResponseDTO} with status 200 (OK) and the updated forum data
     */
    @PutMapping("/{id}")
    public ResponseEntity<ForumResponseDTO> updateForum(@Valid
                                                        @PathVariable
                                                        @org.hibernate.validator.constraints.UUID
                                                        UUID id,
                                                        @Valid
                                                        @RequestBody
                                                        ForumUpdateRequestDTO forumRequestDTO) {

        return ResponseEntity.ok(updateForumUsecase.execute(id, forumRequestDTO));
    }

    /**
     * Endpoint for handling forum deletion operations.
     * This method deletes a forum by its unique identifier.
     *
     * @param id the forum's unique identifier of type {@link UUID} to be deleted
     * @return a {@link ResponseEntity} with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForum(@Valid
                                           @PathVariable
                                           @org.hibernate.validator.constraints.UUID
                                           UUID id) {

        deleteForumUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
