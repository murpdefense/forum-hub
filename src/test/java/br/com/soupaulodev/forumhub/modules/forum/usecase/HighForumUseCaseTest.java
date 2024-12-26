package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumHighsEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumHighsRepository;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://soupaulode.com.br>soupaulodev</a>
 */
class HighForumUseCaseTest {

    @Mock
    private ForumHighsRepository forumHighsRepository;

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HighForumUseCase highForumUseCase;

    private UUID forumId;
    private UUID userId;
    private ForumEntity forumEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forumId = UUID.randomUUID();
        userId = UUID.randomUUID();

        forumEntity = new ForumEntity();
        forumEntity.setId(forumId);

        userEntity = new UserEntity();
        userEntity.setId(userId);
    }

    @Test
    void execute_ShouldHighForumSuccessfully() {
        // Arrange
        when(forumHighsRepository.findByForum_IdAndUser_Id(forumId, userId)).thenReturn(Optional.empty());
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // Act
        highForumUseCase.execute(forumId, userId);

        // Assert
        verify(forumHighsRepository).findByForum_IdAndUser_Id(forumId, userId);
        verify(forumRepository).findById(forumId);
        verify(userRepository).findById(userId);
        verify(forumHighsRepository).save(any(ForumHighsEntity.class));
        assertEquals(1L, forumEntity.getHighsCount());
    }

    @Test
    void execute_ShouldThrowException_WhenForumAlreadyHighed() {
        // Arrange
        when(forumHighsRepository.findByForum_IdAndUser_Id(forumId, userId))
                .thenReturn(Optional.of(new ForumHighsEntity()));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                highForumUseCase.execute(forumId, userId)
        );

        assertEquals("Forum already highed", exception.getMessage());
        verify(forumHighsRepository).findByForum_IdAndUser_Id(forumId, userId);
        verify(forumRepository, never()).findById(any());
        verify(userRepository, never()).findById(any());
        verify(forumHighsRepository, never()).save(any());
    }

    @Test
    void execute_ShouldThrowException_WhenForumNotFound() {
        // Arrange
        when(forumHighsRepository.findByForum_IdAndUser_Id(forumId, userId)).thenReturn(Optional.empty());
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                highForumUseCase.execute(forumId, userId)
        );

        assertEquals("Forum not found", exception.getMessage());
        verify(forumHighsRepository).findByForum_IdAndUser_Id(forumId, userId);
        verify(forumRepository).findById(forumId);
        verify(userRepository, never()).findById(any());
        verify(forumHighsRepository, never()).save(any());
    }

    @Test
    void execute_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(forumHighsRepository.findByForum_IdAndUser_Id(forumId, userId)).thenReturn(Optional.empty());
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                highForumUseCase.execute(forumId, userId)
        );

        assertEquals("Forum not found", exception.getMessage());
        verify(forumHighsRepository).findByForum_IdAndUser_Id(forumId, userId);
        verify(forumRepository).findById(forumId);
        verify(userRepository).findById(userId);
        verify(forumHighsRepository, never()).save(any());
    }
}
