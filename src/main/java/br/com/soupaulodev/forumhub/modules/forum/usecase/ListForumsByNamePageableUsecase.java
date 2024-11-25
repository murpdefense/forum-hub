package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListForumsByNamePageableUsecase {

    private final ForumRepository forumRepository;

    public ListForumsByNamePageableUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public List<ForumEntity> execute(String name, int page) {
        return forumRepository.findAllByNameContainingOrderByCreatedAtDesc(name, Pageable.ofSize(10).withPage(page));
    }
}
