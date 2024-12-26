package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://soupaulode.com.br>soupaulodev</a>
 */
class UpdateForumUseCaseTest {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private ForumMapper forumMapper;

    @InjectMocks
    private UpdateForumUseCase updateForumUseCase;

    private ForumEntity forumEntity;
    private UUID authenticatedUserId;
    private UUID forumId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticatedUserId = UUID.randomUUID();
        forumId = UUID.randomUUID();

        forumEntity = new ForumEntity();
        forumEntity.setId(forumId);
        forumEntity.setName("Original Name");
        forumEntity.setDescription("Original Description");
        forumEntity.setCreatedAt(Instant.now());
        forumEntity.setUpdatedAt(Instant.now());
        UserEntity owner = new UserEntity();
        owner.setId(authenticatedUserId);
        forumEntity.addOwner(owner);
    }

    @Test
    void execute_ShouldUpdateForumSuccessfully() {
        ForumUpdateRequestDTO updateRequest = new ForumUpdateRequestDTO("Updated Name", "Updated Description");
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));

        ForumResponseDTO responseMock = new ForumResponseDTO(
                forumId,
                "Updated Name",
                "Updated Description",
                authenticatedUserId,
                0L,
                10,
                5L,
                forumEntity.getCreatedAt(),
                Instant.now()
        );
        when(forumMapper.toResponseDTO(any())).thenReturn(responseMock);
        when(forumRepository.save(any())).thenReturn(forumEntity);

        ForumResponseDTO responseDTO = updateForumUseCase.execute(forumId, updateRequest, authenticatedUserId);

        assertNotNull(responseDTO);
        assertEquals("Updated Name", responseDTO.name());
        assertEquals("Updated Description", responseDTO.description());

        verify(forumRepository).findById(forumId);
        verify(forumRepository).save(forumEntity);
        verify(forumMapper).toResponseDTO(forumEntity);
    }

    @Test
    void execute_ShouldThrowResourceNotFoundException_WhenForumNotFound() {
        ForumUpdateRequestDTO updateRequest = new ForumUpdateRequestDTO("Updated Name", "Updated Description");
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                updateForumUseCase.execute(forumId, updateRequest, authenticatedUserId));

        verify(forumRepository).findById(forumId);
        verify(forumRepository, never()).save(any());
        verify(forumMapper, never()).toResponseDTO(any());
    }

    @Test
    void execute_ShouldThrowForbiddenException_WhenUserIsNotOwner() {
        ForumUpdateRequestDTO updateRequest = new ForumUpdateRequestDTO("Updated Name", "Updated Description");
        forumEntity.addOwner(new UserEntity());
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));

        assertThrows(ForbiddenException.class, () ->
                updateForumUseCase.execute(forumId, updateRequest, authenticatedUserId));

        verify(forumRepository).findById(forumId);
        verify(forumRepository, never()).save(any());
        verify(forumMapper, never()).toResponseDTO(any());
    }

    @Test
    void execute_ShouldThrowIllegalArgumentException_WhenRequestIsInvalid() {
        ForumUpdateRequestDTO invalidRequest = new ForumUpdateRequestDTO(null, null);
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));

        assertThrows(IllegalArgumentException.class, () ->
                updateForumUseCase.execute(forumId, invalidRequest, authenticatedUserId));

        verify(forumRepository).findById(forumId);
        verify(forumRepository, never()).save(any());
        verify(forumMapper, never()).toResponseDTO(any());
    }
}
