package br.com.soupaulodev.forumhub.modules.comment.controller;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.usecase.CreateCommentUsecase;
import br.com.soupaulodev.forumhub.modules.comment.usecase.DeleteCommentUsecase;
import br.com.soupaulodev.forumhub.modules.comment.usecase.UpdateCommentUsecase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @Mock
    private CreateCommentUsecase createCommentUsecase;

    @Mock
    private UpdateCommentUsecase updateCommentUsecase;

    @Mock
    private DeleteCommentUsecase deleteCommentUsecase;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createComment_Success() {
        UUID userId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        UUID topicId = UUID.randomUUID();
        CommentCreateRequestDTO requestDTO = new CommentCreateRequestDTO("Content", userId.toString(), topicId.toString(), null);
        CommentResponseDTO responseDTO = new CommentResponseDTO(
                commentId,
                "Content",
                userId,
                topicId,
                null,
                null,
                null,
                null
        );

        when(authentication.getPrincipal()).thenReturn(userId.toString());
        when(createCommentUsecase.execute(requestDTO, userId)).thenReturn(responseDTO);

        ResponseEntity<CommentResponseDTO> response = commentController.createComment(requestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        verify(createCommentUsecase, times(1)).execute(requestDTO, userId);
    }

    @Test
    void updateComment_Success() {
        UUID userId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        UUID topicId = UUID.randomUUID();
        CommentUpdateRequestDTO requestDTO = new CommentUpdateRequestDTO("Content Updated");
        CommentResponseDTO responseDTO = new CommentResponseDTO(
                commentId,
                "Content",
                userId,
                topicId,
                null,
                null,
                null,
                null
        );

        when(authentication.getPrincipal()).thenReturn(userId.toString());
        when(updateCommentUsecase.execute(commentId, requestDTO, userId)).thenReturn(responseDTO);

        ResponseEntity<CommentResponseDTO> response = commentController.updateComment(commentId.toString(), requestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        verify(updateCommentUsecase, times(1)).execute(commentId, requestDTO, userId);
    }

    @Test
    void deleteComment_Success() {
        UUID userId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();

        when(authentication.getPrincipal()).thenReturn(userId.toString());

        ResponseEntity<Void> response = commentController.deleteComment(commentId.toString());

        assertEquals(204, response.getStatusCode().value());
        verify(deleteCommentUsecase, times(1)).execute(commentId, userId);
    }
}
