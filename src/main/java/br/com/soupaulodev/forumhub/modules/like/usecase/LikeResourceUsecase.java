package br.com.soupaulodev.forumhub.modules.like.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.LikeAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeRequestDTO;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.like.mapper.LikeMapper;
import br.com.soupaulodev.forumhub.modules.like.repository.LikeRepository;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeResourceUsecase {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public LikeResourceUsecase(LikeRepository likeRepository,
                               UserRepository userRepository,
                               TopicRepository topicRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public void execute(LikeRequestDTO requestDTO) {

        UserEntity user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(UserNotFoundException::new);
        TopicEntity topic = topicRepository.findById(requestDTO.getTopicId())
                .orElseThrow(TopicNotFoundException::new);

        LikeEntity likeEntity = LikeMapper.toEntity(user, topic);
        likeRepository.findByUserAndTopic(user, topic)
                .ifPresent(like -> {
                    throw new LikeAlreadyExistsException();
                });

        likeRepository.save(likeEntity);
    }
}
