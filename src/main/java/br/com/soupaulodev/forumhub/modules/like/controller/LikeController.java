package br.com.soupaulodev.forumhub.modules.like.controller;

import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeRequestDTO;
import br.com.soupaulodev.forumhub.modules.like.mapper.LikeMapper;
import br.com.soupaulodev.forumhub.modules.like.usecase.LikeResourceUsecase;
import br.com.soupaulodev.forumhub.modules.like.usecase.UnlikeResourceUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeResourceUsecase likeResourceUsecase;
    private final UnlikeResourceUsecase unlikeResourceUsecase;

    public LikeController(LikeResourceUsecase likeResourceUsecase, UnlikeResourceUsecase unlikeResourceUsecase) {
        this.likeResourceUsecase = likeResourceUsecase;
        this.unlikeResourceUsecase = unlikeResourceUsecase;
    }

    @PostMapping
    public ResponseEntity<Void> like(@Valid @RequestBody LikeRequestDTO likeRequestDTO) {
        likeResourceUsecase.execute(LikeMapper.toEntity(likeRequestDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unlike(@PathVariable UUID id) {
        unlikeResourceUsecase.execute(id);
        return ResponseEntity.ok().build();
    }
}
