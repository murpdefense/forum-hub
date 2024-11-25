package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetTopicUsecase {

    private final TopicRepository topicRepository;

    public GetTopicUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicEntity execute(UUID id) {
        return topicRepository.findById(id)
                .orElseThrow(TopicNotFoundException::new);
    }
}
