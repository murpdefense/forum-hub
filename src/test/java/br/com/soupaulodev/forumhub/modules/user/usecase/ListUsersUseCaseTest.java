package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class ListUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListUsersUseCase listUsersUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_shouldReturnListOfUsers() {
        UserEntity userEntity = new UserEntity(
                "John Doe",
                "johndoe",
                "johndoe@mail.com",
                "password");

        Page<UserEntity> userEntities = new PageImpl<>(List.of(userEntity, userEntity, userEntity));

        List<UserResponseDTO> usersDTOs = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            usersDTOs.add(UserMapper.toResponseDTO(userEntities.getContent().get(i)));
        }

        int pageNumber = 0;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        when(userRepository.findAll(pageable)).thenReturn(userEntities);

        List<UserResponseDTO> result = listUsersUseCase.execute(pageNumber, pageSize);

        assertEquals(usersDTOs, result);
        assertEquals(usersDTOs.size(), result.size());
    }

    @Test
    void testExecute_shouldReturnEmptyList() {
        Page<UserEntity> userEntities = new PageImpl<>(List.of());

        int pageNumber = 0;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        when(userRepository.findAll(pageable)).thenReturn(userEntities);

        List<UserResponseDTO> result = listUsersUseCase.execute(pageNumber, pageSize);

        assertEquals(0, result.size());
    }

    @Test
    void testExecute_shouldThrowIllegalArgumentException_whenPageIsNegative() {
        int pageNumber = -1;
        int pageSize = 3;

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> listUsersUseCase.execute(pageNumber, pageSize));

        assertEquals("Page and size must be positive numbers.", exception.getMessage());
    }

    @Test
    void testExecute_shouldThrowIllegalArgumentException_whenSizeIsZero() {
        int pageNumber = 0;
        int pageSize = 0;

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> listUsersUseCase.execute(pageNumber, pageSize));

        assertEquals("Page and size must be positive numbers.", exception.getMessage());
    }
}