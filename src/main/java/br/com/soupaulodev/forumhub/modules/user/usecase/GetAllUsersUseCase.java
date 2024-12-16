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

/**
 * Use case for retrieving all users.
 * This class provides the business logic for retrieving all users.
 *
 * <p>
 * The {@link GetAllUsersUseCase} is responsible for:
 * <ul>
 *     <li>Retrieving all users.</li>
 * </ul>
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class GetAllUsersUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor for {@link GetAllUsersUseCase}.
     *
     * @param userRepository {@link UserRepository} the repository for handling user-related operations
     */
    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case.
     * Retrieves all users.
     * The users are retrieved in descending order of creation date.
     * The users are paginated.
     * The users' emails are not included in the response.
     * @return a list of {@link UserResponseDTO} containing all users
     */
    public List<UserResponseDTO> execute(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<UserEntity> entities = userRepository.findAll(pageable);

        return entities.getContent().stream().map(UserMapper::toResponseDTO).toList();
    }
}
