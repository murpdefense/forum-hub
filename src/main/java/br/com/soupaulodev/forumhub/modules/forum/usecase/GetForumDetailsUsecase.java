package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetForumDetailsUsecase {

    private final ForumRepository forumRepository;

    public GetForumDetailsUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public ForumEntity execute(UUID id) {
        return forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);
    }
}
