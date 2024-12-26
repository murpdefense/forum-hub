package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumHighsEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumHighsRepository;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
class UnHighForumUseCaseTest {

    private ForumHighsRepository forumHighsRepository;
    private ForumRepository forumRepository;
    private UnHighForumUseCase unHighForumUseCase;

    private UUID forumId;
    private UUID userId;
    private ForumEntity forumEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        forumHighsRepository = mock(ForumHighsRepository.class);
        forumRepository = mock(ForumRepository.class);
        unHighForumUseCase = new UnHighForumUseCase(forumHighsRepository, forumRepository);

        forumId = UUID.randomUUID();
        userId = UUID.randomUUID();
        userEntity = new UserEntity();
        forumEntity = new ForumEntity("Forum", "Description", userEntity);
    }

    @Test
    void shouldUnHighForumSuccessfully() {
        forumEntity.incrementHighs();
        ForumHighsEntity forumHighsEntity = new ForumHighsEntity(forumEntity, userEntity);
        when(forumHighsRepository.findByForum_IdAndUser_Id(forumId, userId)).thenReturn(Optional.of(forumHighsEntity));
        when(forumRepository.findById(forumId)).thenReturn(Optional.of(forumEntity));

        unHighForumUseCase.execute(forumId, userId);

        verify(forumHighsRepository, times(1)).delete(forumHighsEntity);
        verify(forumRepository, times(1)).save(forumEntity);
        assertEquals(0, forumEntity.getHighsCount());
    }

    @Test
    void shouldThrowExceptionIfForumIsNotHighed() {
        when(forumHighsRepository.findByForum_IdAndUser_Id(forumId, userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> unHighForumUseCase.execute(forumId, userId));

        assertEquals("Forum not highed", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfForumDoesNotExist() {
        when(forumHighsRepository.findByForum_IdAndUser_Id(forumId, userId)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> unHighForumUseCase.execute(forumId, userId));
    }
}
