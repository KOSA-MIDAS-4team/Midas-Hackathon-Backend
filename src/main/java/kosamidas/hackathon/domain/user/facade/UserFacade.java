package kosamidas.hackathon.domain.user.facade;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.exception.UserNotFoundException;
import kosamidas.hackathon.domain.user.domain.repository.UserRepository;
import kosamidas.hackathon.domain.user.verifier.CreateUserVerifier;
import kosamidas.hackathon.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CreateUserVerifier createUserVerifier;

    public void save(User user) {
        createUserVerifier.alreadyExistsUserVerifier(user);
        user.encodePassword(passwordEncoder);
        userRepository.save(user);
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
