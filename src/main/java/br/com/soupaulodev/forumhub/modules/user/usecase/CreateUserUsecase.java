package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUsecase {

    private final UserRepository userRepository;

    public CreateUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity execute(UserEntity userEntity) {
        userRepository.findByUsernameAndEmail(userEntity.getUsername(), userEntity.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });
        return userRepository.save(userEntity);
    }
}
