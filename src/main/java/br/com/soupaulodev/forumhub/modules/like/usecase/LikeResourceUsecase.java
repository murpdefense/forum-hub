package br.com.soupaulodev.forumhub.modules.like.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.LikeAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.like.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeResourceUsecase {

    private final LikeRepository likeRepository;

    public LikeResourceUsecase(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void execute(LikeEntity likeEntity) {
        likeRepository.findById(likeEntity.getId())
                .ifPresent(like -> {
                    throw new LikeAlreadyExistsException();
                });
        likeRepository.save(likeEntity);
    }
}
