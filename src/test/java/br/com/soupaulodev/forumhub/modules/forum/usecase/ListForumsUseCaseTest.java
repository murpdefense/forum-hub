package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
class ListForumsUseCaseTest {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private ForumMapper forumMapper;

    @InjectMocks
    private ListForumsUseCase listForumsUseCase;

    private ForumEntity forumEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        forumEntity = new ForumEntity();
        forumEntity.setId(UUID.randomUUID());
        forumEntity.setName("Test Forum");
        forumEntity.setDescription("A test forum description");
        forumEntity.setCreatedAt(Instant.now());
        forumEntity.setUpdatedAt(Instant.now());
    }

    @Test
    void execute_ShouldReturnListOfForums() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ForumEntity> forumPage = new PageImpl<>(List.of(forumEntity));

        when(forumRepository.findAll(pageable)).thenReturn(forumPage);

        ForumResponseDTO responseMock = new ForumResponseDTO(
                forumEntity.getId(),
                forumEntity.getName(),
                forumEntity.getDescription(),
                UUID.randomUUID(),
                0L,
                10,
                5L,
                forumEntity.getCreatedAt(),
                forumEntity.getUpdatedAt()
        );

        when(forumMapper.toResponseDTO(forumEntity)).thenReturn(responseMock);

        List<ForumResponseDTO> responseList = listForumsUseCase.execute(0, 10);

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals("Test Forum", responseList.getFirst().name());

        verify(forumRepository).findAll(pageable);
        verify(forumMapper).toResponseDTO(forumEntity);
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenNoForumsExist() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ForumEntity> emptyPage = new PageImpl<>(List.of());

        when(forumRepository.findAll(pageable)).thenReturn(emptyPage);

        List<ForumResponseDTO> responseList = listForumsUseCase.execute(0, 10);

        assertNotNull(responseList);
        assertTrue(responseList.isEmpty());

        verify(forumRepository).findAll(pageable);
        verify(forumMapper, never()).toResponseDTO(any());
    }
}
