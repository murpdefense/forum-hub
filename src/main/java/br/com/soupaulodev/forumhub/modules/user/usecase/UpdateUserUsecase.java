package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserUsecase {

    private final UserRepository userRepository;

    public UpdateUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity execute(UUID id, UserEntity user) {
        UserEntity userDB = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (user == null
            || user.getName() == null
            && user.getUsername() == null
            && user.getEmail() == null
            && user.getPassword() == null) {

            throw new UserIllegalArgumentException("""
                You must provide at least one field to update:
                - name
                - username
                - email
                - password
                """);
        }
        if (user.getName() != null) {
            userDB.setName(user.getName());
        }
        if (user.getUsername() != null) {
            userDB.setUsername(user.getUsername());
        }
        if (user.getEmail() != null) {
            userDB.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            userDB.setPassword(user.getPassword());
        }

        return userRepository.save(userDB);
    }
}
