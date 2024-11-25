package br.com.soupaulodev.forumhub.modules.forum.controller;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
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
    public ResponseEntity<ForumResponseDTO> createForum(@Valid @RequestBody ForumRequestDTO forumResponseDTO) {
        ForumEntity forumCreated = createForumUsecase.execute(ForumMapper.toEntity(forumResponseDTO));
        URI uri = URI.create("/forums/" + forumCreated.getId());
        return ResponseEntity.created(uri).body(ForumMapper.toResponseDTO(forumCreated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumResponseDTO> getForumDetails(@Valid
                                                            @PathVariable
                                                            @org.hibernate.validator.constraints.UUID
                                                            UUID id) {
        return ResponseEntity.ok(ForumMapper.toResponseDTO(getForumDetailsUsecase.execute(id)));
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<List<ForumResponseDTO>> listForumsPageable(@PathVariable int page) {
        List<ForumEntity> forums = listForumsPageableUsecase.execute(page);
        return ResponseEntity.ok(forums.stream().map(ForumMapper::toResponseDTO).toList());
    }

    @GetMapping("/{name}/{page}")
    public ResponseEntity<List<ForumResponseDTO>> listForumsByNamePageable(@PathVariable String name,
                                                                           @PathVariable int page) {
        List<ForumEntity> forums = listForumsByNamePageableUsecase.execute(name, page);
        return ResponseEntity.ok(forums.stream().map(ForumMapper::toResponseDTO).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumResponseDTO> updateForum(@Valid
                                                        @PathVariable
                                                        @org.hibernate.validator.constraints.UUID
                                                        UUID id,
                                                        @Valid
                                                        @RequestBody
                                                        ForumRequestDTO forumRequestDTO) {
        ForumEntity forumUpdated = updateForumUsecase.execute(id, ForumMapper.toEntity(forumRequestDTO));
        return ResponseEntity.ok(ForumMapper.toResponseDTO(forumUpdated));
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
