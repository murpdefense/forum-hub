package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTopicsUsecase {

    private final TopicRepository topicRepository;

    public SearchTopicsUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<TopicResponseDTO> execute(String query, int page) {

        List<TopicEntity> entities = topicRepository
                .findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(
                        query,
                        query,
                        Pageable.ofSize(10).withPage(page));

        return entities
                .stream().map(TopicMapper::toResponseDTO).toList();
    }
}
