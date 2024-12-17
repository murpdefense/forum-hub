package br.com.soupaulodev.forumhub.modules.like.controller;

import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeRequestDTO;
import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;
import br.com.soupaulodev.forumhub.modules.like.usecase.DislikeResourceUsecase;
import br.com.soupaulodev.forumhub.modules.like.usecase.LikeResourceUsecase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LikeControllerTest {

    @Mock
    private LikeResourceUsecase likeResourceUsecase;

    @Mock
    private DislikeResourceUsecase dislikeResourceUsecase;

    @InjectMocks
    private LikeController likeController;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void likeResource_shouldReturnNoContent() {
        LikeRequestDTO likeRequestDTO = new LikeRequestDTO(ResourceType.TOPIC, UUID.randomUUID().toString());

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        doNothing().when(likeResourceUsecase).execute(any(LikeRequestDTO.class), eq(userId));

        ResponseEntity<Void> response = likeController.likeResource(likeRequestDTO);

        assertEquals(204, response.getStatusCode().value());
        verify(likeResourceUsecase).execute(likeRequestDTO, userId);
    }

    @Test
    void unlikeResource_shouldReturnNoContent() {
        LikeRequestDTO likeRequestDTO = new LikeRequestDTO(ResourceType.TOPIC, UUID.randomUUID().toString());

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        doNothing().when(dislikeResourceUsecase).execute(any(LikeRequestDTO.class), eq(userId));

        ResponseEntity<Void> response = likeController.dislike(likeRequestDTO);

        assertEquals(204, response.getStatusCode().value());
        verify(dislikeResourceUsecase).execute(likeRequestDTO, userId);
    }
}
