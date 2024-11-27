package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UpdateTopicUsecase {

    private final TopicRepository topicRepository;

    public UpdateTopicUsecase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicResponseDTO execute(UUID id, TopicUpdateRequestDTO requestDTO) {

        TopicEntity topicDB = topicRepository.findById(id)
                .orElseThrow(TopicNotFoundException::new);

        if (requestDTO.getTitle() == null && requestDTO.getContent() == null) {
            throw new TopicIllegalArgumentException("""
                    You must provide at least one field to update:
                    - title
                    - content
                    """);
        }

        if (requestDTO.getTitle() != null) {
            topicDB.setTitle(requestDTO.getTitle());
        }
        if (requestDTO.getContent() != null) {
            topicDB.setContent(requestDTO.getContent());
        }
        topicDB.setUpdatedAt(Instant.now());

        TopicEntity updatedTopic = topicRepository.save(topicDB);
        return TopicMapper.toResponseDTO(updatedTopic);
    }
}
