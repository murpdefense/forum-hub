package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTopicUsecase {

    private final TopicRepository topicRepository;

    public CreateTopicUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicEntity execute(TopicEntity topicEntity) {
        return topicRepository.save(topicEntity);
    }
}
