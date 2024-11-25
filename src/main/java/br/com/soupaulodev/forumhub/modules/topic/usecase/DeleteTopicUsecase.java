package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteTopicUsecase {

    private final TopicRepository topicRepository;

    public DeleteTopicUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void execute(UUID id) {
        topicRepository.deleteById(id);
    }
}
