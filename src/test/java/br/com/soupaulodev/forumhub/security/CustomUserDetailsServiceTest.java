package br.com.soupaulodev.forumhub.security;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private CustomUserDetailsService customUserDetailsService;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        jwtUtil = mock(JwtUtil.class);
        customUserDetailsService = new CustomUserDetailsService(userRepository, jwtUtil);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername("testuser");
        mockUser.setPassword("testpassword");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals("testuser", userDetails.getUsername(), "Username should match");
        assertEquals("testpassword", userDetails.getPassword(), "Password should match");
        assertTrue(userDetails.getAuthorities().isEmpty(), "Authorities should be empty");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("nonexistentuser"),
                "Should throw UsernameNotFoundException for nonexistent user");
        assertEquals("User not found", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testGenerateToken_Success() {
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername("testuser");
        mockUser.setPassword("testpassword");
        when(jwtUtil.generateToken(mockUser)).thenReturn("mock-jwt-token");

        String token = customUserDetailsService.generateToken(mockUser);

        assertNotNull(token, "Token should not be null");
        assertEquals("mock-jwt-token", token, "Generated token should match the mocked value");
        verify(jwtUtil, times(1)).generateToken(mockUser);
    }
}
