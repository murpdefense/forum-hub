package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateForumUsecase {

    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    public CreateForumUsecase(ForumRepository forumRepository,
                              UserRepository userRepository) {
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    public ForumResponseDTO execute(ForumCreateRequestDTO requestDTO) {

        forumRepository.findByName(requestDTO.getName()).ifPresent(forum -> {
            throw new ForumAlreadyExistsException();
        });

        UserEntity owner = userRepository.findById(requestDTO.getOwnerId()).orElseThrow();

        ForumEntity forumFound =  forumRepository.save(ForumMapper.toEntity(requestDTO, owner));
        return ForumMapper.toResponseDTO(forumFound);
    }
}
