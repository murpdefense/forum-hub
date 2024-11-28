package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListForumsPageableUsecase {

    private final ForumRepository forumRepository;

    public ListForumsPageableUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public List<ForumResponseDTO> execute(int page) {

        List<ForumEntity> entities = forumRepository.findAllOrderByCreatedAt(Pageable.ofSize(10).withPage(page));

        return entities
                .stream()
                .map(ForumMapper::toResponseDTO)
                .toList();
    }
}
