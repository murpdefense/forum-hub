package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
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

    public List<TopicEntity> execute(String query, int page) {

        return topicRepository
                .findAllByTitleContainingIgnoreCaseOrMessageContainingIgnoreCaseOrderByCreatedAtDesc(
                        query,
                        query,
                        Pageable.ofSize(10).withPage(page));
    }
}
