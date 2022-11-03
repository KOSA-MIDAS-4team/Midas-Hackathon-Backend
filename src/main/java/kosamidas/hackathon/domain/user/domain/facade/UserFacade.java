package kosamidas.hackathon.domain.user.domain.facade;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.domain.exception.UserNotFoundException;
import kosamidas.hackathon.domain.user.domain.repository.UserRepository;
import kosamidas.hackathon.domain.user.domain.verifier.CreateUserVerifier;
import kosamidas.hackathon.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CreateUserVerifier createUserVerifier;

    public User createUser(User user) {
        createUserVerifier.alreadyExistsUserVerifier(user);
        user.encodePassword(passwordEncoder);
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        return SecurityUtil.getCurrentUser().getUser();
    }

    public User getUserByAuthId(String authId) {
        return userRepository.findByAuthId(authId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public User findUserByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }
}
