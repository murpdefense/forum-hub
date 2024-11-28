package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UpdateForumUsecase {

    private final ForumRepository forumRepository;

    public UpdateForumUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public ForumResponseDTO execute(UUID id, ForumUpdateRequestDTO requestDTO) {
        ForumEntity forumFound = forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);

        if(requestDTO.getName().equals(forumFound.getName())
                && requestDTO.getDescription().equals(forumFound.getDescription())) {
            throw new ForumIllegalArgumentException("Data is the same");
        }

        if((requestDTO.getName().isEmpty()) || requestDTO.getDescription().isEmpty()) {
            throw new ForumIllegalArgumentException("Name and description are empty");
        }

        forumFound.setName(requestDTO.getName());
        forumFound.setDescription(requestDTO.getDescription());
        forumFound.setUpdatedAt(Instant.now());

        ForumEntity forumUpdated = forumRepository.save(forumFound);
        return ForumMapper.toResponseDTO(forumUpdated);
    }
}
