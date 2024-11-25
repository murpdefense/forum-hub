package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateForumUsecase {

    private final ForumRepository forumRepository;

    public CreateForumUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public ForumEntity execute(ForumEntity forumEntity) {
        forumRepository.findByName(forumEntity.getName()).ifPresent(forum -> {
            throw new ForumAlreadyExistsException();
        });
        return forumRepository.save(forumEntity);
    }
}
