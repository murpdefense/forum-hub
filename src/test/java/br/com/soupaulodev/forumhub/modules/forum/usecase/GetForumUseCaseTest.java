package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
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
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
class GetForumUseCaseTest {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private ForumMapper forumMapper;

    @InjectMocks
    private GetForumUseCase getForumUseCase;

    private UUID forumId;
    private ForumEntity mockForum;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forumId = UUID.randomUUID();

        mockForum = new ForumEntity();
        mockForum.setId(forumId);
        mockForum.setName("Test Forum");
        mockForum.setDescription("A test forum description");
        mockForum.setCreatedAt(Instant.now());
        mockForum.setUpdatedAt(Instant.now());
    }

    @Test
    void execute_ShouldRetrieveForumSuccessfully() {
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(mockForum));

        ForumResponseDTO responseMock = new ForumResponseDTO(
                forumId,
                "Test Forum",
                "A test forum description",
                UUID.randomUUID(),
                0L,
                10,
                5L,
                Instant.now(),
                Instant.now()
        );
        when(forumMapper.toResponseDTO(mockForum)).thenReturn(responseMock);

        ForumResponseDTO responseDTO = getForumUseCase.execute(forumId);
        assertNotNull(responseDTO);
        assertEquals("Test Forum", responseDTO.name());
        assertEquals("A test forum description", responseDTO.description());

        verify(forumRepository).findById(forumId);
        verify(forumMapper).toResponseDTO(mockForum);
    }

    @Test
    void execute_ShouldThrowResourceNotFoundException_WhenForumNotFound() {
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> getForumUseCase.execute(forumId));

        verify(forumRepository).findById(forumId);
        verify(forumMapper, never()).toResponseDTO(any());
    }
}
