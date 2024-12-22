package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for retrieving a paginated list of all users.
 * <p>
 * This service fetches users from the repository with pagination and sorting based on creation date.
 * The returned list excludes sensitive information, such as emails, and provides a structured response.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class ListUsersUseCase {

    private final UserRepository userRepository;

    public ListUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a paginated list of users.
     * <p>
     * This method retrieves users from the repository, paginated by the given parameters (page, size),
     * and sorted by the creation date in descending order. It returns a list of user data transfer objects (DTOs),
     * with email information excluded.
     * </p>
     *
     * @param page the page number to retrieve (0-based index)
     * @param size the number of users per page
     * @return a list of {@link UserResponseDTO} containing user details, excluding emails
     * @throws IllegalArgumentException if the page or size parameters are invalid (negative or zero)
     */
    public List<UserResponseDTO> execute(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be positive numbers.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<UserEntity> entities = userRepository.findAll(pageable);

        return entities.getContent().stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
