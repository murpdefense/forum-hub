package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
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

    public List<ForumResponseDTO> execute(String name, int page) {

        List<ForumEntity> entities = forumRepository.findAllByNameContainingOrderByCreatedAtDesc(name, Pageable.ofSize(10).withPage(page));

        return entities
                .stream()
                .map(ForumMapper::toResponseDTO)
                .toList();
    }
}
