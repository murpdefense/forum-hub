package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateTopicUsecase {

    private final TopicRepository topicRepository;

    public UpdateTopicUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicEntity execute(UUID id, TopicEntity topic) {
        TopicEntity topicDB = topicRepository.findById(id)
                .orElseThrow(TopicNotFoundException::new);

        if (topic.getTitle() == null && topic.getMessage() == null) {
            throw new TopicIllegalArgumentException("""
                    You must provide at least one field to update:
                    - title
                    - message
                    """);
        }

        if (topic.getTitle() != null) {
            topicDB.setTitle(topic.getTitle());
        }
        if (topic.getMessage() != null) {
            topicDB.setMessage(topic.getMessage());
        }

        return topicRepository.save(topicDB);
    }
}
