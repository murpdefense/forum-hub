package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRecentTopicsUsecase {

    private final TopicRepository topicRepository;

    public GetRecentTopicsUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<TopicEntity> execute(int page) {
        return topicRepository.findAllByOrderByCreatedAtDesc(Pageable.ofSize(10).withPage(page));
    }
}
