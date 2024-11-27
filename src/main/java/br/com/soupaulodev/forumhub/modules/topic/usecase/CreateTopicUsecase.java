package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTopicUsecase {

    private final TopicRepository topicRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    public CreateTopicUsecase(TopicRepository topicRepository,
                              ForumRepository forumRepository,
                              UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    public TopicResponseDTO execute(TopicCreateRequestDTO requestDTO) {

        ForumEntity forum = forumRepository.findById(requestDTO.getForumId())
                .orElseThrow(ForumNotFoundException::new);
        UserEntity creator = userRepository.findById(requestDTO.getCreatorId())
                .orElseThrow(UserNotFoundException::new);

        TopicEntity topicSaved = topicRepository.save(TopicMapper.toEntity(requestDTO, forum, creator));
        return TopicMapper.toResponseDTO(topicSaved);
    }
}
