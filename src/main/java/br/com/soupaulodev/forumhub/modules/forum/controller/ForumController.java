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
 * REST controller for managing forums.
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
     * Constructs a new ForumController with the specified use cases.
     *
     * @param createForumUsecase the use case for creating forums
     * @param listForumsPageableUsecase the use case for listing forums with pagination
     * @param listForumsByNamePageableUsecase the use case for listing forums by name with pagination
     * @param getForumDetailsUsecase the use case for getting forum details
     * @param updateForumUsecase the use case for updating forums
     * @param deleteForumUsecase the use case for deleting forums
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
     * Creates a new forum.
     *
     * @param requestDTO the data transfer object containing the forum creation data
     * @return the response entity containing the created forum data
     */
    @PostMapping
    public ResponseEntity<ForumResponseDTO> createForum(@Valid @RequestBody ForumCreateRequestDTO requestDTO) {
        ForumResponseDTO responseDTO = createForumUsecase.execute(requestDTO);

        URI uri = URI.create("/forums/" + responseDTO.getId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    /**
     * Retrieves the details of a specific forum.
     *
     * @param id the unique identifier of the forum
     * @return the response entity containing the forum details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ForumResponseDTO> getForumDetails(@Valid
                                                            @PathVariable
                                                            @org.hibernate.validator.constraints.UUID
                                                            UUID id) {

        return ResponseEntity.ok(getForumDetailsUsecase.execute(id));
    }

    /**
     * Lists forums with pagination support.
     *
     * @param page the page number to retrieve
     * @return the response entity containing the list of forums
     */
    @GetMapping("/all/{page}")
    public ResponseEntity<List<ForumResponseDTO>> listForumsPageable(@PathVariable int page) {

        return ResponseEntity.ok(listForumsPageableUsecase.execute(page));
    }

    /**
     * Lists forums by name with pagination support.
     *
     * @param name the name of the forums to search for
     * @param page the page number to retrieve
     * @return the response entity containing the list of forums
     */
    @GetMapping("/{name}/{page}")
    public ResponseEntity<List<ForumResponseDTO>> listForumsByNamePageable(@PathVariable String name,
                                                                           @PathVariable int page) {

        return ResponseEntity.ok(listForumsByNamePageableUsecase.execute(name, page));
    }

    /**
     * Updates a specific forum.
     *
     * @param id the unique identifier of the forum to be updated
     * @param forumRequestDTO the data transfer object containing the forum update data
     * @return the response entity containing the updated forum data
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
     * Deletes a specific forum.
     *
     * @param id the unique identifier of the forum to be deleted
     * @return the response entity indicating the deletion status
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
