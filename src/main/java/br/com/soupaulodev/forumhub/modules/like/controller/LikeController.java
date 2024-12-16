package br.com.soupaulodev.forumhub.modules.like.controller;

import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeRequestDTO;
import br.com.soupaulodev.forumhub.modules.like.usecase.LikeResourceUsecase;
import br.com.soupaulodev.forumhub.modules.like.usecase.UnlikeResourceUsecase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing likes.
 */
@RestController
@RequestMapping("/api/v1/like")
@Tag(name = "Like", description = "Operations related to likes")
public class LikeController {

    private final LikeResourceUsecase likeResourceUsecase;
    private final UnlikeResourceUsecase unlikeResourceUsecase;

    /**
     * Constructs a new LikeController with the specified use cases.
     *
     * @param likeResourceUsecase the use case for liking a topic
     * @param unlikeResourceUsecase the use case for unliking a topic
     */
    public LikeController(LikeResourceUsecase likeResourceUsecase, UnlikeResourceUsecase unlikeResourceUsecase) {
        this.likeResourceUsecase = likeResourceUsecase;
        this.unlikeResourceUsecase = unlikeResourceUsecase;
    }

    /**
     * Endpoint for liking a topic.
     *
     * @param requestDTO the data transfer object containing the like request data
     * @return a ResponseEntity with HTTP status 200 (OK)
     */
    @PostMapping
    public ResponseEntity<Void> like(@Valid @RequestBody LikeRequestDTO requestDTO) {

        likeResourceUsecase.execute(requestDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for unliking a topic.
     *
     * @param id the unique identifier of the like to be removed
     * @return a ResponseEntity with HTTP status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unlike(@PathVariable UUID id) {

        unlikeResourceUsecase.execute(id);
        return ResponseEntity.ok().build();
    }
}
