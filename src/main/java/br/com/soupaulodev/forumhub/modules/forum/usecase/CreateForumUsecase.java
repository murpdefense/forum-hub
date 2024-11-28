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

/**
 * Use case for creating a new forum.
 */
@Service
public class CreateForumUsecase {

    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new CreateForumUsecase with the specified repositories.
     *
     * @param forumRepository the repository for managing forums
     * @param userRepository the repository for managing users
     */
    public CreateForumUsecase(ForumRepository forumRepository,
                              UserRepository userRepository) {
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to create a new forum.
     *
     * @param requestDTO the data transfer object containing the forum creation data
     * @return the response data transfer object containing the created forum data
     * @throws ForumAlreadyExistsException if a forum with the same name already exists
     */
    public ForumResponseDTO execute(ForumCreateRequestDTO requestDTO) {

        forumRepository.findByName(requestDTO.getName()).ifPresent(forum -> {
            throw new ForumAlreadyExistsException();
        });

        UserEntity owner = userRepository.findById(requestDTO.getOwnerId()).orElseThrow();

        ForumEntity forumFound =  forumRepository.save(ForumMapper.toEntity(requestDTO, owner));
        return ForumMapper.toResponseDTO(forumFound);
    }
}
