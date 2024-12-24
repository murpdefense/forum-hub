package br.com.soupaulodev.forumhub.modules.user.mapper;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserDetailsResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserMapperTest {

    private UUID userId;
    private UserCreateRequestDTO createRequestDTO;
    private UserUpdateRequestDTO updateRequestDTO;
    private UserEntity userEntity;
    private UserResponseDTO responseDTO;
    private UserDetailsResponseDTO detailsResponseDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        createRequestDTO = new UserCreateRequestDTO(
                "John Doe",
                "johndoe",
                "johndoe@mail.com",
                "password");
        updateRequestDTO = new UserUpdateRequestDTO(
                "John Doe Updated",
                "johndoeupdated",
                "johndoeupdated@mail.com",
                "newpassword");
        userEntity = new UserEntity(
                "John Doe",
                "johndoe",
                "johndoe@mail.com",
                "password");
        userEntity.setId(userId);
        Instant now = Instant.now();
        responseDTO = new UserResponseDTO(
                userId,
                "John Doe",
                "johndoe",
                0L,
                now,
                now);
        detailsResponseDTO = new UserDetailsResponseDTO(
                userId,
                "John Doe",
                "johndoe",
                "johndoe@mail.com",
                List.of(),
                List.of(),
                0L,
                now,
                now);
    }

    @AfterEach
    void tearDown() {
        userId = null;
        createRequestDTO = null;
        updateRequestDTO = null;
        userEntity = null;
        responseDTO = null;
    }

    @Test
    void testToEntity_shouldConvertUserCreateRequestDTOToUserEntity() {
        UserEntity result = UserMapper.toEntity(createRequestDTO);
        result.setId(userId);
        assertEquals(userEntity, result);
    }

    @Test
    void testToEntity_shouldConvertUserUpdateRequestDTOToUserEntity() {
        UserEntity result = UserMapper.toEntity(updateRequestDTO);
        result.setId(userId);
        assertEquals(userEntity, result);
    }

    @Test
    void testToResponseDTO_shouldConvertUserEntityToUserResponseDTO() {
        UserResponseDTO result = UserMapper.toResponseDTO(userEntity);

        assertEquals(responseDTO.id(), result.id());
        assertEquals(responseDTO.name(), result.name());
        assertEquals(responseDTO.username(), result.username());
        assertEquals(responseDTO.highs(), result.highs());
        assertEquals(responseDTO.createdAt().getLong(ChronoField.INSTANT_SECONDS), result.createdAt().getLong(ChronoField.INSTANT_SECONDS));
        assertNotEquals(responseDTO.updatedAt(), result.updatedAt());
    }

    @Test
    void testToResponseDTO_shouldConvertUserEntityToDetailedUserResponseDTO() {
        UserDetailsResponseDTO result = UserMapper.toDetailsResponseDTO(userEntity);

        assertEquals(detailsResponseDTO.id(), result.id());
        assertEquals(detailsResponseDTO.name(), result.name());
        assertEquals(detailsResponseDTO.username(), result.username());
        assertEquals(detailsResponseDTO.email(), result.email());
        assertEquals(detailsResponseDTO.ownerOf(), result.ownerOf());
        assertEquals(detailsResponseDTO.participatesIn(), result.participatesIn());
        assertEquals(detailsResponseDTO.highs(), result.highs());
        assertEquals(detailsResponseDTO.createdAt().getLong(ChronoField.INSTANT_SECONDS), result.createdAt().getLong(ChronoField.INSTANT_SECONDS));
        assertNotEquals(detailsResponseDTO.updatedAt(), result.updatedAt());
    }
}