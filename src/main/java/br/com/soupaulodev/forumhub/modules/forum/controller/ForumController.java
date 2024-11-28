package br.com.soupaulodev.forumhub.modules.forum.controller;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.usecase.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/forums")
public class ForumController {

    private final CreateForumUsecase createForumUsecase;
    private final ListForumsPageableUsecase listForumsPageableUsecase;
    private final ListForumsByNamePageableUsecase listForumsByNamePageableUsecase;
    private final GetForumDetailsUsecase getForumDetailsUsecase;
    private final UpdateForumUsecase updateForumUsecase;
    private final DeleteForumUsecase deleteForumUsecase;

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

    @PostMapping
    public ResponseEntity<ForumResponseDTO> createForum(@Valid @RequestBody ForumCreateRequestDTO requestDTO) {
        ForumResponseDTO responseDTO = createForumUsecase.execute(requestDTO);

        URI uri = URI.create("/forums/" + responseDTO.getId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumResponseDTO> getForumDetails(@Valid
                                                            @PathVariable
                                                            @org.hibernate.validator.constraints.UUID
                                                            UUID id) {

        return ResponseEntity.ok(getForumDetailsUsecase.execute(id));
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<List<ForumResponseDTO>> listForumsPageable(@PathVariable int page) {

        return ResponseEntity.ok(listForumsPageableUsecase.execute(page));
    }

    @GetMapping("/{name}/{page}")
    public ResponseEntity<List<ForumResponseDTO>> listForumsByNamePageable(@PathVariable String name,
                                                                           @PathVariable int page) {

        return ResponseEntity.ok(listForumsByNamePageableUsecase.execute(name, page));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForum(@Valid
                                           @PathVariable
                                           @org.hibernate.validator.constraints.UUID
                                           UUID id) {

        deleteForumUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
