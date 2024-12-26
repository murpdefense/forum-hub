package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
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
class CreateForumUseCaseTest {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ForumMapper forumMapper;

    @InjectMocks
    private CreateForumUseCase createForumUseCase;

    private UUID authenticatedUserId;
    private UserEntity mockUser;
    private ForumCreateRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticatedUserId = UUID.randomUUID();
        mockUser = new UserEntity();
        mockUser.setId(authenticatedUserId);

        requestDTO = new ForumCreateRequestDTO("Test Forum", "A test forum description");
    }

    @Test
    void execute_ShouldCreateForumSuccessfully() {
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(mockUser));
        when(forumRepository.existsByName(requestDTO.name())).thenReturn(false);

        ForumEntity forumEntity = new ForumEntity();
        when(forumMapper.toEntity(requestDTO, mockUser)).thenReturn(forumEntity);
        ForumResponseDTO responseMock = new ForumResponseDTO(
                UUID.randomUUID(),
                "Test Forum",
                "A test forum description",
                authenticatedUserId,
                0L,
                1,
                0L,
                Instant.now(),
                Instant.now()
        );
        when(forumMapper.toResponseDTO(forumEntity)).thenReturn(responseMock);

        ForumResponseDTO responseDTO = createForumUseCase.execute(requestDTO, authenticatedUserId);

        assertNotNull(responseDTO);
        assertEquals("Test Forum", responseDTO.name());
        assertEquals("A test forum description", responseDTO.description());

        verify(userRepository).findById(authenticatedUserId);
        verify(forumRepository).existsByName(requestDTO.name());
        verify(userRepository).save(mockUser);
    }

    @Test
    void execute_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> createForumUseCase.execute(requestDTO, authenticatedUserId));

        verify(userRepository).findById(authenticatedUserId);
        verify(forumRepository, never()).existsByName(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void execute_ShouldThrowResourceAlreadyExistsException_WhenForumAlreadyExists() {
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(mockUser));
        when(forumRepository.existsByName(requestDTO.name())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> createForumUseCase.execute(requestDTO, authenticatedUserId));

        verify(userRepository).findById(authenticatedUserId);
        verify(forumRepository).existsByName(requestDTO.name());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
