package br.com.soupaulodev.forumhub.modules.forum.mapper;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForumMapperTest {

    private ForumMapper forumMapper;

    @BeforeEach
    void setUp() {
        forumMapper = new ForumMapper();
    }

    @Test
    void shouldConvertForumCreateRequestDTOToForumEntity() {
        UserEntity owner = new UserEntity(
                "Name Example",
                "usernameexample",
                "email@example.mail",
                "password");
        ForumCreateRequestDTO dto = new ForumCreateRequestDTO(
                "Forum Example",
                "This is a example forum.");
        ForumEntity forumEntity = forumMapper.toEntity(dto, owner);

        assertInstanceOf(ForumEntity.class, forumEntity);
        assertEquals(dto.name(), forumEntity.getName());
        assertEquals(dto.description(), forumEntity.getDescription());
        assertEquals(owner, forumEntity.getOwner());
        assertEquals(owner.getId(), forumEntity.getOwner().getId());
    }


    @Test
    void shouldThrowExceptionWhenOwnerIsNull() {
        ForumCreateRequestDTO dto = new ForumCreateRequestDTO(
                "Forum Example",
                "This is a example forum.");

        assertThrows(IllegalArgumentException.class, () -> forumMapper.toEntity(dto, null));
    }

    @Test
    void shouldThrowExceptionWhenForumNameOrDescriptionIsEmpty() {
        UserEntity owner = new UserEntity(
                "Name Example",
                "usernameexample",
                "email@example.mail",
                "password");
        ForumCreateRequestDTO dto = new ForumCreateRequestDTO(
                "",
                "");

        assertThrows(IllegalArgumentException.class, () -> forumMapper.toEntity(dto, owner));
    }

    @Test
    void shouldThrowExceptionWhenForumNameOrDescriptionIsBlank() {
        UserEntity owner = new UserEntity(
                "Name Example",
                "usernameexample",
                "email@example.mail",
                "password");
        ForumCreateRequestDTO dto = new ForumCreateRequestDTO(
                " ",
                " ");

        assertThrows(IllegalArgumentException.class, () -> forumMapper.toEntity(dto, owner));
    }

    @Test
    void shouldConvertForumUpdateRequestDTOToForumEntity() {
        UserEntity owner = new UserEntity(
                "Name Example",
                "usernameexample",
                "email@example.mail",
                "password");
        ForumUpdateRequestDTO dto = new ForumUpdateRequestDTO(
                "Forum Example",
                "This is a example forum.");
        ForumEntity forumEntity = forumMapper.toEntity(dto, owner);

        assertInstanceOf(ForumEntity.class, forumEntity);
        assertEquals(dto.name(), forumEntity.getName());
        assertEquals(dto.description(), forumEntity.getDescription());
        assertEquals(owner, forumEntity.getOwner());
        assertEquals(owner.getId(), forumEntity.getOwner().getId());
    }

    @Test
    void shouldConvertForumEntityToResponseDTO() {
        UserEntity owner = new UserEntity(
                "Name Example",
                "usernameexample",
                "email@example.mail",
                "password");

        ForumEntity forumEntity = new ForumEntity(
                "Forum Example",
                "This is a example forum.",
                owner);

        ForumResponseDTO dto = forumMapper.toResponseDTO(forumEntity);

        assertInstanceOf(ForumResponseDTO.class, dto);
        assertEquals(forumEntity.getName(), dto.name());
        assertEquals(forumEntity.getDescription(), dto.description());
        assertEquals(owner.getId(), dto.owner());
    }

    @Test
    void shouldThrowExceptionWhenForumEntityIsNull() {
        assertThrows(IllegalArgumentException.class, () -> forumMapper.toResponseDTO(null));
    }

    @Test
    void shouldThrowExceptionWhenForumOwnerIsNull() {
        ForumEntity forumEntity = new ForumEntity(
                "Forum Example",
                "This is a example forum.",
                null);

        assertThrows(IllegalArgumentException.class, () -> forumMapper.toResponseDTO(forumEntity));
    }
}