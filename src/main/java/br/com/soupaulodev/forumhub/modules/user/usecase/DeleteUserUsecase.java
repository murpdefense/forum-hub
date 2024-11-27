package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserUsecase {

    private final UserRepository userRepository;

    public DeleteUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id) {

        UserEntity userDB = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(userDB);
    }
}
