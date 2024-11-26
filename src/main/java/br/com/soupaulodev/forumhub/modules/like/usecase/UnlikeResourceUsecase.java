package br.com.soupaulodev.forumhub.modules.like.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.LikeNotFoundException;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.like.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UnlikeResourceUsecase {

    private LikeRepository likeRepository;

    public UnlikeResourceUsecase(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void execute(UUID likeId) {

        LikeEntity likeDB = likeRepository.findById(likeId)
                .orElseThrow(LikeNotFoundException::new);
        likeRepository.delete(likeDB);
    }
}
