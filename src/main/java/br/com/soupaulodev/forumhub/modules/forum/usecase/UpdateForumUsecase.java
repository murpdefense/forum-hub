package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UpdateForumUsecase {

    private final ForumRepository forumRepository;

    public UpdateForumUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public ForumEntity execute(UUID id, ForumEntity forumEntity) {
        ForumEntity forumDB = forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);

        if(forumDB.equals(forumEntity)) {
            throw new ForumIllegalArgumentException("Data is the same");
        }
        if((forumEntity.getName() == null || forumEntity.getName().isEmpty())
                && (forumEntity.getDescription() == null || forumEntity.getDescription().isEmpty())) {
            throw new ForumIllegalArgumentException("Name and description are empty");
        }

        forumDB.setName(forumEntity.getName());
        forumDB.setDescription(forumEntity.getDescription());
        forumDB.setUpdatedAt(Instant.now());

        return forumRepository.save(forumEntity);
    }
}
