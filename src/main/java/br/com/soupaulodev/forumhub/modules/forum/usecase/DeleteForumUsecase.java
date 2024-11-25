package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteForumUsecase {

    private final ForumRepository forumRepository;

    public DeleteForumUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public void execute(UUID id) {
        ForumEntity forumDB = forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);
        forumRepository.delete(forumDB);
    }
}
