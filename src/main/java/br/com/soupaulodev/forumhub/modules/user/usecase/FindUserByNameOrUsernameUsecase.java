package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FindUserByNameOrUsernameUsecase {

    private final UserRepository userRepository;

    public FindUserByNameOrUsernameUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity execute(String nameOrUsername) {
        return userRepository.findByNameOrUsername(nameOrUsername);
    }
}
