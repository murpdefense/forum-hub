package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
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
class DeleteForumUseCaseTest {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteForumUseCase deleteForumUseCase;

    private ForumEntity forumEntity;
    private UserEntity userEntity;
    private UUID forumId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forumId = UUID.randomUUID();
        userId = UUID.randomUUID();

        userEntity = new UserEntity();
        userEntity.setId(userId);

        forumEntity = new ForumEntity();
        forumEntity.setId(forumId);
        forumEntity.addOwner(userEntity);
    }

    @Test
    void execute_ShouldDeleteForumSuccessfully() {
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        deleteForumUseCase.execute(forumId, userId);

        verify(forumRepository).findById(forumId);
        verify(userRepository).findById(userId);
        verify(forumRepository).delete(forumEntity);
        assertTrue(userEntity.getOwnedForums().isEmpty());
        assertTrue(userEntity.getParticipatingForums().isEmpty());
    }

    @Test
    void execute_ShouldThrowResourceNotFoundException_WhenForumNotFound() {
        when(forumRepository.findById(forumId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                deleteForumUseCase.execute(forumId, userId));

        verify(forumRepository).findById(forumId);
        verify(userRepository, never()).findById(any());
        verify(forumRepository, never()).delete(any());
    }

    @Test
    void execute_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                deleteForumUseCase.execute(forumId, userId));

        verify(forumRepository).findById(forumId);
        verify(userRepository).findById(userId);
        verify(forumRepository, never()).delete(any());
    }

    @Test
    void execute_ShouldThrowForbiddenException_WhenUserIsNotOwner() {
        UUID anotherUserId = UUID.randomUUID();
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));

        assertThrows(ForbiddenException.class, () ->
                deleteForumUseCase.execute(forumId, anotherUserId));

        verify(forumRepository).findById(forumId);
        verify(userRepository, never()).findById(any());
        verify(forumRepository, never()).delete(any());
    }
}
